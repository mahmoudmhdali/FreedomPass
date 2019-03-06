package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOfferImages;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferImagesDao")
public class UserOutletOfferImagesDaoImpl extends AbstractDao<Long, UserOutletOfferImages> implements UserOutletOfferImagesDao {

    @Override
    public void deleteImage(UserOutletOfferImages image) {
        delete(image);
    }
}
