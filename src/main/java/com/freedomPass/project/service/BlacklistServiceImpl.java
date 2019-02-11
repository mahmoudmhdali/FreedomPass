package com.freedomPass.project.service;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.engine.SettingsEngine;
import com.freedomPass.project.dao.BlacklistDao;
import com.freedomPass.project.dao.UserDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfileCredentials;
import com.freedomPass.project.model.Blacklist;
import com.freedomPass.project.model.UserProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("blacklistService")
@Transactional
public class BlacklistServiceImpl extends AbstractService implements BlacklistService {

    @Autowired
    @Qualifier("blacklistDao")
    BlacklistDao blacklistDao;

    @Autowired
    @Qualifier("userDao")
    UserDao userDao;

    @Autowired
    GetSettingsService getsettingsservice;

    @Autowired
    ContextHolder context;

    @Autowired
    SettingsEngine settingsEngine;

    @Override
    public List<Blacklist> getBlacklists(String source) {
        return blacklistDao.getBlacklists(source);
    }

    @Override
    public Blacklist getBlacklistByMSISDN(String MSISDN) {
        return blacklistDao.filterByMobileNumber(MSISDN);
    }

    @Override
    public ResponseBodyEntity addBlacklist(Blacklist blacklist, String Source) {
        //check if msisdn is duplicated
        UserProfileCredentials u = new UserProfileCredentials();
        UserProfile user = null;
        if (Source.equals("0")) {
            user = u.getAuthenticatedUser();
        } else {
            user = userDao.getUser(Long.valueOf(2));
        }
        if (!StringUtils.isNumeric(blacklist.getmsisdn())) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("msisdn", this.getMessageBasedOnLanguage("blacklist.invalidmobilenumber", null))
                    .getResponse();
        }
        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        if (blacklist.getmsisdn().length() != msisdnLength) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("msisdn", this.getMessageBasedOnLanguage("blacklist.numberlengthinvalid", null))
                    .getResponse();
        }
        if (blacklistDao.filterByMobileNumber(blacklist.getmsisdn()) != null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("msisdn", this.getMessageBasedOnLanguage("blacklist.numberalreadyblacklisted", null))
                    .getResponse();
        }
        blacklist.setmsisdn(blacklist.getmsisdn());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Date date1;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            blacklist.setdateblacklisted(date1);
        } catch (ParseException ex) {
            Logger.getLogger(BlacklistServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        blacklist.setbwflag(false);
        blacklist.setuserProfileId(user);
        blacklistDao.addBlacklist(blacklist);
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("blacklist", blacklist)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteBlacklist(Long id) {
        Blacklist persistantblacklist = blacklistDao.getBlacklist(id);
        if (persistantblacklist == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("blacklist.notFound", null))
                    .getResponse();
        }
        blacklistDao.deleteBlacklist(persistantblacklist);
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("blacklist.deletedSuccess", null))
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteBlacklist(String msisdn, String Source) {
        Blacklist persistantblacklist = blacklistDao.filterByMobileNumber(msisdn);
        if (persistantblacklist == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("blacklist.notFound", null))
                    .getResponse();
        }
        blacklistDao.deleteBlacklist(persistantblacklist);
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("blacklist.deletedSuccess", null))
                .getResponse();
    }

    @Override
    public Object[] store(MultipartFile file) {

        int existingcount = 0;
        int addedcount = 0;
        int invalidcount = 0;
        Object[] returnedObject = new Object[3];
        List<Blacklist> blacklistslist = new ArrayList<>();
        String locationfile = context.getCatalina().getCatalinaWorkInstanceDir() + "/Excel";
        Path dir = Paths.get(locationfile);
        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        try {
            Path originalFile = dir.resolve(file.getOriginalFilename() + "-" + new Date().getTime());
            Files.copy(file.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
            if (originalFile.toString().contains(".csv")) {
                try (Scanner blacklists = new Scanner(originalFile)) {
                    blacklists.useDelimiter("\r\n");
                    while (blacklists.hasNext()) {
                        String msisdn = blacklists.next();
                        Blacklist blacklist = new Blacklist();
                        blacklist.setmsisdn(msisdn);
                        blacklist.setdateblacklisted(new Date());
                        blacklist.setbwflag(false);
                        blacklist.setuserProfileId(getAuthenticatedUser());
                        if (msisdn.equals("")) {
                            invalidcount = invalidcount + 1;

                        } else if (msisdn.length() != msisdnLength) {
                            invalidcount = invalidcount + 1;
                        } else if (!isNumber(msisdn)) {
                            invalidcount = invalidcount + 1;
                        } else if (blacklistDao.filterByMobileNumber(msisdn) != null) {
                            existingcount = existingcount + 1;
                        } else {
                            blacklistDao.addBlacklist(blacklist);
                            blacklistslist.add(blacklist);
                            addedcount = addedcount + 1;
                        }

                    }
                }
                Files.delete(originalFile);
            }

            returnedObject[0] = originalFile.toString();
            returnedObject[1] = existingcount + "@#@" + addedcount + "@#@" + invalidcount;
            returnedObject[2] = blacklistslist;

            return returnedObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isNumber(String string) {
        try {
            Long.parseLong(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Object[] delete(MultipartFile file) {
        int notexistingcount = 0;
        int deletedcount = 0;
        int invalidcount = 0;
        Object[] returnedObject = new Object[3];
        List<Blacklist> blacklistslist = new ArrayList<>();
        String locationfile = context.getCatalina().getCatalinaWorkInstanceDir() + "/Excel";
        Path dir = Paths.get(locationfile);
        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        try {
            Path originalFile = dir.resolve(file.getOriginalFilename() + "-" + new Date().getTime());
            Files.copy(file.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
            if (originalFile.toString().contains(".csv")) {
                try (Scanner blacklists = new Scanner(originalFile)) {
                    blacklists.useDelimiter("\r\n");
                    while (blacklists.hasNext()) {
                        String msisdn = blacklists.next();

                        if (msisdn.equals("")) {
                            invalidcount = invalidcount + 1;

                        } else if (msisdn.length() != msisdnLength) {
                            invalidcount = invalidcount + 1;
                        } else if (!isNumber(msisdn)) {
                            invalidcount = invalidcount + 1;
                        } else {
                            Blacklist btobedeleted = blacklistDao.filterByMobileNumber(msisdn);
                            if (btobedeleted == null) {
                                notexistingcount = notexistingcount + 1;
                            } else {
                                blacklistDao.deleteBlacklist(btobedeleted);
                                blacklistslist.add(btobedeleted);
                                deletedcount = deletedcount + 1;
                            }
                        }

                    }
                }
                Files.delete(originalFile);
            }

            returnedObject[0] = originalFile.toString();
            returnedObject[1] = notexistingcount + "@#@" + deletedcount + "@#@" + invalidcount;
            returnedObject[2] = blacklistslist;
            return returnedObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
