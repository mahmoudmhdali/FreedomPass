/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons;

import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.io.LocalFileManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class Logger {

    //public static final int LOG_TYPE_ALARM = 1;
    public static final int LOG_TYPE_ERROR = 1;
    public static final int LOG_TYPE_BRIEF = 2;
    public static final int LOG_TYPE_NORMAL = 3;
    public static final int LOG_TYPE_DEBUG = 4;
    public static int LOG_LEVEL = 1; // can be changed from http endpoint
    public static boolean ENABLE_HOURLY_LOGS = false; // can be changed from http endpoint

    private static String logRootDirName;
    private static String logRootPath = "";
    private static final boolean byPassLog = true;
    /* Buffering Logs */
    private static boolean immediatFlush = false;
    private static HashMap<String, StringBuilder> bufferedLog = new HashMap<String, StringBuilder>();
    private static HashMap<String, StringBuilder> flushedBufferedLog = new HashMap<String, StringBuilder>();
    private static int allowedIdleTimeBeforeFlush = 10;
    private static ScheduledExecutorService logBufferTimer;
    private static LocalFileManager localFileMgr = new LocalFileManager();


    /* Buffering Logs */
    public void setApplicationRootPath(String logRootPath) {
        Logger.logRootPath = logRootPath;
    }

    public void setApplicationRootDirName(String logRootDirName) {
        Logger.logRootDirName = logRootDirName;
    }

    public static int ERROR(Object outputMessage, Object inputData, String subDirectory) {
        return byPassLog ? writeLogData(outputMessage.toString(), inputData.toString(), LOG_TYPE_ERROR, subDirectory) : 0;
    }

    public static int DEBUG(Object outputMessage, Object inputData, String subDirectory) {
        return byPassLog ? writeLogData(outputMessage.toString(), inputData.toString(), LOG_TYPE_DEBUG, subDirectory) : 0;
    }

    public static int BRIEF(Object outputMessage, Object inputData, String subDirectory) {
        return byPassLog ? writeLogData(outputMessage.toString(), inputData.toString(), LOG_TYPE_BRIEF, subDirectory) : 0;
    }

    public static int NORMAL(Object outputMessage, Object inputData, String subDirectory) {
        return byPassLog ? writeLogData(outputMessage.toString(), inputData.toString(), LOG_TYPE_NORMAL, subDirectory) : 0;
    }

    private static int writeLogData(String outputMessage, String inputData, int logType, String subDirectory) {
        StackTraceElement[] functions = Thread.currentThread().getStackTrace();
        try {
            if (!logRootPath.equals("")) {
                String mainPath = logRootPath + "/" + Utils.getFormattedDateAsString("yyyy-MM-dd");
                localFileMgr.createDirectory(mainPath);
                String hours = ENABLE_HOURLY_LOGS ? " HH" : "";
                String fileLogType = "";
                boolean writeLog = false;
                if (logType == LOG_TYPE_DEBUG && logType <= LOG_LEVEL) {
                    fileLogType = "DEBUG";
                    writeLog = true;
                } else if (logType == LOG_TYPE_NORMAL && logType <= LOG_LEVEL) {
                    fileLogType = "NORMAL";
                    writeLog = true;
                } else if (logType == LOG_TYPE_BRIEF && logType <= LOG_LEVEL) {
                    fileLogType = "BRIEF";
                    writeLog = true;
                } else if (logType == LOG_TYPE_ERROR && logType <= LOG_LEVEL) {
                    fileLogType = "ERRORS";
                    writeLog = true;
                }
                if (writeLog) {
                    if (!subDirectory.equals("")) {
                        localFileMgr.createDirectory(mainPath + "/" + subDirectory);
                        subDirectory = "/" + subDirectory;
                    }
                    String path = mainPath + subDirectory + "/" + logRootDirName + "_" + fileLogType + "_" + Utils.getFormattedDateAsString("dd-MM-yyyy" + hours + "") + ".log";
                    inputData = inputData.equals("") ? inputData : " ==> Input[" + inputData + "]";
                    outputMessage = outputMessage.equals("") ? outputMessage : " ==> Output[" + outputMessage + "]";
                    if (logType == LOG_TYPE_ERROR || immediatFlush) {
                        log_data_to_file(path, Utils.getFormattedDateAsString("dd-MM-yyyy HH:mm:ss") + " ==> Class: " + functions[3].getClassName() + " ==> Method: " + functions[3].getMethodName() + inputData + outputMessage);
                    } else {
                        start_log_buffer_timer();
                        append_to_buffer(path, Utils.getFormattedDateAsString("dd-MM-yyyy HH:mm:ss") + " ==> Class: " + functions[3].getClassName() + " ==> Method: " + functions[3].getMethodName() + inputData + outputMessage);
                    }
                }
            }
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    private static void start_log_buffer_timer() {
        if (logBufferTimer == null) {
            logBufferTimer = Executors.newSingleThreadScheduledExecutor();
            logBufferTimer.scheduleAtFixedRate(() -> flush_buffer(), 0, allowedIdleTimeBeforeFlush, TimeUnit.SECONDS);
        }
    }

    @SuppressWarnings("unchecked")
    private static void flush_buffer() {
        synchronized (bufferedLog) {
            flushedBufferedLog.clear();
            flushedBufferedLog = (HashMap<String, StringBuilder>) bufferedLog.clone();
            bufferedLog.clear();
        }

        for (Map.Entry<String, StringBuilder> entry : flushedBufferedLog.entrySet()) {
            log_data_to_file(entry.getKey(), entry.getValue().toString());
        }
    }

    private static void append_to_buffer(String file_path, String data) {
        synchronized (bufferedLog) {
            if (bufferedLog.containsKey(file_path)) {
                bufferedLog.put(file_path, bufferedLog.get(file_path).append("\n" + data));
            } else {
                bufferedLog.put(file_path, new StringBuilder().append(data));
            }
        }
    }

    private static void log_data_to_file(String path, String data) {
        try {
            FileWriter fstream = new FileWriter(path, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);
            out.newLine();

            out.close();
            fstream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
    }
}
