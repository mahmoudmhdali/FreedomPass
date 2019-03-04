package com.freedomPass.project.service;

import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.project.dao.OutletOfferTypeDao;
import com.freedomPass.project.dao.UserOutletInfoDao;
import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.helpermodel.OffersPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserOutletOfferImages;
import com.freedomPass.project.model.UserProfile;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userOutletOfferService")
@Transactional
public class UserOutletOfferServiceImpl extends AbstractService implements UserOutletOfferService {

    @Autowired
    UserOutletOfferDao userOutletOfferDao;

    @Autowired
    OutletOfferTypeDao outletOfferTypeDao;

    @Autowired
    UserOutletInfoDao userOutletInfoDao;

    @Override
    public List<UserOutletOffer> getUserOutletOffers() {
        return userOutletOfferDao.getUserOutletOffers();
    }

    @Override
    public UserOutletOffer getUserOutletOffer(Long id) {
        return userOutletOfferDao.getUserOutletOffer(id);
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByType(Long type) {
        return userOutletOfferDao.getUserOutletOffersByType(type);
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByOutletId(Long id) {
        return userOutletOfferDao.getUserOutletOffersByOutletId(id);
    }

    @Override
    public OffersPagination getOffersPagination(int pageNumber, int maxRes) {
        return userOutletOfferDao.getOffersPagination(pageNumber, maxRes);
    }

    @Override
    public ResponseBodyEntity addOffer(UserOutletOffer userOutletOffer, UserOutletInfo userOutletInfo,
            MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) {
//        if (userOutletOfferDao.getUserOutletOfferByName(userOutletOffer.getName()) != null) {
//            return ResponseBuilder.getInstance()
//                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
//                    .addHttpResponseEntityData("name", "Offer Name already taken")
//                    .getResponse();
//        }
        UserProfile loggedInUser = getAuthenticatedUser();
        if (userOutletInfo == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("userOutletInfo", "Outlet is required.")
                    .getResponse();
        }
        userOutletOffer.setUserOutletInfo(userOutletInfoDao.getUserOutletInfo(userOutletInfo.getId()));
        if (userOutletOffer.getUserOutletInfo() == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("userOutletInfo", "Outlet does not exist.")
                    .getResponse();
        }

        if (userOutletOffer.getOutletOfferType() == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("outletOfferType", "Type is required.")
                    .getResponse();
        }
        userOutletOffer.setOutletOfferType(outletOfferTypeDao.getOutletOfferType(userOutletOffer.getOutletOfferType().getId()));
        if (userOutletOffer.getOutletOfferType() == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("outletOfferType", "Type does not exist.")
                    .getResponse();
        }
        Collection<UserOutletOfferImages> userOutletOfferImages = new ArrayList<>();
        if (image1 != null) {
            String extension = FilenameUtils.getExtension(image1.getOriginalFilename()).toLowerCase();
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName1", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }
        if (image2 != null) {
            String extension = FilenameUtils.getExtension(image2.getOriginalFilename()).toLowerCase();
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName2", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }
        if (image3 != null) {
            String extension = FilenameUtils.getExtension(image3.getOriginalFilename()).toLowerCase();
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName3", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }
        if (image4 != null) {
            String extension = FilenameUtils.getExtension(image4.getOriginalFilename()).toLowerCase();
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName4", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }
        if (image1 != null) {
            UserOutletOfferImages userOutletOfferImage = new UserOutletOfferImages();
            userOutletOfferImage.setFileName(image1.getOriginalFilename().replace("." + FilenameUtils.getExtension(image1.getOriginalFilename()), ""));
            userOutletOfferImage.setImageIndex(1);
            userOutletOfferImage.setPath("/OffersImages/" + loggedInUser.getId() + "-" + System.currentTimeMillis() + "-1");
            userOutletOfferImage.setUserOutletOffer(userOutletOffer);
            userOutletOfferImages.add(userOutletOfferImage);
        }
        if (image2 != null) {
            UserOutletOfferImages userOutletOfferImage = new UserOutletOfferImages();
            userOutletOfferImage.setFileName(image2.getOriginalFilename().replace("." + FilenameUtils.getExtension(image2.getOriginalFilename()), ""));
            userOutletOfferImage.setImageIndex(2);
            userOutletOfferImage.setPath("/OffersImages/" + loggedInUser.getId() + "-" + System.currentTimeMillis() + "-2");
            userOutletOfferImage.setUserOutletOffer(userOutletOffer);
            userOutletOfferImages.add(userOutletOfferImage);
        }
        if (image3 != null) {
            UserOutletOfferImages userOutletOfferImage = new UserOutletOfferImages();
            userOutletOfferImage.setFileName(image3.getOriginalFilename().replace("." + FilenameUtils.getExtension(image3.getOriginalFilename()), ""));
            userOutletOfferImage.setImageIndex(3);
            userOutletOfferImage.setPath("/OffersImages/" + loggedInUser.getId() + "-" + System.currentTimeMillis() + "-3");
            userOutletOfferImage.setUserOutletOffer(userOutletOffer);
            userOutletOfferImages.add(userOutletOfferImage);
        }
        if (image4 != null) {
            UserOutletOfferImages userOutletOfferImage = new UserOutletOfferImages();
            userOutletOfferImage.setFileName(image4.getOriginalFilename().replace("." + FilenameUtils.getExtension(image4.getOriginalFilename()), ""));
            userOutletOfferImage.setImageIndex(4);
            userOutletOfferImage.setPath("/OffersImages/" + loggedInUser.getId() + "-" + System.currentTimeMillis() + "-4");
            userOutletOfferImage.setUserOutletOffer(userOutletOffer);
            userOutletOfferImages.add(userOutletOfferImage);
        }
        userOutletOffer.setUserOutletOfferImagesCollection(userOutletOfferImages);
        userOutletOfferDao.addUser(userOutletOffer);
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("offer", userOutletOffer)
                .getResponse();
    }

}
