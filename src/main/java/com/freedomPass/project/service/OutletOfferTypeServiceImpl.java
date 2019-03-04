package com.freedomPass.project.service;

import com.freedomPass.project.dao.OutletCategoryDao;
import com.freedomPass.project.dao.OutletOfferTypeDao;
import com.freedomPass.project.model.OutletCategory;
import com.freedomPass.project.model.OutletOfferType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("outletOfferTypeService")
@Transactional
public class OutletOfferTypeServiceImpl extends AbstractService implements OutletOfferTypeService {

    @Autowired
    OutletOfferTypeDao outletOfferTypeDao;

    @Override
    public List<OutletOfferType> getOutletOfferTypes() {
        return outletOfferTypeDao.getOutletOfferTypes();
    }

    @Override
    public OutletOfferType getOutletOfferType(Long id) {
        return outletOfferTypeDao.getOutletOfferType(id);
    }

}
