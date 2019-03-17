package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserOutletInfoImages;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserOutletInfoImagesService {

    ResponseBodyEntity editImages(UserOutletInfoImages UserOutletInfoImage, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException;

}
