package com.freedomPass.api.configuration.spirng;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.io.LocalFileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ContextHolder context;

    @Autowired
    LocalFileManager localFileMgr;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            File propertyFile = new File(URLDecoder.decode(this.getClass().getResource("/application.properties").getFile(), "UTF-8"));
            HashMap<String, String> propertyFileMap = Utils.propertyFileToMap(propertyFile);
            context.getCatalina().setApplicationNameDeployed(propertyFileMap.get("application.name"));
            context.getLogger().setApplicationRootPath(context.getCatalina().getCatalinaLogInstanceDir());
            context.getLogger().setApplicationRootDirName(context.getCatalina().getApplicationNameDeployed());
        } catch (UnsupportedEncodingException ex) {
        }

        Logger.NORMAL("******** API Started up by Tomcat ********", "", "");

        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaLogInstanceDir())) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaLogInstanceDir());
            Logger.NORMAL("******** API Log Directory created ********", "", "");
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir())) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir());
            Logger.NORMAL("******** API Work Directory created ********", "", "");
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/config")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/config");
            try {
                localFileMgr.copyFile(URLDecoder.decode(this.getClass().getResource("/application.properties").getFile(), "UTF-8"), context.getCatalina().getCatalinaWorkInstanceDir() + "/config/application.properties");
            } catch (FileNotFoundException ex) {
                Logger.ERROR("1- Error occured on API Startup [" + ex.getMessage() + "]", "", "");
            } catch (UnsupportedEncodingException ex) {
                Logger.ERROR("1- Error occured on API Startup [" + ex.getMessage() + "]", "", "");
            }
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/profile_photos")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/profile_photos");
            try {
                localFileMgr.copyFile(URLDecoder.decode(this.getClass().getResource("/user.png").getFile(), "UTF-8"), context.getCatalina().getCatalinaWorkInstanceDir() + "/profile_photos/user.png");
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Logger.ERROR("1- Error occured on API Startup [" + ex.getMessage() + "]", "", "");
            }
        }

        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/Excel")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/Excel");
        }

        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/QRCodes")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/QRCodes");
        }

        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/OffersImages")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/OffersImages");
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/MySQLDumps")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/MySQLDumps");
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/API")) {
            localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/API");
        }
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaWorkInstanceDir() + "/sqlcl")) {
            try {
                localFileMgr.createDirectory(context.getCatalina().getCatalinaWorkInstanceDir() + "/sqlcl");
                FileUtils.copyDirectory(new File(URLDecoder.decode(this.getClass().getResource("/sqlcl").getPath(), "UTF-8")),
                        new File(context.getCatalina().getCatalinaWorkInstanceDir() + "/sqlcl"), true);

                //COPY FILES FROM BIN AND LIB
            } catch (IOException ex) {
                Logger.ERROR("1- Error occured on API Startup [" + ex.getMessage() + "]", "", "");
            }

        }
        try {
            FileUtils.copyDirectory(new File(URLDecoder.decode(this.getClass().getResource("/sqlcl/bin/").getPath(), "UTF-8")),
                    new File(context.getCatalina().getCatalinaWorkInstanceDir() + "/sqlcl/bin/"), true);
        } catch (IOException ex) {
            Logger.ERROR("1- Error occured on API Startup [" + ex.getMessage() + "]", "", "");
        }
    }
}
