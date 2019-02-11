package com.freedomPass.project.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service("liveDataService")
public class LiveDataServiceImpl implements LiveDataService {

    @Override
    public List<Object> getLiveStatistics() {
        return (List<Object>) new Object();
    }
}
