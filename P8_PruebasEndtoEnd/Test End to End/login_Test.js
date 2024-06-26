import { browser } from 'k6/experimental/browser';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations', // para realizar iteraciones sin indicar el tiempo
      options: {
        browser: {
          type: 'chromium',
        },
      },
    } 
  },
  thresholds: {
    checks: ["rate==1.0"]
  }
}

export default async function () {
  const page = browser.newPage();
  try {
    await page.goto('http://localhost:4200/');

    page.locator('input[name="nombre"]').clear();
    page.locator('input[name="DNI"]').clear();

    page.locator('input[name="nombre"]').type('Pablo Motos');
    page.locator('input[name="DNI"]').type('111111111');

    const submitButton = page.locator('button[name="login"]');
    sleep(2);
    await Promise.all([page.waitForNavigation({waitUntil: 'networkidle'}), submitButton.click()]);

    check(page, {
      'Title': p => p.locator('h2').textContent() == 'Listado de pacientes',
    });
    sleep(3);

  } finally {
    page.close();
  }
}