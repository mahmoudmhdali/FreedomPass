package com.freedomPass.project.dao;

import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoImpl extends AbstractDao<Long, Group> implements GroupDao {

    @Override
    public List<Group> getGroups() {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("id"))
                .createAlias("userProfileCollection", "userProfile", JoinType.LEFT_OUTER_JOIN)
                //                .add(Restrictions.isNull("userProfile.deletedDate"))
                .add(Restrictions.isNull("deletedDate"))
                .add(Restrictions.ne("name", "Installer Group"))
                .add(Restrictions.ne("name", "Support Group"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        List<Group> groups = criteria.list();
        for (Group group : groups) {
            Hibernate.initialize(group.getRoleCollection());
            Hibernate.initialize(group.getReportCollection());
            List list = new ArrayList(group.getUserProfileCollection());
            Collection<UserProfile> users = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (((UserProfile) list.get(i)).getDeletedDate() == null) {
                    users.add((UserProfile) list.get(i));
                }
            }
            group.setUserProfileCollection(users);
        }
        return groups;
    }

    @Override
    public Group getGroup(Long id) {
        Group group = getByKey(id);
        if (group == null) {
            return null;
        }
        if (group.getDeletedDate() != null) {
            return null;
        }
        if (group != null) {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("id", group.getId()))
                    .createAlias("userProfileCollection", "userProfile", JoinType.LEFT_OUTER_JOIN)
                    //                    .add(Restrictions.isNull("userProfile.deletedDate"))
                    .add(Restrictions.isNull("deletedDate"))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
            group = (Group) criteria.uniqueResult();
            Hibernate.initialize(group.getRoleCollection());
            Hibernate.initialize(group.getReportCollection());
        }
        return group;
    }

    @Override
    public Group getGroupForAdd(Long id) {
        Group group = getByKey(id);
        if (group == null) {
            return null;
        }
        if (group.getDeletedDate() != null) {
            return null;
        }
        return group;
    }

    @Override
    public Group getGroup(String name) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("name", name))
                .add(Restrictions.isNull("deletedDate"));
        Group group = (Group) criteria.uniqueResult();
        if (group != null) {
            return getGroup(group.getId());
        }
        return null;
    }

    @Override
    public Group addGroup(Group group) {
        persist(group);
        return group;
    }

    @Override
    public void deleteGroup(Group group) {
        delete(group);
    }

    @Override
    public Integer deleteSelection(List<Long> ids) {
        StringBuilder commaSeperatedIds = new StringBuilder();
        for (Long each : ids) {
            if (commaSeperatedIds.length() > 0) {
                commaSeperatedIds.append(",").append(each);
            } else {
                commaSeperatedIds.append(each);
            }
        }

        String dbDateFunction = "now()";
        if (environment.getProperty("hibernate.dialect").contains("Oracle")) {
            dbDateFunction = "sysdate";
        }
        Integer totalFetchedData;

        totalFetchedData = createSqlQuery("UPDATE TBL_GROUPS SET DELETED_DATE = " + dbDateFunction + " WHERE "
                + "ID IN (" + commaSeperatedIds + ") AND "
                + "ID NOT IN (SELECT GROUP_ID FROM TBL_USER_PROFILE_GROUPS INNER JOIN TBL_USER_PROFILES on "
                + "TBL_USER_PROFILE_GROUPS.USER_PROFILE_ID=TBL_USER_PROFILES.ID "
                + "WHERE TBL_USER_PROFILES.DELETED_DATE IS NULL)").executeUpdate();

        return totalFetchedData;
    }
}
