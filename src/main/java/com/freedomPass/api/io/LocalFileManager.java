
package com.freedomPass.api.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class LocalFileManager {
 
    public void createDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!checkIfDirExists(dirPath)) {
            dir.mkdir();
        }
    }

    public boolean checkIfDirExists(String dirPath) {
        File dir = new File(dirPath);
        return dir.exists();
    }
    
     public boolean copyFile(String srcFile, String desPath) throws FileNotFoundException {
       File file = new File(desPath);
        if (!file.exists()) {
            try(InputStream inStream = new FileInputStream(srcFile)) {
                FileUtils.copyInputStreamToFile(inStream, file);
                return true;
            } catch (IOException ex) {
                return false;
            }
        } else {
            return true;
        }
    }

    public byte[] getFileAsBytes(String filePath) {
        Path path = Paths.get(filePath);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException ex) {
        }
        return data;
    }

    public String extractFileNameFromPath(String filePath, boolean removeExtension) {
        String fileName = new File(filePath).getName();
        if (removeExtension) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }
}
