package com.project3.project3.Service.Impl;

import com.project3.project3.Model.xuly;
import com.project3.project3.Service.xulyService;
import com.project3.project3.Repository.xulyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class xulyServiceImpl implements xulyService {

    xulyRepository xulyRepository;

    public xulyServiceImpl(xulyRepository xulyRepository) {
        this.xulyRepository = xulyRepository;
    }

    @Override
    public List<xuly> getAll() {
        return xulyRepository.findAll();
    }

    @Override
    public xuly getById(int id) {
        return xulyRepository.findById(id).orElse(null);
    }

    @Override
    public void saveOrUpdate(xuly xuly) {
        xulyRepository.save(xuly);
    }

    @Override
    public void delete(int id) {
        xulyRepository.deleteById(id);
    }
}
