package Controller.GCM;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.util.*;

public class FileDecryption {

    //testing key
    private static final byte[] FIXED_KEY = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    public static void main(String[] args) {

        decryptFiles();
    }

    public static void decryptFiles() {
        byte[] fixedIV = "123456789!!!!!!!".getBytes(); // testing fixed 16 bits IV

        try {
            byte[] encryptedData = readFileContents(new File("encrypted_files.bin"));

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(FIXED_KEY, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Write decrypted data back to the original file
            FileOutputStream fileOut = new FileOutputStream("decrypted_files.docx");
            fileOut.write(decryptedData);
            fileOut.close();

            System.out.println("Files decrypted successfully.");
        } catch (Exception e) {
            e.printStackTrace(); //error checking
        }
    }

    public static byte[] getRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static byte[] readFileContents(File file) throws IOException {
        byte[] fileBytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileBytes = new byte[(int) file.length()];
            int read = fis.read(fileBytes);
        }
        return fileBytes;
    }
}
