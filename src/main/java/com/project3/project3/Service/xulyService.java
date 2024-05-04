package com.project3.project3.Service;

import com.project3.project3.Model.xuly;

import java.util.List;

public interface xulyService {

    List<xuly> getAll();

    List<xuly> getXulyFalse();

    List<xuly> getXulyTrue();

    List<xuly> getByThanhvienId(Long id);

    List<xuly> getXulyFalseByThanhvien(Long id);

    xuly getById(int id);

    void saveOrUpdate(xuly xuly);

    void delete(int id);

    void nullifyThanhvienInXuly(Long id);

    void deleteByThanhvien(Long id);
}
