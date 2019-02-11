package com.freedomPass.project.helpermodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResponseBodyEntity {

    @JsonIgnore
    private ObjectMapper mapper;
    private int code;
    private String description;
    private ObjectNode data;

    public ResponseBodyEntity(int code, String description, ObjectNode data) {
        this.code = code;
        this.description = description;
        this.data = data;
    }

    public ResponseBodyEntity() {
        super();
        mapper = new ObjectMapper();
        data = mapper.createObjectNode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }

    public void addData(String key, Object value) {
        data.putPOJO(key, value);
    }

    @Override
    public String toString() {
        try {
            return "{ \"code\": " + code + ", \"description\" : \"" + description + "\", \"data\": " + mapper.writeValueAsString(data) + " }";
        } catch (JsonProcessingException e) {
            return "";
        }

    }
}
