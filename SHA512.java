//TODO: Complete java docs and code in missing spots.
import java.security.*;
import java.nio.charset.*;

//This code is already made for you, it is extended by Hashing to generate the SHA-512 hash of a String. No explicit call to any method here is required.

/**
 * This class provides methods for computing the sha512 of a message.
 */
public class SHA512 {
    /**
     * computes the sha512 hash of the given message and returns the result as a hexadecimal String.
     * @param message the message to hash.
     * @return a string representing the sha512 of the message.
     */
    protected static String hashSHA512(String message) {
        String sha512ValueHexa = "";
        try {
            MessageDigest digest512 = MessageDigest.getInstance("SHA-512");
            sha512ValueHexa = byteToHex(digest512.digest(message.getBytes(StandardCharsets.UTF_8)));
        }
        catch(NoSuchAlgorithmException exp) {
            exp.getMessage();
        }
        return sha512ValueHexa;
    }


    /**
     * cinverst an array of bytes to a hexadecimal String representation.
     * @param digest the array of bytes to convert.
     * @return a hexadecimal string representing the input byte array.
     */

    public static String byteToHex(byte[] digest) {
        StringBuilder vector = new StringBuilder();
        for (byte c : digest) {
            vector.append(String.format("%02X", c));
        }
        String output = vector.toString();
        return output;
    }
}
