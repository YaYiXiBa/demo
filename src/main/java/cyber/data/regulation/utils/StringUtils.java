package cyber.data.regulation.utils;

import java.util.Map;

public class StringUtils {
    public static String mapToString(Map<String, String> map) {
        if(map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        map.forEach( (k, v) -> System.out.println(k + "=" + v));
    }
}
