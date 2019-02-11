package com.freedomPass.project.helpermodel;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class DropDown {

    private Object OPTIONID;
    private Object OPTIONNAME;

    public Object getOptionId() {
        return OPTIONID;
    }

    public void setOptionId(Object OPTIONID) {
        this.OPTIONID = OPTIONID;
    }

    public Object getOptionName() {
        return OPTIONNAME;
    }

    public void setOptionName(Object OPTIONNAME) {
        this.OPTIONNAME = OPTIONNAME;
    }

}
