package cyber.data.regulation.enums;

import lombok.Getter;

@Getter
public enum AppKeyEnums {
    ADJUDICATE("adjudicat", "cHWKREvp"),
    SIMILARITY_PUSH("similarPush", "xxx"),
    SUPPLY_EVIDENCE("supplyevidence", "xxx"),
    COMPUTE_AMOUNT("computeAmount", "xxx");
    private final String appKey;
    private final String appName;

    AppKeyEnums(String appName, String appKey) {
        this.appKey = appKey;
        this.appName = appName;
    }

}
