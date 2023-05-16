//TODO: Complete java docs and code in missing spots.
//This code is already made for you, you just need to call Hashing.cryptHash whenever you want to generate the hash of a particular String. The output is formatted in HexaDecimal


/**
 * The Hashing class extends the SHA512 class.
 */
public class Hashing extends SHA512 {

    /**
     *computes the SHA512 hash of the hash of the given string and returns the first.
     * 128 characters of the outcome digest as a string.
     * @param s the input string to hash.
     * @return a string containing the first 128 characters of the SHA-512 hash.
     */
    public static String cryptHash(String s) {
        String digest = hashSHA512(s);
        return digest.substring(0, 128);
    }
}
