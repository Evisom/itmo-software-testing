import { test, expect } from "@playwright/test";

test("Открытие FeedBurner с авторизацией", async ({ page }) => {
  await page.goto("/");
  await expect(page).toHaveTitle(/FeedBurner/i);
});
