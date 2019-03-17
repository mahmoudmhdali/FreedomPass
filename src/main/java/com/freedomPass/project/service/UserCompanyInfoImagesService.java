package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserCompanyInfoImages;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserCompanyInfoImagesService {

    ResponseBodyEntity editImages(UserCompanyInfoImages UserCompanyInfoImage, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException;

}
