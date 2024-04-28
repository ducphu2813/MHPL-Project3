package com.project3.project3.Service.Impl;

import com.project3.project3.Service.thanhvienSequenceService;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.thanhvienSequenceRepository;

@Service
public class thanhvienSequenceServiceImpl implements thanhvienSequenceService {

    thanhvienSequenceRepository thanhvienSequenceRepository;

    public thanhvienSequenceServiceImpl(thanhvienSequenceRepository thanhvienSequenceRepository) {
        this.thanhvienSequenceRepository = thanhvienSequenceRepository;
    }
    @Override
    public Long getIndex() {
        return thanhvienSequenceRepository.getIndex();
    }

    @Override
    public void updateIndex() {
        thanhvienSequenceRepository.updateIndex();
    }
}
