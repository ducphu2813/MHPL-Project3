package com.project3.project3.Service;

import com.project3.project3.Model.nganh;

import java.util.List;

public interface nganhService {

    List<nganh> getAll();

    nganh findById(Integer id);

    nganh findByName(String ten);
}
