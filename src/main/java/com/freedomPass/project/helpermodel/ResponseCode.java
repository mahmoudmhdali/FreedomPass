package com.freedomPass.project.helpermodel;

public class ResponseCode {

    public static int SUCCESS = 0;
    public static int MISSING_PARAMETERS = -1;
    public static int PARAMETERS_VALIDATION_ERROR = -2;
    public static int UNIQUE_CONSTRAINT_VIOLATION = -3;
    public static int ENTITY_NOT_FOUND = -4;
    public static int EXCEPTION_OCCURED = -5;
    public static int UNAUTHORIZED_USER_ACTION = -6;
    public static int WRONG_USER_PASSWORD = -7;
    public static int ALERT = -8;
    public static int MANDATORY_FIELDS_MISSING = -9;
    public static int NOT_NUMERIC = -10;
    public static int NOT_FOUND_PARAMETERS = -11;
    public static int SUBSCRIBER_NOT_FOUND = -12;
    public static int LANGUAGE_NOT_FOUND = -13;
    public static int NO_BLACKLIST_FOUND = -14;
    public static int ERROR_UPDATING_SETTINGS = -15;
    public static int ERROR_ACTIVATING_MCA = -16;
    public static int ERROR_ACTIVATING_CA = -17;
    public static int ERROR_DEACTIVATING_MCA = -18;
    public static int ERROR_DEACTIVATING_CA = -19;
    public static int INVALID_MSISDN = -20;
    public static int INVALID_LENGTH_MSISDN = -21;
    public static int MSISDN_ALREADY_BLACKLISTED = -22;
    public static int INVALID_INPUT = -23;
    public static int BLOCKED_DISABLED = -24;
    public static int SOURCE_NOT_FOUND = -25;
    public static int USER_NOT_FOUND = -26;

    public static String getDescription(int code) {
        if (code == SUCCESS) {
            return "Success";
        } else if (code == MISSING_PARAMETERS) {
            return "Missing Parameters";
        } else if (code == PARAMETERS_VALIDATION_ERROR) {
            return "Parameters Validation Error";
        } else if (code == UNIQUE_CONSTRAINT_VIOLATION) {
            return "Unique constraint violation";
        } else if (code == UNIQUE_CONSTRAINT_VIOLATION) {
            return "Entity not found";
        } else if (code == EXCEPTION_OCCURED) {
            return "EXCEPTION OCCURED";
        } else if (code == UNAUTHORIZED_USER_ACTION) {
            return "UnAuthorized User Action";
        } else if (code == WRONG_USER_PASSWORD) {
            return "wrong user password";
        } else if (code == ALERT) {
            return "Generic Error";
        } else if (code == MANDATORY_FIELDS_MISSING) {
            return "Mandatory Fields are Missing";
        } else if (code == NOT_NUMERIC) {
            return "Parameters should be Numeric";
        } else if (code == NOT_FOUND_PARAMETERS) {
            return "Parameters sent are not found";
        } else if (code == SUBSCRIBER_NOT_FOUND) {
            return "Subscriber Not Found";
        } else if (code == LANGUAGE_NOT_FOUND) {
            return "Language Not Found";
        } else if (code == NO_BLACKLIST_FOUND) {
            return "No Blacklist Found";
        } else if (code == ERROR_UPDATING_SETTINGS) {
            return "Error Updating Settings";
        } else if (code == ERROR_ACTIVATING_MCA) {
            return "Error Activating MCA";
        } else if (code == ERROR_ACTIVATING_CA) {
            return "Error Activating CA";
        } else if (code == ERROR_DEACTIVATING_MCA) {
            return "Error Deactivating MCA";
        } else if (code == ERROR_DEACTIVATING_CA) {
            return "Error Deactivating CA";
        } else if (code == INVALID_MSISDN) {
            return "Invalid MSISDN";
        } else if (code == INVALID_LENGTH_MSISDN) {
            return "Invalid MSISDN Length";
        } else if (code == MSISDN_ALREADY_BLACKLISTED) {
            return "MSISDN Already Blacklisted";
        } else if (code == ENTITY_NOT_FOUND) {
            return "Entity Not Found";
        } else if (code == INVALID_INPUT) {
            return "Invalid Input";
        } else if (code == BLOCKED_DISABLED) {
            return "The Blocked Number Feature is disabled";
        } else if (code == SOURCE_NOT_FOUND) {
            return "Source Not Found";
        } else if (code == USER_NOT_FOUND) {
            return "User Not Found";
        }

        return "unknown error code";
    }
}
