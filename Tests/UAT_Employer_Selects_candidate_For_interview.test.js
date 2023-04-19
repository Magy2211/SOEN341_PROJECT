describe('Employer job posting management', () => {
  test('Can review and select candidates for a job posting', async () => {
    // Navigate to the website's homepage.
    await page.goto('http://localhost:3000');

    // Log in to your employer account.
    await page.type('#Email', 'employer@gmail.com');
    await page.type('#Password', '123456');
    await page.click('#Login');
    
    await page.waitForNavigation();
    
    expect(page.url()).toBe('http://localhost:8080/UserAccount//ViewCreatedJobPostings.jsp');
    
    await page.click('#jobPosting');
    
    await page.waitForNavigation();
    
    await page.click('#Student Information');
    
    await page.click('#Select For Interview');
    
    await browser.close();
 }); 
   
  
