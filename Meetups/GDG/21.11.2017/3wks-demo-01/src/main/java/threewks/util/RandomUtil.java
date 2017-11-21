package threewks.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.hashids.Hashids;

import java.security.SecureRandom;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * Helpers for dealing with randomisation.
 */
public class RandomUtil {
    private static char[] possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    /**
     * Generate a random alphanumeric string using SecureRandom.
     *
     * @param length the length of the generated string
     * @return a random string of the given length
     * @see {@link SecureRandom}
     */
    public static String secureRandomAlphanumeric(int length) {
        return RandomStringUtils.random(
                length,
                0,
                possibleCharacters.length - 1,
                false,
                false,
                possibleCharacters,
                new SecureRandom());
    }

    /**
     * Generate a short unique ID.
     */
    public static String uniqueShortId() {
        Hashids hashids = new Hashids(randomUUID().toString());
        return hashids.encode(System.currentTimeMillis());
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
