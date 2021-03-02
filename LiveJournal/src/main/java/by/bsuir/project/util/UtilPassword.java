package by.bsuir.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class UtilPassword {

    /**
     * private default constructor (help SonarLint)
     */
    private UtilPassword() {
    }

    /**
     * Hashes password using MD5 algorithm
     *
     * @param string password - password to be hashed
     * @return hashed password
     */
    public static String hashPassword(String string) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");
            digest.reset();
            digest.update(string.getBytes());
            byte[] hash = digest.digest();
            Formatter formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02X", b);
            }
            String md5sum = formatter.toString();
            formatter.close();
            return md5sum;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
