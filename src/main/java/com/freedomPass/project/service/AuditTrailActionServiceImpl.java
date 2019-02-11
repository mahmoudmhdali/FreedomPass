package com.freedomPass.project.service;

import com.freedomPass.project.dao.AuditTrailActionDao;
import com.freedomPass.project.model.AuditTrailAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("auditTrailActionService")
@Transactional
public class AuditTrailActionServiceImpl implements AuditTrailActionService {

    @Autowired
    AuditTrailActionDao auditTrailActionDao;

    @Override
    public AuditTrailAction getAuditTrailActionByID(Long id) {
        return auditTrailActionDao.getAuditTrailActionByID(id);
    }

}
