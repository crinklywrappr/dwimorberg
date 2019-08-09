package dwimorberg;

import java.math.BigInteger;
import java.math.BigDecimal;

public class Common {
    public static final double TOP_DOUBLE = Math.pow(2d, 53d);
    public static final double BOT_DOUBLE = (TOP_DOUBLE - 1) * -1;

    public static final double TOP_DOUBLE_ADD_RANGE = TOP_DOUBLE / 2;
    public static final double BOT_DOUBLE_ADD_RANGE = BOT_DOUBLE / 2;

    public static final long BOT_LONG_ADD_RANGE = (long) BOT_DOUBLE_ADD_RANGE;
    public static final long TOP_LONG_ADD_RANGE = (long) TOP_DOUBLE_ADD_RANGE;

    public static boolean isLongInAddRange (long x) {
        return x > BOT_LONG_ADD_RANGE && x < TOP_LONG_ADD_RANGE;
    }

    public static boolean isDoubleInAddRange (double x) {
        return x > BOT_DOUBLE_ADD_RANGE && x < TOP_DOUBLE_ADD_RANGE;
    }

    public static boolean isInt (double x) {
        return (isDoubleInAddRange(x) && x == (long) x)
            || Math.ceil(x) - Math.floor(x) == 0;
    }

    public static boolean isInt (BigDecimal x) {
        return x.compareTo(x.setScale(0)) == 0;
    }

    public static Number add (long x, long y) {
        long z = x + y;
        if ((z ^ x) < 0 && (z ^ y) < 0) {
            return BigInteger.valueOf(x).add(BigInteger.valueOf(y));
        }
        return z;
    }

    public static Number add (double x, long y) {
        // TODO: enlarge this limit
        if (isDoubleInAddRange(x) && isDoubleInAddRange((double)y)) {
            return x + (double) y;
        }
        return BigDecimal.valueOf(x).add(BigDecimal.valueOf(y));
    }

    public static Number add (double x, double y) {
        // TODO: enlarge this limit
        if (isDoubleInAddRange(x) && isDoubleInAddRange(y)) {
            return x + y;
        }
        return BigDecimal.valueOf(x).add(BigDecimal.valueOf(y));
    }
}
