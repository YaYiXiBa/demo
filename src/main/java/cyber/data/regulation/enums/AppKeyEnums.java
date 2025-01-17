package cyber.data.regulation.enums;

public enum AppKeyEnums {
    ADJUDICATE("adjudicat", "cHWKREvp"),
    SIMILARPUSH("similarPush", "xxx"),
    SUPPLYEVIDENCE("supplyevidence", "xxx"),
    COMPUTEAMOUNT("computeAmount", "xxx");
    private final String appKey;
    private final String appName;

    AppKeyEnums(String appName, String appKey) {
        this.appKey = appKey;
        this.appName = appName;
    }
    
    public String getAppKey() {
        return appKey;
    }

    public String getAppName() {
        return appName;
    }
}
