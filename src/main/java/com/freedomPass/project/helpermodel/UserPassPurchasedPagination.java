/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.helpermodel;

import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;

/**
 *
 * @author mahmoudmhdali
 */
public class UserPassPurchasedPagination {

    private int maxPages;
    private int currentPage;
    private int totalResults;
    List<UserPassPurchased> userPassPurchased;

    public UserPassPurchasedPagination(int maxPages, int currentPage, int totalResults, List<UserPassPurchased> userPassPurchased) {
        this.maxPages = maxPages;
        this.currentPage = currentPage;
        this.totalResults = totalResults;
        this.userPassPurchased = userPassPurchased;
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

    public List<UserPassPurchased> getUserPassPurchased() {
        return userPassPurchased;
    }

    public void setUserPassPurchased(List<UserPassPurchased> userPassPurchased) {
        this.userPassPurchased = userPassPurchased;
    }
}
