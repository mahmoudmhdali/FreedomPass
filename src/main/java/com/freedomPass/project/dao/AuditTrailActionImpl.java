/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.model.AuditTrailAction;
import org.springframework.stereotype.Repository;

@Repository("auditTrailActionDao")
public class AuditTrailActionImpl extends AbstractDao<Long, AuditTrailAction> implements AuditTrailActionDao {

    @Override
    public AuditTrailAction getAuditTrailActionByID(Long id) {
        AuditTrailAction auditTrailAction = getByKey(id);
        return auditTrailAction;
    }
}
