const puppeteer = require('pupeteer');

describe('Job application UAT', ()=> {
  let browser, page; 
  
  beforeAll(async () => {
    browser = await puppeteer.launch();
    page = await browser.newPage();
  });

  afterAll(async () => {
    await browser.close();
  });

  test(' Student can apply to a job ' async() => {
  //go to the website page
  await page.goto('http://localhost:8080/LoginPage.html');
  
  // Log in to your candidate account
  await page.type('#Email:', 'test@gmail.com'); //assuming a student account already exists with this information 
  await page.type('#Password:', '123456');
  await page.click('#Login');
  
  // Wait for page to reload after registration
  await page.waitForNavigation();
  
  / Assert that user is redirected to dashboard page after successful registration
  expect(page.url()).toBe('http://localhost:8080/viewJobPostings.html'); //make sure we are redirected to the correct page to view postings 
  
  await page.click('#jobPosting');
  
  await page.waitForNavigation(); 
  
  await page.click('#Apply');
  
  await page.waitForNavigation();
  
  await page.click('#Default resume');
  await page.click('#Default letter');
  await page.click('#Default transcript');
  
  await page.click('#Submit');
  
  const JobStatus = await page.$eval('#Status',el => el.textContent));
  
  expect(JobStatus).toBe('Applied to');
  
  await browser.close();
  
  };
