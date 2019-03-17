package com.freedomPass.project.service;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.dao.OutletOfferTypeDao;
import com.freedomPass.project.dao.UserCompanyInfoImagesDao;
import com.freedomPass.project.dao.UserOutletInfoDao;
import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.dao.UserOutletOfferImagesDao;
import com.freedomPass.project.helpermodel.OffersPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserOutletOfferImages;
import com.freedomPass.project.model.UserProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userCompanyInfoImagesService")
@Transactional
public class UserCompanyInfoImagesServiceImpl extends AbstractService implements UserCompanyInfoImagesService {

    @Autowired
    UserCompanyInfoImagesDao userCompanyInfoImagesDao;

    @Autowired
    ContextHolder context;

    @Override
    public ResponseBodyEntity editImages(UserCompanyInfoImages UserCompanyInfoImage, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException {
        UserProfile user = getAuthenticatedUser();
        if (user != null) {
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
            String locationfile = context.getCatalina().getCatalinaWorkInstanceDir() + "/profile_photos";
            Path dir = Paths.get(locationfile);
            int numberOfNotAlLowed = 0;
            Collection<UserCompanyInfoImages> userCompanyInfoImages = new ArrayList<>();
            Collection<UserCompanyInfoImages> persistantUserCompanyInfoImages = user.getUserCompanyInfo().getUserCompanyInfoImagesCollection();
            Collection<UserCompanyInfoImages> toKeepImages = new ArrayList<>();
            if (image1 != null) {
                String imageExtension = FilenameUtils.getExtension(image1.getOriginalFilename());
                if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                    try {
                        String fileName = user.getId() + "-" + System.currentTimeMillis() + "-1." + imageExtension;
                        Path originalFile = dir.resolve(fileName);
                        Files.copy(image1.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                        UserCompanyInfoImages userCompanyInfoImage = new UserCompanyInfoImages();
                        userCompanyInfoImage.setFileName(image1.getOriginalFilename().replace("." + FilenameUtils.getExtension(image1.getOriginalFilename()), ""));
                        userCompanyInfoImage.setImageIndex(1);
                        userCompanyInfoImage.setPath("/profile_photos/" + fileName);
                        userCompanyInfoImage.setUserCompanyInfo(user.getUserCompanyInfo());
                        userCompanyInfoImages.add(userCompanyInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 1 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image1 == null && !UserCompanyInfoImage.getImageName1().equals("")) {
                for (UserCompanyInfoImages image : persistantUserCompanyInfoImages) {
                    if (image.getImageIndex() == 1) {
                        userCompanyInfoImages.add(image);
                        toKeepImages.add(image);
                    }
                }
            }
            if (image2 != null) {
                String imageExtension = FilenameUtils.getExtension(image2.getOriginalFilename());
                if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                    try {
                        String fileName = user.getId() + "-" + System.currentTimeMillis() + "-2." + imageExtension;
                        Path originalFile = dir.resolve(fileName);
                        Files.copy(image2.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                        UserCompanyInfoImages userCompanyInfoImage = new UserCompanyInfoImages();
                        userCompanyInfoImage.setFileName(image2.getOriginalFilename().replace("." + FilenameUtils.getExtension(image2.getOriginalFilename()), ""));
                        userCompanyInfoImage.setImageIndex(2);
                        userCompanyInfoImage.setPath("/profile_photos/" + fileName);
                        userCompanyInfoImage.setUserCompanyInfo(user.getUserCompanyInfo());
                        userCompanyInfoImages.add(userCompanyInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editPass 2 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image2 == null && !UserCompanyInfoImage.getImageName2().equals("")) {
                for (UserCompanyInfoImages image : persistantUserCompanyInfoImages) {
                    if (image.getImageIndex() == 2) {
                        userCompanyInfoImages.add(image);
                        toKeepImages.add(image);
                    }
                }
            }
            if (image3 != null) {
                String imageExtension = FilenameUtils.getExtension(image3.getOriginalFilename());
                if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                    try {
                        String fileName = user.getId() + "-" + System.currentTimeMillis() + "-3." + imageExtension;
                        Path originalFile = dir.resolve(fileName);
                        Files.copy(image3.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                        UserCompanyInfoImages userCompanyInfoImage = new UserCompanyInfoImages();
                        userCompanyInfoImage.setFileName(image3.getOriginalFilename().replace("." + FilenameUtils.getExtension(image3.getOriginalFilename()), ""));
                        userCompanyInfoImage.setImageIndex(3);
                        userCompanyInfoImage.setPath("/profile_photos/" + fileName);
                        userCompanyInfoImage.setUserCompanyInfo(user.getUserCompanyInfo());
                        userCompanyInfoImages.add(userCompanyInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editPass 3 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image3 == null && !UserCompanyInfoImage.getImageName3().equals("")) {
                for (UserCompanyInfoImages image : persistantUserCompanyInfoImages) {
                    if (image.getImageIndex() == 3) {
                        userCompanyInfoImages.add(image);
                        toKeepImages.add(image);
                    }
                }
            }
            if (image4 != null) {
                String imageExtension = FilenameUtils.getExtension(image4.getOriginalFilename());
                if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                    try {
                        String fileName = user.getId() + "-" + System.currentTimeMillis() + "-4." + imageExtension;
                        Path originalFile = dir.resolve(fileName);
                        Files.copy(image4.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                        UserCompanyInfoImages userCompanyInfoImage = new UserCompanyInfoImages();
                        userCompanyInfoImage.setFileName(image4.getOriginalFilename().replace("." + FilenameUtils.getExtension(image4.getOriginalFilename()), ""));
                        userCompanyInfoImage.setImageIndex(4);
                        userCompanyInfoImage.setPath("/profile_photos/" + fileName);
                        userCompanyInfoImage.setUserCompanyInfo(user.getUserCompanyInfo());
                        userCompanyInfoImages.add(userCompanyInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editPass 4 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image4 == null && !UserCompanyInfoImage.getImageName4().equals("")) {
                for (UserCompanyInfoImages image : persistantUserCompanyInfoImages) {
                    if (image.getImageIndex() == 4) {
                        userCompanyInfoImages.add(image);
                        toKeepImages.add(image);
                    }
                }
            }

            for (UserCompanyInfoImages image : persistantUserCompanyInfoImages) {
                if (!toKeepImages.contains(image)) {
                    Path originalFile = dir.resolve(image.getPath().replace("/profile_photos/", ""));
                    try {
                        Files.delete(originalFile);
                        userCompanyInfoImagesDao.deleteImage(image);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editPass 5 on API [" + ex.getMessage() + "]", image, "");
                    }
                }
            }

            user.getUserCompanyInfo().setUserCompanyInfoImagesCollection(userCompanyInfoImages);
            if (numberOfNotAlLowed > 0) {
                return ResponseBuilder.getInstance().
                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                        .addHttpResponseEntityData("validation", numberOfNotAlLowed + " not uploaded images. Reason: extension not valid.")
                        .getResponse();
            } else {
                return ResponseBuilder.getInstance().
                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                        .addHttpResponseEntityData("offer", "Success")
                        .getResponse();
            }
        } else {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SOURCE_NOT_FOUND)
                    .addHttpResponseEntityData("Message", "User not found")
                    .getResponse();
        }
    }

}
