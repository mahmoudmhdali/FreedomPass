package com.freedomPass.project.model;

import com.freedomPass.api.commons.utils.Utils;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SequenceIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        return System.identityHashCode(object) + Utils.generateUniqueInteger();
    }
}
