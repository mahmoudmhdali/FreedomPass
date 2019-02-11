package com.freedomPass.project.service;

import com.freedomPass.project.dao.DashBoardDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.DashBoard;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dashboardService")
@Transactional
public class DashBoardServiceImpl extends AbstractService implements DashBoardService {

    @Autowired
    @Qualifier("dashboardDao")
    DashBoardDao dashboardDao;

    @Override
    public List<DashBoard> getCounters() throws Exception {
        return dashboardDao.getCounters();
    }

    @Override
    public List<DashBoard> getCountersByType(Long id) {
        List<DashBoard> s = dashboardDao.getCountersByType(id);
        if (s == null) {
            return null;
        }
        return s;
    }

    @Override
    public ResponseBodyEntity getCountersBykey(String key) {
        DashBoard dash = dashboardDao.getCountersByKey(key);
        if (dash == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("dashboard.counterNotFound", null))
                    .getResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("counters", dash)
                .getResponse();
    }

}
