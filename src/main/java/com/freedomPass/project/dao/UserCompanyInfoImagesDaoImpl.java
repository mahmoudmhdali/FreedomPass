package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserOutletOfferImages;
import org.springframework.stereotype.Repository;

@Repository("userCompanyInfoImagesDao")
public class UserCompanyInfoImagesDaoImpl extends AbstractDao<Long, UserCompanyInfoImages> implements UserCompanyInfoImagesDao {

    @Override
    public void deleteImage(UserCompanyInfoImages image) {
        try {
            delete(image);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyInfoImagesDao 1 on API [" + ex.getMessage() + "]", image, "");
        }
    }
}
