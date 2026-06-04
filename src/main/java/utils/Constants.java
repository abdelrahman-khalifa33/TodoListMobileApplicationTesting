package utils;

import java.util.ResourceBundle;

public class Constants {

    private static final ResourceBundle ENVIRONMENT_RN = ResourceBundle.getBundle("environment");
    public static final String ENVIRONMENT_NAME;
    static {
        String raw = ENVIRONMENT_RN.getString("env.name");
        ENVIRONMENT_NAME = (raw == null || raw.startsWith("${")) ? "Testing" : raw;
    }

    public static final String APP_DATA_PROVIDER_PATH =
            System.getProperty("user.dir") + "/src/main/resources/testData/AppTestData.json";
}
