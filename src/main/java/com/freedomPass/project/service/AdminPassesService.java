package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.AdminPassesPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.AdminPasses;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AdminPassesService {

    List<AdminPasses> getAdminPasses(boolean isCorporate);
    
    List<AdminPasses> getAdminPassesForUsers();

    AdminPassesPagination getAdminPassesPagination(int pageNumber, int maxRes, boolean isCorporate);

    AdminPasses getAdminPasse(Long id);

    ResponseBodyEntity addPass(AdminPasses adminPass, MultipartFile image1) throws IOException;

    ResponseBodyEntity editPass(AdminPasses adminPass, MultipartFile image1) throws IOException;
    
    List<AdminPasses> getAdminPassesByOfferID(Long offerID);
    
    ResponseBodyEntity deletePackage(Long id);

}
