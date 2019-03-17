package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserOutletOfferImages;
import org.springframework.stereotype.Repository;

@Repository("userCompanyInfoImagesDao")
public class UserCompanyInfoImagesDaoImpl extends AbstractDao<Long, UserCompanyInfoImages> implements UserCompanyInfoImagesDao {

    @Override
    public void deleteImage(UserCompanyInfoImages image) {
        delete(image);
    }
}
