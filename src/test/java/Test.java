import dev.quantum.qlib.NumberUtils;
import dev.quantum.qlib.QLib;

public class Test {
    public static void main(String[] args) {
        System.out.println("Dev Mode: " + QLib.DEVELOPMENT_MODE);
        double num = 6332858585.2664;
        System.out.println(NumberUtils.COMMA_NUMBER_SHOWPOINT.format(num));
    }
}
