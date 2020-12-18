package admin.build1.Services;

import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class ImageService {
    public static String saveImage(Bitmap image) throws NoSuchAlgorithmException {
        String fileName = getHash(image);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public static String getHash(Bitmap imageBitmap) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        int amountBytes = imageBitmap.getByteCount();
        byte[] hashedImage = md.digest(ByteBuffer.allocate(4).putInt(amountBytes).array());

        return Arrays.toString(hashedImage);
    }
}
