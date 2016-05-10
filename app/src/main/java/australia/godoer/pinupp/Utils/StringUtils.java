package australia.godoer.pinupp.Utils;

/**
 * Created by neo on 5/5/2016.
 */
public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.trim().isEmpty();
    }
}
