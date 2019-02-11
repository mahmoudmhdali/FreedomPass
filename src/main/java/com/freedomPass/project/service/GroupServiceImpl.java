package com.freedomPass.project.service;

import com.freedomPass.project.dao.GroupDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Group;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupService")
@Transactional
public class GroupServiceImpl extends AbstractService implements GroupService {

    @Autowired
    GroupDao groupDao;

    @Autowired
    MessageSource messageSource;

    @Override
    public List<Group> getGroups() {
        return groupDao.getGroups();
    }

    @Override
    public ResponseBodyEntity getGroup(Long id) {
        Group group = groupDao.getGroup(id);
        if (group == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.notFound", null))
                    .getResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("group", group)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity getGroup(String name) {
        Group group = groupDao.getGroup(name);
        if (group == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.notFound", null))
                    .getResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("group", group)
                .getResponse();
    }

    @Override
    public Group toGroup(Long id) {
        return groupDao.getGroup(id);
    }

    @Override
    public Group isUnique(String name) {
        return groupDao.getGroup(name);
    }

    @Override
    public ResponseBodyEntity addGroup(Group group) {
        /* Check if group name is unique */
        if (isUnique(group.getName()) != null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("name", this.getMessageBasedOnLanguage("group.nametaken", null))
                    .getResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("group", groupDao.addGroup(group))
                .getResponse();
    }

    @Override
    public ResponseBodyEntity updateGroup(Group group) {

        Group persistantGroup = groupDao.getGroup(group.getId());
        if (persistantGroup == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.unknown", null))
                    .getResponse();
        }

        if (!persistantGroup.getName().equals(group.getName())) {
            if (isUnique(group.getName()) != null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("name", this.getMessageBasedOnLanguage("group.nametaken", null))
                        .getResponse();
            }
        }

        if (group.getRoleCollection() == null || group.getRoleCollection().size() == 0) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("roleCollection", this.getMessageBasedOnLanguage("group.atleastonerole", null))
                    .getResponse();
        }

        persistantGroup.setName(group.getName());
        persistantGroup.setDescription(group.getDescription());
        persistantGroup.setRoleCollection(group.getRoleCollection());
        persistantGroup.setReportCollection(group.getReportCollection());

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("group", persistantGroup)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteGroup(Long id) {
        Group persistantGroup = groupDao.getGroup(id);
        if (persistantGroup == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.notFound", null))
                    .getResponse();
        }

        // Prohibit deletion of groups joined by users
        if (userService.filterUsersByGroup(id).size() > 0) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.isUsed", null))
                    .getResponse();
        }

        // Prohibit deletion of Support and Installer Groups
        if (persistantGroup.getRoleCollection().stream()
                .filter(p -> p.getRole().equals("SUPPORT"))
                .collect(Collectors.toList()).size() > 0
                || persistantGroup.getRoleCollection().stream()
                .filter(p -> p.getRole().equals("INSTALLER"))
                .collect(Collectors.toList()).size() > 0) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.defaultNoDelete", null))
                    .getResponse();
        }

        persistantGroup.setDeletedDate(new Date());

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.deletedSuccessfully", null))
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteSelection(List<Long> id) {

        Integer deletedGroups = groupDao.deleteSelection(id);

        // if all groups were deleted...
        if (deletedGroups == id.size()) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.deletedsuccess", null))
                    .addHttpResponseEntityData("group", this.getMessageBasedOnLanguage("group.deletedsuccess", null))
                    .getResponse();
        } // Not all groups were deleted
        else if (deletedGroups > 0) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                    .setHttpResponseEntityResultDescription("(" + deletedGroups + "/" + id.size() + ") " + this.getMessageBasedOnLanguage("group.numberDeletedSuccess", null))
                    .getResponse();

        }
        // None of the groups were deleted
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("group.noGroupDeleted", null))
                .getResponse();
    }
}
