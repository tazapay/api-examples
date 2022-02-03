package com.commons;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;

import static com.commons.ContextManager.getTestContext;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void initTestRun(ITestContext context) {
        ContextManager.initContext(context);

        // Directories initialization
        File testng_report_root_location = new File(
                getTestContext()
                        .getAttribute(
                                TestConfig.TESTNG_REPORT_DIR));
        testng_report_root_location.mkdir();
        File htmlFileLocation = new File(
                testng_report_root_location.getAbsolutePath() + "/htmlFiles");
        htmlFileLocation.mkdir();
        File screenShotLocation = new File(
                testng_report_root_location.getAbsolutePath() + "/screenshots");
        screenShotLocation.mkdir();
        getTestContext().setValueInConfig(
                TestConfig.HTML_FILE_DIR,
                htmlFileLocation.getAbsolutePath());
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        ContextManager.initContext(context);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context) {
        ContextManager.initContext(context);
    }

	/*@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws APIException, IOException {
		if(result.getStatus()==ITestResult.SUCCESS) {
			TestrailsIntegeration.addResultForTestCase(testCaseId , TestrailsIntegeration.TEST_CASE_PASSED_STATUS , "Test case is passed");
		} else if (result.getStatus()==ITestResult.FAILURE) {
			TestrailsIntegeration.addResultForTestCase(testCaseId , TestrailsIntegeration.TEST_CASE_FAILED_STATUS , "Test case is failed");
		}
	}

	 */







    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext context){

    }
}
