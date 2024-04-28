package com.project3.project3.Service.Impl;

import com.project3.project3.Model.khoa;
import com.project3.project3.Service.khoaService;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.khoaRepository;

import java.util.List;

@Service
public class khoaServiceImpl implements khoaService {

    khoaRepository khoaRepository;

    public khoaServiceImpl(khoaRepository khoaRepository){
        this.khoaRepository = khoaRepository;
    }
    @Override
    public List<khoa> getAll() {
        return khoaRepository.findAll();
    }

    @Override
    public khoa findById(Integer id) {
        return khoaRepository.findById(id).orElse(null);
    }

    @Override
    public khoa getByName(String name) {
        return khoaRepository.findByName(name);
    }
}
