/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.helpermodel;

import java.util.Date;

/**
 *
 * @author mahmoudmhdali
 */
public class UserOffersUsed {

    private Long offerID;
    private int maxNumberOfUsage;
    private int numberOfUsedTimes;
    private Long offerType;
    private Date resetDate;

    public Long getOfferID() {
        return offerID;
    }

    public void setOfferID(Long offerID) {
        this.offerID = offerID;
    }

    public int getMaxNumberOfUsage() {
        return maxNumberOfUsage;
    }

    public void setMaxNumberOfUsage(int maxNumberOfUsage) {
        this.maxNumberOfUsage = maxNumberOfUsage;
    }

    public int getNumberOfUsedTimes() {
        return numberOfUsedTimes;
    }

    public void setNumberOfUsedTimes(int numberOfUsedTimes) {
        this.numberOfUsedTimes = numberOfUsedTimes;
    }

    public Long getOfferType() {
        return offerType;
    }

    public void setOfferType(Long offerType) {
        this.offerType = offerType;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

}
