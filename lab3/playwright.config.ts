import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  projects: [
    // {
    //   name: "chromium",
    //   use: {
    //     browserName: "chromium",
    //     storageState: "auth/auth.json",
    //     headless: false,
    //   }
    // }
    {
      // Пока без firefox, потом включим
      name: "firefox",
      use: {
        browserName: "firefox",
        storageState: "auth/auth.json",
        headless: false,
      },
    },
  ],
  use: {
    baseURL: "https://feedburner.google.com/",
    viewport: { width: 1280, height: 720 },
    screenshot: "only-on-failure",
    video: "retain-on-failure",
  },
  reporter: [["line"], ["allure-playwright"]],
});
