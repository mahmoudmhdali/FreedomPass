package com.freedomPass.project.service;

import com.freedomPass.project.model.OutletCategory;
import com.freedomPass.project.model.OutletOfferType;
import java.util.List;

public interface OutletOfferTypeService {

    List<OutletOfferType> getOutletOfferTypes();

    OutletOfferType getOutletOfferType(Long id);

}
