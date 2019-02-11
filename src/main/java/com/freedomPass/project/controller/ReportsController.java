/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Report;
import com.freedomPass.project.service.ReportStyleService;
import com.freedomPass.project.service.ReportsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController extends AbstractController {

    @Autowired
    ReportsService reportsService;

    @Autowired
    ReportStyleService reportStyleService;

    @GetMapping()
    public ResponseEntity getReports() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("reports", reportsService.getReports())
                .returnClientResponse();
    }

    @GetMapping("/roles")
    public ResponseEntity getReportsBasedOnRoles() {
        List<Report> reports = reportsService.getReportsBasedOnRoles();
        for (Report report : reports) {
            report.setGroupCollection(null);
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("reports", reports)
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getReport(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("report", reportsService.getReport(id))
                .returnClientResponse();
    }

    @PostMapping("/add")
    public ResponseEntity addReport(@ModelAttribute @Valid Report report, BindingResult groupBindingResults) {

        String[] byPassFields = new String[1];
        if (report.getReportFilters() == null) {
            byPassFields[0] = "reportFilters";
        }
        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, byPassFields);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(reportsService.addReport(report))
                .returnClientResponse();
    }

    @PostMapping("/edit")
    public ResponseEntity editReport(@ModelAttribute @Valid Report report, BindingResult groupBindingResults) {

        String[] byPassFields = new String[1];
        if (report.getReportFilters() == null) {
            byPassFields[0] = "reportFilters";
        }
        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, byPassFields);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(reportsService.editReport(report))
                .returnClientResponse();
    }

    @GetMapping("/styles")
    public ResponseEntity getReportStyles() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("styles", reportStyleService.getReportStyles())
                .returnClientResponse();
    }

    @GetMapping("/getByProcID/{procID}/{pageNumber}/{offset}/{withCounter}/{withLimit}")
    public ResponseEntity getProcResultByID(@PathVariable Long procID, @PathVariable Integer pageNumber, @PathVariable Integer offset, @PathVariable Integer withCounter, @PathVariable Integer withLimit, HttpServletRequest httpRequest) throws SQLException, Exception {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("result", reportsService.callStoredProcedure(procID, pageNumber, offset, withCounter, httpRequest.getParameterMap(), withLimit))
                .returnClientResponse();
    }

    @GetMapping("/exportByProcID/{procID}")
    public void download(@PathVariable Long procID, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException, ParseException, SQLException {
        String filePath = reportsService.exportToExcel(procID, httpRequest.getParameterMap());
        //File file = new File(this.getClass().getResource("/Sample-CSV-List.csv").getFile());
        File file = new File(URLDecoder.decode(filePath, "UTF-8"));
        InputStream is = new FileInputStream(file);

        // MIME type of the file
        response.setContentType("application/octet-stream");
        // Response header
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + file.getName() + "\"");
        // Read from the file and write into the response
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
        Files.delete(Paths.get(filePath));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReport(@PathVariable Long id) {

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(reportsService.deleteReport(id))
                .returnClientResponse();
    }

}
