package csit321.cloudcrypt.Controller.GCM;

import jakarta.persistence.Table;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Table(name = "key")
public class FileEncryptionController {

    public static void main(String[] args) throws IOException {

    }

    public static void encryptFiles() {
        byte[] fixedIV = "123456789!!!!!!!".getBytes();
        String secretKeyBase64;

        try {
            secretKeyBase64 = Key.readKeyFromFile("aes_key.txt"); // change to get from database
        } catch (Exception e) {
            e.printStackTrace();
            return; // Exit the method if reading the key fails
        }
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);
        try {
            //File[] files = new File(".").listFiles();
            File[] files = new File(".").listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".docx") || name.toLowerCase().endsWith(".pdf") ||
                            name.toLowerCase().endsWith(".mp4"));

            if (files != null) {
                for (File file : files) {
                    System.out.println("Encrypting file: " + file.getName());
                    encryptFile(file, secretKeyBytes, fixedIV);
                }
            }

            System.out.println("Files encrypted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptFile(File file, byte[] secretKeyBytes, byte[] fixedIV) {
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(file.getName() + "_encrypted")) {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] encryptedChunk = cipher.update(buffer, 0, bytesRead);
                fos.write(encryptedChunk);
            }
            byte[] encryptedFinalChunk = cipher.doFinal();
            fos.write(encryptedFinalChunk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
