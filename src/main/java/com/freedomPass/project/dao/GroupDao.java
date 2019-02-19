
package com.freedomPass.project.dao;

import com.freedomPass.project.model.Group;
import java.util.List;

public interface GroupDao {

    List<Group> getGroups();

    Group getGroup(Long id);

    Group getGroupForAdd(Long id);

    Group getGroup(String name);

    Group addGroup(Group group);
    
    void deleteGroup(Group group);
    
    Integer deleteSelection(List<Long> id);

}
