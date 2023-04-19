const puppeteer = require('puppeteer');

test('Profile management', async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto('http://localhost:8080/UserAccount/index.jsp');

  // Fill in registration information
  await page.type('#Email', 'test@gmail.com');
  await page.type('#Password', '123456');
  await page.select('select[name = Select user type:]', 'Student')
  await page.click('#Create Account');

  // Wait for page to reload after registration
  await page.waitForNavigation();

  // Assert that user is redirected to dashboard page after successful registration
  expect(page.url()).toBe('http://localhost:8080/UserAccount/CreateUser.html');
  
  await page.type('#First Name:', 'Test');
  await page.type('Last Name:', 'Test');
  await page.select('select[name = Engineering Field:]', 'Computer Engineering');
  await page.click('#Submit');
  
  const Fname = await page.$eval('#First Name:', el => el.textContent);
  const Lname = await page.$eval('#Last Name:', el => el.textContent);
  const Field = await page.$eval('#Engineering Field:', el => el.textContent);
  
  expect(Fname).toBe('Test');
  expect(Lname).toBe('Test');
  expect(field).toBe('Computer Engineering');
  

  await browser.close();
});
