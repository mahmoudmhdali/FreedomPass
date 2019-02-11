package com.freedomPass.project.helpermodel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private HttpStatus httpStatus;
    private HttpHeaders headers;
    private ResponseBodyEntity responseBodyEntity;

    private ResponseBuilder() {
        this.httpStatus = HttpStatus.OK;
        this.headers = new HttpHeaders();
        this.responseBodyEntity = new ResponseBodyEntity();
    }

    public static ResponseBuilder getInstance() {
        return new ResponseBuilder();
    }

    public ResponseBuilder setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ResponseBuilder addHttpHeader(String headerKey, String headerValue) {
        this.headers.add(headerKey, headerValue);
        return this;
    }

    public ResponseBuilder setHttpResponseEntityResultCode(int responseCode) {
        this.responseBodyEntity.setCode(responseCode);
        this.responseBodyEntity.setDescription(ResponseCode.getDescription(responseCode));
        return this;
    }

    public ResponseBuilder setHttpResponseEntityResultDescription(String description) {
        /* Overrides the default result code description provided in ResponseCode Class */
        this.responseBodyEntity.setDescription(description);
        return this;
    }

    public ResponseBuilder addHttpResponseEntityData(String objectKey, Object data) {
        this.responseBodyEntity.addData(objectKey, data);
        return this;
    }

    public ResponseBuilder setHttpResponseEntity(ResponseBodyEntity responseBodyEntity) {
        this.responseBodyEntity = responseBodyEntity;
        return this;
    }

    public ResponseBodyEntity getHttpResponseEntity() {
        return this.responseBodyEntity;
    }

    public ResponseEntity returnClientResponse() {
        /* /!\ Called ONLY from "Controller Layers" which is responsible for returning for the front-end (Web, Mobile) /!\ */
        return new ResponseEntity(this.responseBodyEntity, this.headers, this.httpStatus);
    }

    public ResponseBodyEntity getResponse() {
        /* /!\ Called ONLY from "Service Layers" when required /!\ */
        return this.responseBodyEntity;
    }
}
