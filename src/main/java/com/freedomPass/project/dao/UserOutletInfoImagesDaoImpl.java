package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletInfoImages;
import com.freedomPass.project.model.UserOutletOfferImages;
import org.springframework.stereotype.Repository;

@Repository("userOutletInfoImagesDao")
public class UserOutletInfoImagesDaoImpl extends AbstractDao<Long, UserOutletInfoImages> implements UserOutletInfoImagesDao {

    @Override
    public void deleteImage(UserOutletInfoImages image) {
        delete(image);
    }
}
