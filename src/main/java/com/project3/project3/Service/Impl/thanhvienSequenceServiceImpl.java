package com.project3.project3.Service.Impl;

import com.project3.project3.Model.thanhvien_sequence;
import com.project3.project3.Service.thanhvienSequenceService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.thanhvienSequenceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class thanhvienSequenceServiceImpl implements thanhvienSequenceService {

    thanhvienSequenceRepository thanhvienSequenceRepository;

    public thanhvienSequenceServiceImpl(thanhvienSequenceRepository thanhvienSequenceRepository) {
        this.thanhvienSequenceRepository = thanhvienSequenceRepository;
    }

    @PostConstruct
    public void init() {
        checkAndCreateRecord();
    }
    @Override
    public Long getIndex() {
        return thanhvienSequenceRepository.getIndex();
    }

    @Override
    public void updateIndex() {
        thanhvienSequenceRepository.updateIndex();
    }

    @Override
    @Transactional
    public void checkAndCreateRecord() {
        thanhvien_sequence sequence = thanhvienSequenceRepository.findByName("index");
        if (sequence == null) {
            sequence = new thanhvien_sequence();
            sequence.setName("index");
            sequence.setNum(1L);
            thanhvienSequenceRepository.save(sequence);
        }
    }
}
