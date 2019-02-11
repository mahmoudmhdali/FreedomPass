package com.freedomPass.api.io;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 *
 * @author jfaysal
 */
@Component
public class NetworkFileManager {

    @Autowired
    ContextHolder context;

    @Autowired
    LocalFileManager localFileMgr;

    private NtlmPasswordAuthentication auth;

    public String testAccessibility(String fullPath, String username, String password) {
        try {
            SmbFile dir = new SmbFile("smb://" + fullPath, new NtlmPasswordAuthentication(null, username, password));
            // create dir if not found
            if (!dir.exists()) {
                dir.mkdir();
                dir.delete();
            }
            return "0";
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), fullPath + ", " + username + ", " + password, "");
            return "1";
        }
    }

    public void createRootDirectory(String fullPath, String username, String password) {
        /* This function is called from installation Controller only!!
         * fullPath: smb://ip/Shared/Module/
         */
        try {
            SmbFile dir = new SmbFile(fullPath, new NtlmPasswordAuthentication(null, username, password));
            // create dir if not found
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), fullPath + ", " + username + ", " + password, "");
        }
    }

    public int getFileSize(String path) {
        try {
            SmbFile sFile = createSmbFile(path);
            return (int) sFile.length() / 1000;
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), path, "");
        }
        return 0;
    }

    public int getMp3FileDurationInSeconds(String path) {

        try {

            if (FilenameUtils.getExtension(path.toLowerCase()).equals("wav")) {
                localFileMgr.createDirectory(URLDecoder.decode(context.getCatalina().getCatalinaWorkDir() + "$Temporary", "UTF-8"));
                File wavFile = new File(URLDecoder.decode(context.getCatalina().getCatalinaWorkDir() + "$Temporary/temporary_" + FilenameUtils.getName(path), "UTF-8"));
                try (OutputStream os = new FileOutputStream(wavFile)) {
                    os.write(streamFileToBytes(path));
                }
                AudioFormat format;
                long frames;
                try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile)) {
                    format = audioInputStream.getFormat();
                    frames = audioInputStream.getFrameLength();
                }

                return (int) Math.ceil((frames + 0.0) / format.getFrameRate());
            }

            /* if the path is for mp2 file then do the below... */
            SmbFile sFile = createSmbFile(path);
            Map<?, ?> properties;
            try (InputStream is = sFile.getInputStream()) {
                AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(is);
                properties = ((TAudioFileFormat) fileFormat).properties();
            }
            String key = "mp3.bitrate.nominal.bps";
            Integer bitRates = (Integer) properties.get(key);
            // get file size in bytes
            double fileSize = (double) sFile.length();
            // convert file size to kbites *8
            fileSize = fileSize * 8;
            // get duration  =   size (kbits)/bitrate(kbps)
            return (int) Math.floor(fileSize / bitRates);
        } catch (MalformedURLException ex) {
            Logger.ERROR(ex.getMessage(), path, "");
        } catch (IOException | UnsupportedAudioFileException ex) {
            Logger.ERROR(ex.getMessage(), path, "");
        }
        return 0;
    }

    public byte[] streamFileToBytes(String path) {
        byte[] fileBytes = null;
        try {
            SmbFile sFile = createSmbFile(path);
            try (SmbFileInputStream sfis = new SmbFileInputStream(sFile)) {
                fileBytes = IOUtils.toByteArray(sfis);
            }
        } catch (IOException ex) {
            Logger.ERROR(ex.getMessage(), path, "");
        }
        return fileBytes;
    }

    public boolean isDirectoryOrFileExists(String directory) {
        try {
            SmbFile dir = createSmbFile(directory);
            // create dir if not found
            return dir.exists();
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), directory, "");
            return false;
        }
    }

    public void createDirectory(String directory) {
        try {
            SmbFile dir = createSmbFile(directory);
            // create dir if not found
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), directory, "");
        }
    }

    public void deleteFileOrDirectory(String directory) {
        try {
            SmbFile dir = createSmbFile(directory);
            if (dir.exists()) {
                dir.delete();
            }
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), directory, "");
        }
    }

    public void renameFileOrDirectory(String origDir, String ranameToDir) {
        try {
            SmbFile sFile = createSmbFile(origDir);
            if (isDirectoryOrFileExists(ranameToDir)) {
                deleteFileOrDirectory(ranameToDir);
            }
            sFile.renameTo(createSmbFile(ranameToDir));
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), origDir + ", " + ranameToDir, "");
        }
    }

    public void copyFileFromSrcToDest(String srcDir, String destDir) {
        try {
            SmbFile srcFile = createSmbFile(srcDir);
            srcFile.copyTo(createSmbFile(destDir + srcFile.getName()));
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), srcDir + ", " + destDir, "");
        }
    }

    public void moveFilesFromSrcToDest(String srcDir, String destDir) {
        try {
            SmbFile srcFile = createSmbFile(srcDir);
            createDirectory(srcDir);
            createDirectory(destDir);
            for (SmbFile smbFile : srcFile.listFiles()) {
                if (!smbFile.isDirectory()) {
                    smbFile.copyTo(createSmbFile(destDir + smbFile.getName()));
                    smbFile.delete();
                }
            }
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), srcDir + ", " + destDir, "");
        }
    }

    public List<String> getCSVFileNames(String directory) {

        try {
            List<String> listOfFiles = new ArrayList<>();
            SmbFile sFile = createSmbFile(directory);
            // retreive list of csv files
            for (SmbFile smbObj : sFile.listFiles()) {
                if (!smbObj.isDirectory()) {
                    if (smbObj.getName().toLowerCase().contains(".csv")) {
                        listOfFiles.add(smbObj.getName());
                        Logger.NORMAL("File Name " + smbObj.getName() + " found in " + directory, "", "");
                    }
                }
            }
            return listOfFiles;
        } catch (MalformedURLException | SmbException ex) {
            Logger.ERROR(ex.getMessage(), directory, "");
            return null;
        }
    }

    public boolean downloadFile(String fileName, String srcDir, boolean deleteFileAfterDownload) {

        boolean downloadStatus = false;
        try {
            String path = srcDir + fileName;
            SmbFile sFile = createSmbFile(path);
            SmbFileInputStream sfis = new SmbFileInputStream(sFile);
            File file = new File(context.getCatalina().getCatalinaWorkInstanceDir() + "/" + fileName);
            FileUtils.copyInputStreamToFile(sfis, file);
            if (deleteFileAfterDownload) {
                sFile.delete();
            }
            downloadStatus = true;
            Logger.NORMAL("Downloading file " + fileName + " from " + path + " done.", "", "");
        } catch (IOException ex) {
            downloadStatus = false;
            Logger.ERROR(ex.getMessage(), fileName + ", " + srcDir + ", " + deleteFileAfterDownload, "");
        }
        return downloadStatus;
    }

    public boolean downloadFileFromExternalNetworkNode(String fileName, String srcDir, String localDestDir, boolean deleteFileAfterDownload) {

        boolean downloadStatus = false;
        try {
            String path = srcDir + fileName;
            SmbFile sFile = new SmbFile(context.getEnvironment().getProperty("shared.mysql.network_smb_folder") + path, getAuthentication());
            SmbFileInputStream sfis = new SmbFileInputStream(sFile);
            File file = new File(localDestDir + fileName);
            FileUtils.copyInputStreamToFile(sfis, file);
            if (deleteFileAfterDownload) {
                sFile.delete();
            }
            downloadStatus = true;
            Logger.NORMAL("Downloading file " + fileName + " from " + path + " done.", "", "");
        } catch (IOException ex) {
            downloadStatus = false;
            Logger.ERROR(ex.getMessage(), fileName + ", " + srcDir + ", " + localDestDir + ", " + deleteFileAfterDownload, "");
        }
        return downloadStatus;
    }

    public boolean downloadFileFromMySQLNetworkNode(String fileName, String srcDir, String localDestDir, boolean deleteFileAfterDownload) {

        boolean downloadStatus = false;
        try {
            String path = srcDir + fileName;
            SmbFile sFile = new SmbFile(context.getEnvironment().getProperty("shared.mysql.network_smb_folder") + path, getMySqlServerAuthentication());
            SmbFileInputStream sfis = new SmbFileInputStream(sFile);
            File file = new File(localDestDir + fileName);
            FileUtils.copyInputStreamToFile(sfis, file);
            if (deleteFileAfterDownload) {
                sFile.delete();
            }
            downloadStatus = true;
            Logger.NORMAL("Downloading file " + fileName + " from " + path + " done.", "", "");
        } catch (IOException ex) {
            downloadStatus = false;
            Logger.ERROR(ex.getMessage(), fileName + ", " + srcDir + ", " + localDestDir + ", " + deleteFileAfterDownload, "");
        }
        return downloadStatus;
    }

    public boolean uploadFile(byte[] fileContent, String fileName, String destDir) {

        boolean uploadStatus = false;
        String path = destDir + fileName;
        try {

            SmbFile sFile = createSmbFile(path);
            try (SmbFileOutputStream sfos = new SmbFileOutputStream(sFile)) {
                sfos.write(fileContent);
                sfos.close();
            }
            uploadStatus = true;
            Logger.NORMAL("Uploading file " + fileName + " to " + path + " done.", "", "");
        } catch (IOException ex) {
            uploadStatus = false;
            Logger.ERROR(ex.getMessage(), fileName + ", " + destDir, "");
        }
        return uploadStatus;
    }

    public void copyFile(String srcFile, String desPath) throws FileNotFoundException {

        File file = new File(desPath);
        if (!file.exists()) {
            try {
                try (InputStream inStream = new FileInputStream(srcFile)) {
                    FileUtils.copyInputStreamToFile(inStream, file);
                }
            } catch (IOException ex) {
                Logger.ERROR(ex.getMessage(), srcFile + ", " + desPath, "");
            }
        }
    }

    // helper/utility functions uses inside the class only
    private NtlmPasswordAuthentication getAuthentication() {
        if (auth == null) {
            if (context.getEnvironment().getProperty("shared.username") != null) {
                auth = new NtlmPasswordAuthentication(null, context.getEnvironment().getProperty("shared.username"), context.getEnvironment().getProperty("shared.password"));
            }
        }
        return auth;
    }

    // helper/utility functions uses inside the class only
    private NtlmPasswordAuthentication getMySqlServerAuthentication() {
        if (auth == null) {
            if (context.getEnvironment().getProperty("shared.mysql.username") != null) {
                auth = new NtlmPasswordAuthentication(null, context.getEnvironment().getProperty("shared.mysql.username"), context.getEnvironment().getProperty("shared.mysql.password"));
            }
        }
        return auth;
    }

    private SmbFile createSmbFile(String path) throws MalformedURLException {
        return new SmbFile(context.getEnvironment().getProperty("shared.network_smb_folder") + path, getAuthentication());
    }
}
