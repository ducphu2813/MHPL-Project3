package com.project3.project3.Service.Impl;

import com.project3.project3.Model.loai_thietbi;
import com.project3.project3.Service.loaiThietBiService;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.loaiThietBiRepository;
import java.util.List;

@Service
public class loaiThietBiServiceImpl implements loaiThietBiService {

    loaiThietBiRepository loaiThietBiRepository;

    public loaiThietBiServiceImpl(loaiThietBiRepository loaiThietBiRepository) {
        this.loaiThietBiRepository = loaiThietBiRepository;
    }

    @Override
    public loai_thietbi findByName(String name) {
        return loaiThietBiRepository.findByName(name);
    }

    @Override
    public List<loai_thietbi> findAll() {
        return loaiThietBiRepository.findAll();
    }

    @Override
    public loai_thietbi findById(Integer id) {
        return loaiThietBiRepository.findById(id).orElse(null);
    }
}
