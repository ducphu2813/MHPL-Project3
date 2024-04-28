package com.project3.project3.Service;

import com.project3.project3.Model.hinhthuc_xuly;

import java.util.List;

public interface hinhthucXuLyService {

    List<hinhthuc_xuly> getAll();

    hinhthuc_xuly findByHinhthuc(String hinhthuc);

    hinhthuc_xuly findById(Integer id);
}
