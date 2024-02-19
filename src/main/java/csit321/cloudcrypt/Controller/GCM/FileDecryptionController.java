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
public class FileDecryptionController {

    public static void main(String[] args) {
        // Depending on how dropbox API works, need to modify this codes
        // Get the current directory
        //File directory = new File(".");

        // List all files in the directory
       // File[] files = directory.listFiles();

        // Loop through the files and decrypt those with "_encrypted" in the name
        //for (File file : files) {
          //  if (file.isFile() && file.getName().contains("_encrypted")) {
               // decryptFiles(file.getAbsolutePath());
            //}
        //}
    }

    public static void decryptFiles(String encryptedFileName) {
        byte[] fixedIV = "123456789!!!!!!!".getBytes(); // testing fixed 16 bits IV
        String secretKeyBase64;

        try {
            secretKeyBase64 = Key.readKeyFromFile("aes_key.txt"); // change to get from database
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit the method if reading the key fails
        }
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);

        try {
            File encryptedFile = new File(encryptedFileName);
            String originalFileName = encryptedFile.getName().replace("_encrypted", "");
            File decryptedFile = new File(originalFileName);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

            try (FileInputStream fis = new FileInputStream(encryptedFile);
                 FileOutputStream fos = new FileOutputStream(decryptedFile)) {

                byte[] buffer = new byte[8192]; // 8KB buffer
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] decryptedChunk = cipher.update(buffer, 0, bytesRead);
                    fos.write(decryptedChunk);
                }
                byte[] decryptedFinalChunk = cipher.doFinal();
                fos.write(decryptedFinalChunk);
            }

        } catch (Exception e) {
            e.printStackTrace(); //error checking
        }
    }
}
