package com.freedomPass.project.service;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.dao.OutletOfferTypeDao;
import com.freedomPass.project.dao.UserOutletInfoImagesDao;
import com.freedomPass.project.dao.UserOutletInfoDao;
import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.dao.UserOutletOfferImagesDao;
import com.freedomPass.project.helpermodel.OffersPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserOutletInfoImages;
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

@Service("userOutletInfoImagesService")
@Transactional
public class UserOutletInfoImagesServiceImpl extends AbstractService implements UserOutletInfoImagesService {

    @Autowired
    UserOutletInfoImagesDao userOutletInfoImagesDao;

    @Autowired
    ContextHolder context;

    @Override
    public ResponseBodyEntity editImages(UserOutletInfoImages UserOutletInfoImage, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException {
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
            Collection<UserOutletInfoImages> userOutletInfoImages = new ArrayList<>();
            Collection<UserOutletInfoImages> persistantUserOutletInfoImages = user.getUserOutletInfo().getUserOutletInfoImagesCollection();
            Collection<UserOutletInfoImages> toKeepImages = new ArrayList<>();
            if (image1 != null) {
                String imageExtension = FilenameUtils.getExtension(image1.getOriginalFilename());
                if (imageExtension.toLowerCase().equals("jpg") || imageExtension.toLowerCase().equals("jpeg") || imageExtension.toLowerCase().equals("png")) {
                    try {
                        String fileName = user.getId() + "-" + System.currentTimeMillis() + "-1." + imageExtension;
                        Path originalFile = dir.resolve(fileName);
                        Files.copy(image1.getInputStream(), originalFile, StandardCopyOption.REPLACE_EXISTING);
                        UserOutletInfoImages userOutletInfoImage = new UserOutletInfoImages();
                        userOutletInfoImage.setFileName(image1.getOriginalFilename().replace("." + FilenameUtils.getExtension(image1.getOriginalFilename()), ""));
                        userOutletInfoImage.setImageIndex(1);
                        userOutletInfoImage.setPath("/profile_photos/" + fileName);
                        userOutletInfoImage.setUserOutletInfo(user.getUserOutletInfo());
                        userOutletInfoImages.add(userOutletInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 1 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image1 == null && !UserOutletInfoImage.getImageName1().equals("")) {
                for (UserOutletInfoImages image : persistantUserOutletInfoImages) {
                    if (image.getImageIndex() == 1) {
                        userOutletInfoImages.add(image);
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
                        UserOutletInfoImages userOutletInfoImage = new UserOutletInfoImages();
                        userOutletInfoImage.setFileName(image2.getOriginalFilename().replace("." + FilenameUtils.getExtension(image2.getOriginalFilename()), ""));
                        userOutletInfoImage.setImageIndex(2);
                        userOutletInfoImage.setPath("/profile_photos/" + fileName);
                        userOutletInfoImage.setUserOutletInfo(user.getUserOutletInfo());
                        userOutletInfoImages.add(userOutletInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 2 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image2 == null && !UserOutletInfoImage.getImageName2().equals("")) {
                for (UserOutletInfoImages image : persistantUserOutletInfoImages) {
                    if (image.getImageIndex() == 2) {
                        userOutletInfoImages.add(image);
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
                        UserOutletInfoImages userOutletInfoImage = new UserOutletInfoImages();
                        userOutletInfoImage.setFileName(image3.getOriginalFilename().replace("." + FilenameUtils.getExtension(image3.getOriginalFilename()), ""));
                        userOutletInfoImage.setImageIndex(3);
                        userOutletInfoImage.setPath("/profile_photos/" + fileName);
                        userOutletInfoImage.setUserOutletInfo(user.getUserOutletInfo());
                        userOutletInfoImages.add(userOutletInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 3 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image3 == null && !UserOutletInfoImage.getImageName3().equals("")) {
                for (UserOutletInfoImages image : persistantUserOutletInfoImages) {
                    if (image.getImageIndex() == 3) {
                        userOutletInfoImages.add(image);
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
                        UserOutletInfoImages userOutletInfoImage = new UserOutletInfoImages();
                        userOutletInfoImage.setFileName(image4.getOriginalFilename().replace("." + FilenameUtils.getExtension(image4.getOriginalFilename()), ""));
                        userOutletInfoImage.setImageIndex(4);
                        userOutletInfoImage.setPath("/profile_photos/" + fileName);
                        userOutletInfoImage.setUserOutletInfo(user.getUserOutletInfo());
                        userOutletInfoImages.add(userOutletInfoImage);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 4 on API [" + ex.getMessage() + "]", "", "");
                    }
                } else {
                    numberOfNotAlLowed++;
                }
            } else if (image4 == null && !UserOutletInfoImage.getImageName4().equals("")) {
                for (UserOutletInfoImages image : persistantUserOutletInfoImages) {
                    if (image.getImageIndex() == 4) {
                        userOutletInfoImages.add(image);
                        toKeepImages.add(image);
                    }
                }
            }

            for (UserOutletInfoImages image : persistantUserOutletInfoImages) {
                if (!toKeepImages.contains(image)) {
                    Path originalFile = dir.resolve(image.getPath().replace("/profile_photos/", ""));
                    try {
                        Files.delete(originalFile);
                        userOutletInfoImagesDao.deleteImage(image);
                    } catch (Exception ex) {
                        Logger.ERROR("1- Error editImages 1 on API [" + ex.getMessage() + "]", image, "");
                    }
                }
            }

            user.getUserOutletInfo().setUserOutletInfoImagesCollection(userOutletInfoImages);
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
