package com.freedomPass.project.service;

import com.freedomPass.project.dao.OutletCategoryDao;
import com.freedomPass.project.model.OutletCategory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("outletCategoryService")
@Transactional
public class OutletCategoryServiceImpl extends AbstractService implements OutletCategoryService {

    @Autowired
    OutletCategoryDao outletCategoryDao;

    @Override
    public List<OutletCategory> getOutletCategories() {
        return outletCategoryDao.getOutletCategories();
    }

}
