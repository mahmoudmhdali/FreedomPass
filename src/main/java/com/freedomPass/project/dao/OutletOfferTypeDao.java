package com.freedomPass.project.dao;

import com.freedomPass.project.model.OutletOfferType;
import java.util.List;

public interface OutletOfferTypeDao {

    List<OutletOfferType> getOutletOfferTypes();

    OutletOfferType getOutletOfferType(Long id);

}
