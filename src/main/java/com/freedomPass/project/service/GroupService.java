package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.Group;
import java.util.List;

public interface GroupService {

    List<Group> getGroups();
    
    ResponseBodyEntity getGroup(Long id);
    
    ResponseBodyEntity getGroup(String name); 
    
    Group toGroup(Long id);
    
    Group toGroupForAdd(Long id);
    
    Group isUnique(String name);
    
    ResponseBodyEntity addGroup(Group group);
    
    ResponseBodyEntity updateGroup(Group group);
    
    ResponseBodyEntity deleteGroup(Long id);
    
    ResponseBodyEntity deleteSelection(List<Long> id);
}
