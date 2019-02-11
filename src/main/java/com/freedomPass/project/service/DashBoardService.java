package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.DashBoard;
import java.sql.SQLException;
import java.util.List;

public interface DashBoardService {

    /**
     *
     * @return all counters
     */
    List<DashBoard> getCounters() throws SQLException, Exception;

    /**
     *
     * @return counters by type
     */
    List<DashBoard> getCountersByType(Long id);

    /**
     *
     * @param key the key of the counter
     * @return {@link com.freedomPass.MCAAPI.model.DashBoard}
     */
    ResponseBodyEntity getCountersBykey(String key);

}
