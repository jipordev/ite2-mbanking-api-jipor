package co.istad.mbanking.util;

import java.util.Random;

public class RandomUtil {

    public static String generateNineDigitString() {
        long lowerBound = 100000001;
        long upperBound = 1000000000;

        Random random = new Random();
        long randomNumber = lowerBound + random.nextInt((int) (upperBound - lowerBound));

        // Convert the random number to a String and pad with zeros to ensure 9 digits
        return String.format("%09d", randomNumber);
    }

}
