package com.project3.project3.Service.Impl;

import com.project3.project3.Model.hinhthuc_xuly;
import com.project3.project3.Service.hinhthucXuLyService;
import com.project3.project3.Repository.hinhthucXulyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class hinhthucXuLyServiceImpl implements hinhthucXuLyService{

    hinhthucXulyRepository hinhthucXuLyRepository;

    public hinhthucXuLyServiceImpl(hinhthucXulyRepository hinhthucXuLyRepository){
        this.hinhthucXuLyRepository = hinhthucXuLyRepository;
    }

    @Override
    public List<hinhthuc_xuly> getAll() {
        return hinhthucXuLyRepository.findAll();
    }

    @Override
    public hinhthuc_xuly findByHinhthuc(String hinhthuc) {
        return hinhthucXuLyRepository.getByHinhthuc(hinhthuc);
    }

    @Override
    public hinhthuc_xuly findById(Integer id) {
        return hinhthucXuLyRepository.findById(id).orElse(null);
    }
}
