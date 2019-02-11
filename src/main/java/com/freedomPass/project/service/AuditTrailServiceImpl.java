package com.freedomPass.project.service;

import com.freedomPass.project.dao.AuditTrailDao;
import com.freedomPass.project.model.AuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("auditTrailService")
@Transactional
public class AuditTrailServiceImpl implements AuditTrailService {

    @Autowired
    AuditTrailDao auditTrailDao;

    @Override
    public void saveAuditTrail(AuditTrail auditTrail) {
        auditTrailDao.save(auditTrail);
    }

}
