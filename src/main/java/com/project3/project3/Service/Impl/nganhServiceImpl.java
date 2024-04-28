package com.project3.project3.Service.Impl;

import com.project3.project3.Model.nganh;
import com.project3.project3.Service.nganhService;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.nganhRepository;

import java.util.List;

@Service
public class nganhServiceImpl implements nganhService {

    nganhRepository nganhRepository;

    public nganhServiceImpl(nganhRepository nganhRepository){
        this.nganhRepository = nganhRepository;
    }
    @Override
    public List<nganh> getAll() {
        return nganhRepository.findAll();
    }

    @Override
    public nganh findById(Integer id) {
        return nganhRepository.findById(id).orElse(null);
    }

    @Override
    public nganh findByName(String name) {
        return nganhRepository.getByTen(name);
    }
}
