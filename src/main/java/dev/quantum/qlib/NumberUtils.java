package dev.quantum.qlib;

import java.text.DecimalFormat;

public class NumberUtils {
    /** Format 123456 => 123,456 */
    public static final DecimalFormat COMMA_NUMBER = new DecimalFormat("#");
    /** Format 123456 => 123,456.00 or 123456.78 => 123,456.78 */
    public static final DecimalFormat COMMA_NUMBER_SHOWPOINT = new DecimalFormat("#.00");

    static {
        COMMA_NUMBER.setGroupingUsed(true);
        COMMA_NUMBER.setGroupingSize(3);
        COMMA_NUMBER_SHOWPOINT.setGroupingUsed(true);
        COMMA_NUMBER_SHOWPOINT.setGroupingSize(3);
    }
}
