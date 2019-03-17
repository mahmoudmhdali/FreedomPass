package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.model.UserOutletOfferImages;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferImagesDao")
public class UserOutletOfferImagesDaoImpl extends AbstractDao<Long, UserOutletOfferImages> implements UserOutletOfferImagesDao {

    @Override
    public void deleteImage(UserOutletOfferImages image) {
        try {
            delete(image);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferImagesDao 1 on API [" + ex.getMessage() + "]", image, "");
        }
    }
}
