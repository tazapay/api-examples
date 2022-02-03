package com.commons;

import lombok.SneakyThrows;
import org.testng.ITestContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class TestConfig {

    public static final String ENVIRONMENT = "environment";
    public static final String RETRY_COUNT = "retry_count";
    public static final String TESTNG_CONTEXT = "testng_context";
    public static final String DOWNLOAD_DIR = "download_dir";
    public static final String TESTNG_REPORT_DIR = "testng_report_dir";
    public static final String HTML_FILE_DIR = "html_file_dir";
    public static final String RETRY_ENABLED = "retry_enabled";
    public static final String ENABLE_LOGS = "enable_logs";
    public static final String ENABLE_REPORTING = "enable_reporting";
    public static final String API_VERSION = "api_version";

    private Map<String, String> configMap = new HashMap<>();
    private Properties properties = new Properties();

    private void loadPropertiesIntoConfig(Properties properties) {
        properties.forEach((k,v) -> configMap.put(k.toString(), v.toString()));
    }

    @SneakyThrows
    public void buildTestContext(ITestContext context) {

        setAttribute(context, ENVIRONMENT, "QA");
        properties = Utility.readFromPropertiesFile(getEnvironment().toString().toLowerCase()+".properties");
        properties.keySet()
                .forEach( k -> setAttribute(context, k.toString(), null));
        context.getSuite().getXmlSuite().getAllParameters().keySet()
                .forEach(k -> setAttribute(context, k.toString(), null));

        setAttribute(context, RETRY_COUNT, "2");
        setAttribute(context, ENABLE_LOGS, true);
        setAttribute(context, ENABLE_REPORTING, true);

        // setting default report directory
        setAttribute(context, TESTNG_REPORT_DIR, System.getProperty("user.dir")
                + "/test-output/");
        setAttribute(context, RETRY_ENABLED, true);

    }

    private void setAttribute(ITestContext ctx, String key, Object defaultValue) {
        setValueInConfig(key, Optional.of(key)
                .map(System::getenv)
                .or(() -> getValueFromTestNGContext(ctx, key))
                .or(() -> Optional.ofNullable(properties.getProperty(key)))
                .orElse(Optional.ofNullable(defaultValue).map(v -> v.toString()).orElse(null)));
    }

    private Optional<String> getValueFromTestNGContext(ITestContext context, String key) {
        return Optional.ofNullable(context.getSuite().getXmlSuite().getParameter(key));
    }

    protected void setValueInConfig(String key, String value) {
        configMap.put(key, value);
    }

    public String getAttribute(String key) {
        return configMap.get(key);
    }

    public Long getValueAsLong(String key) {
        String val = getAttribute(key);
        return val != null ? Long.valueOf(getAttribute(key)) : null;
    }

    public Integer getValueAsInteger(String key) {
        String val = getAttribute(key);
        return val != null ? Integer.valueOf(getAttribute(key)) : null;
    }

    // returns false if there is no value in the context
    public Boolean getValueAsBoolean(String key) {
        String val = getAttribute(key);
        return val != null ? Boolean.valueOf(getAttribute(key)) : false;
    }

    public Environment getEnvironment() {
        return Environment.valueOf((String) getAttribute(ENVIRONMENT));
    }
    public String getApiVersion() {
        return getAttribute(API_VERSION);
    }
    public boolean isRetryEnabled() {
        return getValueAsBoolean(RETRY_ENABLED);
    }

    public boolean isEmpty(){
        return configMap.isEmpty();
    }

    @Override
    public String toString() {
        return configMap.toString();
    }

}

