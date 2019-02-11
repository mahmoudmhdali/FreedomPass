package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.GroupSelection;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.GroupService;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController extends AbstractController {

    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity getGroups() {

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("groups", groupService.getGroups())
                .returnClientResponse();

    }

    @GetMapping("/{id}")
    public ResponseEntity getGroupById(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(groupService.getGroup(id))
                .returnClientResponse();
    }

    @GetMapping("/view")
    public ResponseEntity getGroupByName(@RequestParam("name") String name) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(groupService.getGroup(name))
                .returnClientResponse();
    }

    @PostMapping("/add")
    public ResponseEntity addGroup(@ModelAttribute @Valid Group group, BindingResult groupBindingResults) {

        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(groupService.addGroup(group))
                .returnClientResponse();
    }

    @PostMapping("/update")
    public ResponseEntity updateGroup(@ModelAttribute @Valid Group group, BindingResult groupBindingResults) {

        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntity(groupService.updateGroup(group))
                .returnClientResponse();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(groupService.deleteGroup(id))
                .returnClientResponse();
    }

    @DeleteMapping("/deleteSelection")
    public ResponseEntity deleteGroups(@ModelAttribute GroupSelection groupSelection) {

        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());

        if (groupSelection == null || groupSelection.getId() == null || groupSelection.getId().size() == 0) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("group", messageSource.getMessage("group.nogroupselection", null, locale))
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(groupService.deleteSelection(groupSelection.getId()))
                .returnClientResponse();
    }
}
