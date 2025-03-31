import { chromium } from "playwright";

(async () => {
  const browser = await chromium.connectOverCDP("http://localhost:9222");
  const context = browser.contexts()[0];
  const page = await context.newPage();

  await page.goto("https://accounts.google.com/");
  console.log("🔐 Пройди авторизацию вручную, потом нажми Enter в терминале");
  await page.pause();

  await context.storageState({ path: "auth/auth.json" });
  console.log("✅ Сессия сохранена");
  await browser.close();
})();
