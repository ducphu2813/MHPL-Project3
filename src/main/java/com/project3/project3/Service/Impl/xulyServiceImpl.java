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
    public List<xuly> getXulyFalse() {
        return xulyRepository.findByTrangthaiFalse();
    }

    @Override
    public List<xuly> getXulyTrue() {
        return xulyRepository.findByTrangthaiTrue();
    }

    @Override
    public List<xuly> getByThanhvienId(Long id) {
        return xulyRepository.findByThanhvienId(id);
    }

    //tìm những xử lý chưa được xử lý theo thành viên
    @Override
    public List<xuly> getXulyFalseByThanhvien(Long id) {
        return xulyRepository.findByThanhvienIdAndTrangthaiFalse(id);
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
