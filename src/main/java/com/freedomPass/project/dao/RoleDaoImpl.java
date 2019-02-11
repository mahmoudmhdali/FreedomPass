package com.freedomPass.project.dao;

import com.freedomPass.project.model.Role;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Long, Role> implements RoleDao {

    @Override
    public List<Role> getRoles() {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("role"))
                .add(Restrictions.ne("role", "SUPPORT")) // To avoid including Users who are of group Support
                .add(Restrictions.ne("role", "INSTALLER"));
        return (List<Role>) criteria.list();
    }

    @Override
    public Role getRole(Long id) {
        Role role = getByKey(id);
        return role;
    }

    @Override
    public Role getRole(String name) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("role", name));
        Role role = (Role) criteria.uniqueResult();
        return role;
    }

}
