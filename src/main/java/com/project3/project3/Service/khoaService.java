package com.project3.project3.Service;

import com.project3.project3.Model.khoa;

import java.util.List;

public interface khoaService {
    List<khoa> getAll();

    khoa findById(Integer id);

    khoa getByName(String name);
}
