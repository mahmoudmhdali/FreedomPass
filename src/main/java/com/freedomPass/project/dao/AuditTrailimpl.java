
package com.freedomPass.project.dao;

import com.freedomPass.project.model.AuditTrail;
import org.springframework.stereotype.Repository;

@Repository("auditTrailDao")
public class AuditTrailimpl extends AbstractDao<Long, AuditTrail> implements AuditTrailDao {

    @Override
    public void save(AuditTrail auditTrail) {
        persist(auditTrail);
    }
}
