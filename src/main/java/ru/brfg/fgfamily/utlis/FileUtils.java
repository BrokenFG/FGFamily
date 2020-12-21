package ru.brfg.fgfamily.utlis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static void copy(InputStream inputStream, File file) {
        try {
            int read;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] array = new byte[1024];
            while ((read = inputStream.read(array)) > 0) {
                fileOutputStream.write(array, 0, read);
            }
            fileOutputStream.close();
            inputStream.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void mkdir(File file) {
        try {
            file.mkdir();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

