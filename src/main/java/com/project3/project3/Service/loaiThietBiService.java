package com.project3.project3.Service;

import com.project3.project3.Model.loai_thietbi;

import java.util.List;

public interface loaiThietBiService {

    loai_thietbi findByName(String name);

    List<loai_thietbi> findAll();

    loai_thietbi findById(Integer id);
}
