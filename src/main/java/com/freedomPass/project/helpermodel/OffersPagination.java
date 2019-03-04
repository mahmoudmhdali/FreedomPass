/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.helpermodel;

import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserProfile;
import java.util.List;

/**
 *
 * @author mahmoudmhdali
 */
public class OffersPagination {

    private int maxPages;
    private int currentPage;
    private int totalResults;
    List<UserOutletOffer> offers;

    public OffersPagination(int maxPages, int currentPage, int totalResults, List<UserOutletOffer> offers) {
        this.maxPages = maxPages;
        this.currentPage = currentPage;
        this.totalResults = totalResults;
        this.offers = offers;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<UserOutletOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<UserOutletOffer> offers) {
        this.offers = offers;
    }
}
