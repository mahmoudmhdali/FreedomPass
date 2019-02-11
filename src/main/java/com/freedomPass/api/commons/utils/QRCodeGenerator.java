/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons.utils;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.io.LocalFileManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoudmhdali
 */
@Component
public class QRCodeGenerator {

    @Autowired
    ContextHolder context;

    @Autowired
    LocalFileManager localFileMgr;

    public void generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        String qrPath = context.getCatalina().getCatalinaWorkInstanceDir() + "/QRCodes/123.png";
        localFileMgr.createDirectory(qrPath);
        Path path = FileSystems.getDefault().getPath(qrPath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

}
