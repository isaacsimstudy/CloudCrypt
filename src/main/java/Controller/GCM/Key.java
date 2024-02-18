package Controller.GCM;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.io.FileInputStream;
import javax.crypto.spec.SecretKeySpec;

public class Key{

    public static void main(String[] args) {

    }

    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // AES key size is 256 bits
        return keyGenerator.generateKey();
    }

    public static String convertKeyToBase64(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void writeKeyToFile(String key, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(key.getBytes());
        }
    }

    public static String readKeyFromFile(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] keyBytes = fis.readAllBytes();
            return new String(keyBytes);
        }
    }

    public static SecretKey convertBase64ToKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, "AES");
    }
}



