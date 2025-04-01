import { test, expect } from "@playwright/test";

test("Сайт открывается для авторизованных пользователей", async ({ page }) => {
  await page.goto("/");
  await expect(page).toHaveTitle(/FeedBurner/i);
  await expect(
    page.locator("xpath=/html/body/app-root/div/div[2]/div/app-home/div/h1")
  ).toHaveText("Your Feeds"); // Отображается Your Feeds
  await expect(
    page.locator("xpath=/html/body/app-root/div/div[1]/div")
  ).toHaveText(/@gmail\.com/); // Отображается залогиненный email

  await expect(
    page.locator(
      "xpath=/html/body/app-root/div/div[2]/div/app-home/div/span/button/span[1]"
    )
  ).toHaveText(/Create Proxy/i); // Отображается кнопка создать прокси
});
