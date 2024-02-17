package Controller.GCM;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileEncryption {

    //testing key
    private static final byte[] FIXED_KEY = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };
    public static void main(String[] args) {
        encryptFiles();
    }

    public static void encryptFiles() {
        List<byte[]> itemLists = new ArrayList<>();
        byte[] fixedIV = "123456789!!!!!!!".getBytes(); // testing fixed 16 bits IV

        try {
            File[] files = new File(".").listFiles((dir, name) -> name.toLowerCase().endsWith(".docx"));
            if (files != null) {
                for (File file : files) {
                    byte[] fileBytes = readFileContents(file);
                    System.out.println("Read file: " + file.getName());
                    itemLists.add(fileBytes);
                }
            }

            byte[] combinedBytes = combineByteArrays(itemLists);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(FIXED_KEY, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] ctBytes = cipher.doFinal(combinedBytes);

            FileOutputStream fileOut = new FileOutputStream("encrypted_files.bin");
            fileOut.write(ctBytes);
            fileOut.close();

            System.out.println("Files encrypted successfully.");

        } catch (Exception e) {
            e.printStackTrace(); //error checking
        }
    }


    public static byte[] readFileContents(File file) throws IOException {
        byte[] fileBytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileBytes = new byte[(int) file.length()];
            int read = fis.read(fileBytes);
        }
        return fileBytes;
    }

    public static byte[] combineByteArrays(List<byte[]> byteArrays) {
        int totalLength = byteArrays.stream().mapToInt(array -> array.length).sum();
        byte[] combinedBytes = new byte[totalLength];
        int currentIndex = 0;
        for (byte[] byteArray : byteArrays) {
            System.arraycopy(byteArray, 0, combinedBytes, currentIndex, byteArray.length);
            currentIndex += byteArray.length;
        }
        return combinedBytes;
    }
}
