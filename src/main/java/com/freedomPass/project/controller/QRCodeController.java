//package com.freedomPass.project.controller;
//
//import com.freedomPass.api.commons.ContextHolder;
//import com.freedomPass.api.commons.utils.QRCodeGenerator;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.QRCodeWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/QRCode")
//public class QRCodeController {
//
//    @Autowired
//    ContextHolder context;
//
//    @Autowired
//    QRCodeGenerator qRCodeGenerator;
//
//    @GetMapping("/generateQRCode")
//    public String generateQRCode() throws WriterException, IOException {
//        qRCodeGenerator.generateQRCodeImage("This is my first QR Code", 350, 350);
//        return "DONE";
//    }
//
//    @GetMapping("/getGenerateQRCode")
//    public byte[] getGenerateQRCode() throws WriterException, IOException {
//        return getQRCodeImage("This is my first QR Code", 350, 350);
//    }
//
//    private byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//        byte[] pngData = pngOutputStream.toByteArray();
//        return pngData;
//    }
//}
