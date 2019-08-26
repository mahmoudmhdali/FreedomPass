package com.freedomPass.project.service;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.dao.AdminPassesDao;
import com.freedomPass.project.helpermodel.AdminPassesPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("adminPassesService")
@Transactional
public class AdminPassesServiceImpl extends AbstractService implements AdminPassesService {

    @Autowired
    AdminPassesDao adminPassesDao;

    @Autowired
    ContextHolder context;

    @Autowired
    UserOutletOfferService userOutletOfferService;

    @Override
    public List<AdminPasses> getAdminPasses(boolean isCorporate) {
        return adminPassesDao.getAdminPasses(isCorporate);
    }

    @Override
    public List<AdminPasses> getAdminPassesForUsers() {
        return adminPassesDao.getAdminPassesForUsers();
    }

    @Override
    public List<AdminPasses> getAdminPassesByOfferID(Long offerID) {
        return adminPassesDao.getAdminPassesByOfferID(offerID);
    }

    @Override
    public AdminPassesPagination getAdminPassesPagination(int pageNumber, int maxRes, boolean isCorporate) {
        return adminPassesDao.getAdminPassesPagination(pageNumber, maxRes, isCorporate);
    }

    @Override
    public AdminPasses getAdminPasse(Long id) {
        return adminPassesDao.getAdminPasse(id);
    }

    @Override
    public ResponseBodyEntity addPass(AdminPasses adminPass, MultipartFile image1) throws IOException {
//        if (userOutletOfferDao.getUserOutletOfferByName(userOutletOffer.getName()) != null) {
//            return ResponseBuilder.getInstance()
//                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
//                    .addHttpResponseEntityData("name", "Offer Name already taken")
//                    .getResponse();
//        }
        UserProfile loggedInUser = getAuthenticatedUser();
        if (image1 != null) {
            String extension = FilenameUtils.getExtension(image1.getOriginalFilename()).toLowerCase();
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName1", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }
        String locationfile = context.getCatalina().getCatalinaWorkInstanceDir() + "/PackagesImages";
        Path dir = Paths.get(locationfile);
        if (image1 != null) {
            String imageExtension = FilenameUtils.getExtension(image1.getOriginalFilename());
            if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                try {
                    String fileName = loggedInUser.getId() + "-" + System.currentTimeMillis() + "-1." + imageExtension;
                    Path originalFile = dir.resolve(fileName);
                    Files.copy(image1.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                    adminPass.setFileName(image1.getOriginalFilename().replace("." + FilenameUtils.getExtension(image1.getOriginalFilename()), ""));
                    adminPass.setImagePath("/PackagesImages/" + fileName);
                } catch (Exception ex) {
                    Logger.ERROR("1- Error addPass 1 on API [" + ex.getMessage() + "]", "", "");
                }
            } else {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("imageName1", "Please upload .jpg .jpeg or png images only.")
                        .getResponse();
            }
        }

        Collection<UserOutletOffer> userOutletOffers = new ArrayList<>();
        for (UserOutletOffer userOutletOffer : adminPass.getUserOutletOfferCollection()) {
            userOutletOffers.add(userOutletOfferService.getUserOutletOffer(userOutletOffer.getId()));
        }
        adminPass.setUserOutletOfferCollection(userOutletOffers);

        adminPassesDao.addAdminPasse(adminPass);
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("package", "Success adding package")
                .getResponse();
    }

    @Override
    public ResponseBodyEntity editPass(AdminPasses adminPass, MultipartFile image1) throws IOException {
        AdminPasses persistantAdminPasse = adminPassesDao.getAdminPasse(adminPass.getId());
        if (persistantAdminPasse != null) {
            UserProfile loggedInUser = getAuthenticatedUser();
            persistantAdminPasse.setName(adminPass.getName());
            persistantAdminPasse.setValidity(adminPass.getValidity());
            persistantAdminPasse.setCorporateOnly(adminPass.getCorporateOnly());
            persistantAdminPasse.setPrice(adminPass.getPrice());
            persistantAdminPasse.setDescription(adminPass.getDescription());
            if (image1 != null) {
                String extension = FilenameUtils.getExtension(image1.getOriginalFilename()).toLowerCase();
                if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                    return ResponseBuilder.getInstance()
                            .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                            .addHttpResponseEntityData("imageName1", "Please upload .jpg .jpeg or png images only.")
                            .getResponse();
                }
            }

            Collection<UserOutletOffer> userOutletOffers = new ArrayList<>();
            for (UserOutletOffer userOutletOffer : adminPass.getUserOutletOfferCollection()) {
                userOutletOffers.add(userOutletOfferService.getUserOutletOffer(userOutletOffer.getId()));
            }

            persistantAdminPasse.setUserOutletOfferCollection(userOutletOffers);

            String locationfile = context.getCatalina().getCatalinaWorkInstanceDir() + "/PackagesImages";
            Path dir = Paths.get(locationfile);
            if (image1 != null) {
                String toRemoveImage = persistantAdminPasse.getImagePath();
                String imageExtension = FilenameUtils.getExtension(image1.getOriginalFilename());
                String fileName = loggedInUser.getId() + "-" + System.currentTimeMillis() + "-1." + imageExtension;
                Path originalFile = dir.resolve(fileName);
                Files.copy(image1.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                persistantAdminPasse.setFileName(image1.getOriginalFilename().replace("." + FilenameUtils.getExtension(image1.getOriginalFilename()), ""));
                persistantAdminPasse.setImagePath("/PackagesImages/" + fileName);
                if (toRemoveImage != null) {
                    Path oldFile = dir.resolve(toRemoveImage.replace("/PackagesImages/", ""));
                    Files.delete(oldFile);
                }
            } else if (image1 == null && adminPass.getImageName1().equals("")) {
                String toRemoveImage = persistantAdminPasse.getImagePath();
                persistantAdminPasse.setFileName(null);
                persistantAdminPasse.setImagePath(null);
                if (toRemoveImage != null) {
                    Path oldFile = dir.resolve(toRemoveImage.replace("/PackagesImages/", ""));
                    try {
                        Files.delete(oldFile);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editPass 1 on API [" + ex.getMessage() + "]", oldFile, "");
                    }
                }
            }
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Package", "Success editing package")
                    .getResponse();
        } else {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SOURCE_NOT_FOUND)
                    .addHttpResponseEntityData("Message", "Package not found")
                    .getResponse();
        }
    }

    @Override
    public ResponseBodyEntity deletePackage(Long id) {
        AdminPasses persistantAdminPasse = adminPassesDao.getAdminPasse(id);
        if (persistantAdminPasse == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription("Package Not Found")
                    .getResponse();
        }

        persistantAdminPasse.setDeletedDate(new Date());

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntityResultDescription("Package Deleted successfully")
                .getResponse();
    }

}
