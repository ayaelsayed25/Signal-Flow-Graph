package graphs;

import java.math.BigInteger;
import java.util.UUID;

public class IdManager {
    public static Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val/1000;
    }
	/* public static BigInteger sumId(BigInteger number1, Long number2) {
		    BigInteger bigNumber1 = number1;
		    BigInteger bigNumber2 = BigInteger.valueOf(number2);
		    BigInteger result = bigNumber1.add(bigNumber2);
		    return result;
		}*/



}
