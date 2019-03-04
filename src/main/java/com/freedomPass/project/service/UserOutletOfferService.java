package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.OffersPagination;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserProfile;
import java.util.List;
import javax.mail.internet.AddressException;
import org.springframework.web.multipart.MultipartFile;

public interface UserOutletOfferService {

    List<UserOutletOffer> getUserOutletOffers();

    UserOutletOffer getUserOutletOffer(Long id);

    List<UserOutletOffer> getUserOutletOffersByType(Long type);

    List<UserOutletOffer> getUserOutletOffersByOutletId(Long id);

    OffersPagination getOffersPagination(int pageNumber, int maxRes);

    ResponseBodyEntity addOffer(UserOutletOffer userOutletOffer, UserOutletInfo userOutletInfo, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4);

}
