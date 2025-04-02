import { test, expect } from "@playwright/test";
import { parseStringPromise } from "xml2js";
test.describe("Операции с proxy", () => {
  const TEST_DATA = {
    FEED_URL: "https://vc.ru/rss",
    RESOURCE_LINK: "https://vc.ru/",
    CUSTOM_FIELD_TITLE: "customTitle123",
    CUSTOM_FIELD_DESC: "customDescription123",
    IT_CATEGORY: "Business",
    IT_SUBCATEGORY: "Investing",
    IT_IMAGE: "https://example.com/image.png",
    IT_SUBTITLE: "itunes_subtitle",
    IT_SUMMARY: "itunes_summary",
    IT_KEYWORDS: "itunes_keywords",
    IT_EMAIL: "itunes_email",
    IT_AUTHOR: "itunes_author",
    IT_COPYRIGHT: "itunes_copywright",
    FEED_TITLE: "vcru-rss-v116hzjf",
    FEED_NEW_TITLE: "vcru-rss-v116hzjf",
    FEED_CUSTOM_FIELD_URL: "vcru-rss-tggflo127i8",
  };
  test.beforeAll(async () => {
    TEST_DATA.FEED_TITLE = `vcru-rss-${Math.random()
      .toString(36)
      .substring(2, 10)}`;
    TEST_DATA.FEED_NEW_TITLE = `vcru-rss-${Math.random()
      .toString(36)
      .substring(2, 10)}`;
    TEST_DATA.FEED_CUSTOM_FIELD_URL = `vcru-rss-${Math.random()
      .toString(36)
      .substring(2, 22)}`;

    console.log(TEST_DATA);
  });

  test("Можно создать Proxy", async ({ browserName, page }) => {
    await page.goto("/");

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/app-home/div/span/button"
      )
      .click(); // Кликам на create proxy

    await expect(
      page.locator("xpath=/html/body/div[2]/div[2]/div/mat-dialog-container")
    ).toBeVisible(); // Форма видна

    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-content/mat-horizontal-stepper/div/div[2]/div[1]/form/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.FEED_URL); // Заполняем Feed URL

    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-actions/progress-button/div/button"
      )
      .click(); // Кликаем Next

    await page.waitForSelector(
      'xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-content/mat-horizontal-stepper/div/div[2]/div[2]/form/div[1]/mat-form-field/div/div[1]/div[3]/input[contains(@placeholder, "Proxy title")]'
    ); // Ждем вторую часть формы
    await page
      .locator(
        'xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-content/mat-horizontal-stepper/div/div[2]/div[2]/form/div[1]/mat-form-field/div/div[1]/div[3]/input[contains(@placeholder, "Proxy title")]'
      )
      .click(); // Кликаем на инпут с proxy title

    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-content/mat-horizontal-stepper/div/div[2]/div[2]/form/div[1]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.FEED_TITLE); // Заполняем Proxy Title
    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-actions/progress-button/div/button"
      )
      .click(); // Кликаем Create

    await expect(
      page.locator(
        `xpath=/html/body/app-root/div/div[2]/div/app-home/div/div//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]`
      )
    ).toBeVisible(); // Наш proxy должен появится в списке

    const response = await page.request.get(
      (await page
        .locator(
          `xpath=/html/body/app-root/div/div[2]/div/app-home/div/div//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/preceding-sibling::a`
        )
        .getAttribute("href")) as string
    );
    const xmlContent = await response.text();
    const parsedXml = await parseStringPromise(xmlContent);
    expect(parsedXml.rss.channel[0].link).toContain(TEST_DATA.RESOURCE_LINK);
  });

  test("Можно обновить ленту", async ({ page }) => {
    await page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/following::button[contains(@aria-label, 'More Options')]`
      )
      .nth(0)
      .click(); // Кликаем на троеточие

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/div/button[1]")
      .click(); // Кликаем на обновить

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    await expect(
      page.getByText("Feed content will be refreshed in the background")
    ).toBeVisible(); // Появилось уведомление
  });

  test("Можно деактивировать", async ({ browserName, page }) => {
    await page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/following::button[contains(@aria-label, 'More Options')]`
      )
      .nth(0)
      .click(); // Кликаем на троеточие

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/div/button[3]")
      .click(); // Кликаем на deactivate

    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-confirm-dialog/mat-dialog-actions/button[2]"
      )
      .click(); // Кликаем на Confirm

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    await expect(page.getByText("Proxy deactivated")).toBeVisible(); // Появилось уведомление

    await expect(
      page.locator(
        `//div[contains(@class, 'proxy-list-item') and contains(@class, 'deactivated')]//div[contains(@class, 'proxy-title') and contains(text(), "${TEST_DATA.FEED_TITLE}")]//following-sibling::div[contains(@class, 'redirect-message') and contains(text(), 'This feed is redirecting to your source feed')]`
      )
    ).toBeVisible(); // Будет виден текст This feed is redirecting to your source feed в блоке
  });

  test("Можно активировать деактивированную ленту", async ({ page }) => {
    await page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/following::button[contains(@aria-label, 'More Options')]`
      )
      .nth(0)
      .click(); // Кликаем на троеточие

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/div/button[3]")
      .click(); // Кликаем на activate

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    await expect(page.getByText("Proxy activated")).toBeVisible(); // Появилось уведомление

    await expect(
      page.locator(
        `//div[contains(@class, 'proxy-list-item') and contains(@class, 'deactivated')]//div[contains(@class, 'proxy-title') and contains(text(), "${TEST_DATA.FEED_TITLE}")]//following-sibling::div[contains(@class, 'redirect-message') and contains(text(), 'This feed is redirecting to your source feed')]`
      )
    ).toHaveCount(0); // Будет виден текст This feed is redirecting to your source feed в блоке
  });

  test("Можно экспортировать email подписчиков", async ({ page }) => {
    await page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/following::button[contains(@aria-label, 'More Options')]`
      )
      .nth(0)
      .click(); // Кликаем на троеточие

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/div/button[2]")
      .click(); // Кликаем на export email ....

    const downloadPromise = page.waitForEvent("download");

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    const download = await downloadPromise;

    expect(download).toBeTruthy();
  });

  test("Можно сконфигурировать прокси", async ({ browserName, page }) => {
    page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_TITLE}")]/following::button[1]`
      )
      .nth(0)
      .click(); // Кликаем на Configure Proxy

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/mat-form-field[1]/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.FEED_NEW_TITLE); // Изменяем Proxy Title

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/div[1]/mat-form-field/div/div[1]/div[3]/custom-url-input/div/input"
      )
      .fill(TEST_DATA.FEED_CUSTOM_FIELD_URL); // Изменяем Custom Feed URL

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/fieldset/mat-form-field[1]/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.CUSTOM_FIELD_TITLE); // Изменяем Custom Feed Title

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/fieldset/mat-form-field[2]/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.CUSTOM_FIELD_DESC); // Изменяем Custom Feed Description

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/fieldset/div[1]/mat-form-field/div/div[1]/div[3]"
      )
      .click(); // Открываем селект podcast enclosure link

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/mat-option[1]/span")
      .click(); // Выбираем any rich media file

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/fieldset/div[2]/mat-checkbox/label/span[1]"
      )
      .click(); // Ставим чекбокс индексации поисковыми системами

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/div/button[2]"
      )
      .click(); // Сохраняем изменения

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    const response = await page.request.get(
      `https://feeds.feedburner.com/${TEST_DATA.FEED_CUSTOM_FIELD_URL}`
    );
    const xmlContent = await response.text();
    const parsedXml = await parseStringPromise(xmlContent);

    const channel = parsedXml.rss.channel[0];

    expect(channel.title[0]).toBe(TEST_DATA.CUSTOM_FIELD_TITLE);

    expect(channel.description[0]).toBe(TEST_DATA.CUSTOM_FIELD_DESC);

    const enclosure = parsedXml.rss.channel[0].item[0].enclosure;
    expect(enclosure).toBeDefined();
    expect(enclosure.length).toBeGreaterThan(0);

    expect(channel["xhtml:meta"][0].$.content).toBe("noindex");
  });

  test("Можно сконфигурировать iTunes Tags", async ({ browserName, page }) => {
    await page.goto("/");

    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_NEW_TITLE}")]/following::button[1]`
      )
      .nth(0)
      .click(); // Кликаем на Configure Proxy

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/form/div[2]/button"
      )
      .click(); // Кликаем сконфигурировать iTunes Tags

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[1]/mat-form-field/div/div[1]/div/categories-selector/form/mat-form-field[1]/div/div[1]/div[3]"
      )

      .click(); // Селект категории

    await page
      .locator(
        `xpath=//span[contains(@class, 'mat-option-text') and contains(text(), '${TEST_DATA.IT_CATEGORY}')]`
      )
      .nth(0)
      .click(); // Выбор опции категории

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[1]/mat-form-field/div/div[1]/div/categories-selector/form/mat-form-field[2]/div/div[1]/div[3]"
      )

      .click(); // Селект подкатегории

    await page
      .locator(
        `xpath=//span[contains(@class, 'mat-option-text') and contains(text(), '${TEST_DATA.IT_SUBCATEGORY}')]`
      )
      .nth(0)
      .click(); // Выбор опции категории

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[2]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_IMAGE);
    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[3]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_SUBTITLE);
    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[4]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_SUMMARY);
    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[5]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_KEYWORDS);
    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[6]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_EMAIL);
    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[1]/div[6]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_EMAIL);

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[2]/div[1]/mat-form-field/div/div[1]/div[3]"
      )
      .click(); // Выбор explicit content
    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/mat-option[2]/span")
      .click();

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[2]/div[2]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_AUTHOR);

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/form/fieldset[2]/div[3]/mat-form-field/div/div[1]/div[3]/input"
      )
      .fill(TEST_DATA.IT_COPYRIGHT);

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/enclosure-transformation/div/div/button[3]"
      )
      .click();

    await page.waitForLoadState("networkidle");

    await page
      .locator(
        "xpath=/html/body/app-root/div/div[2]/div/manage-proxy/div/div/button[1]"
      )
      .click();

    await page.waitForLoadState("networkidle");

    const response = await page.request.get(
      `https://feeds.feedburner.com/${TEST_DATA.FEED_CUSTOM_FIELD_URL}`
    );
    const xmlContent = await response.text();
    const parsedXml = await parseStringPromise(xmlContent);
    const channel = parsedXml.rss.channel[0];

    expect(channel["itunes:category"][0].$.text).toBe(TEST_DATA.IT_CATEGORY);
    expect(channel["itunes:category"][0]["itunes:category"][0].$.text).toBe(
      TEST_DATA.IT_SUBCATEGORY
    );
    expect(channel["itunes:image"][0].$.href).toBe(TEST_DATA.IT_IMAGE);

    expect(channel["itunes:subtitle"][0]).toBe(TEST_DATA.IT_SUBTITLE);

    expect(channel["itunes:summary"][0]).toBe(TEST_DATA.IT_SUMMARY);

    expect(channel["itunes:keywords"][0]).toBe(TEST_DATA.IT_KEYWORDS);

    expect(channel["itunes:author"][0]).toBe(TEST_DATA.IT_AUTHOR);

    expect(channel["itunes:owner"][0]["itunes:email"][0]).toBe(
      TEST_DATA.IT_EMAIL
    );

    expect(channel["copyright"][0]).toBe(TEST_DATA.IT_COPYRIGHT);
  });
  test("Можно удалить", async ({ page }) => {
    await page.goto("/");
    await page
      .locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_NEW_TITLE}")]/following::button[contains(@aria-label, 'More Options')]`
      )
      .nth(0)
      .click(); // Кликаем на троеточие

    await page
      .locator("xpath=/html/body/div[2]/div[2]/div/div/div/div/button[4]")
      .click(); // Кликаем на delete

    await page
      .locator(
        "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-confirm-dialog/mat-dialog-actions/button[2]"
      )
      .click();

    await page.waitForLoadState("networkidle"); // Ждем загрузку

    await expect(
      page.locator(
        `xpath=//div[contains(@class, 'proxy-list-item')]//*[contains(text(), "${TEST_DATA.FEED_NEW_TITLE}")]`
      )
    ).toHaveCount(0);
  });
});
