package com.commons;

import org.testng.ITestContext;

public class ContextManager {

    private static ThreadLocal<TestConfig> testContext = new ThreadLocal<>();

    public static void initContext(ITestContext context) {
        if (testContext.get() == null) {
            testContext.set(new TestConfig());
            testContext.get().buildTestContext(context);
        }
    }

    public static TestConfig getTestContext() {
        return testContext.get();
    }
}
