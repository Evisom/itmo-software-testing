import { test, expect } from "@playwright/test";

test("Можно создать Proxy", async ({ page }) => {
  const FEED_URL = "https://vc.ru/rss";
  const FEED_TITLE = `vcru-rss-${Math.random().toString(36).substring(2, 10)}`;
  const FEED_LINK = "https://vc.ru/";

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
    .fill(FEED_URL); // Заполняем Feed URL

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
    .fill(FEED_TITLE); // Заполняем Proxy Title
  await page
    .locator(
      "xpath=/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-proxy-dialog/mat-dialog-actions/progress-button/div/button"
    )
    .click(); // Кликаем Create

  await expect(
    page.locator(
      `xpath=/html/body/app-root/div/div[2]/div/app-home/div/div//*[contains(text(), "${FEED_TITLE}")]`
    )
  ).toBeVisible(); // Наш proxy должен появится в списке

  await page.goto(
    (await page
      .locator(
        `xpath=/html/body/app-root/div/div[2]/div/app-home/div/div//*[contains(text(), "${FEED_TITLE}")]/preceding-sibling::a`
      )
      .getAttribute("href")) as string
  ); // Переходим на наш RSS Proxy
  await page.waitForLoadState("domcontentloaded");
  await expect(
    page.locator("#folder1 > div.opened > div:nth-child(6) > span:nth-child(2)")
  ).toHaveText(FEED_LINK); // Проверяем что это реально наш url
});
