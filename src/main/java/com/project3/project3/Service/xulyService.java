package com.project3.project3.Service;

import com.project3.project3.Model.xuly;

import java.util.List;

public interface xulyService {

    List<xuly> getAll();

    xuly getById(int id);

    void saveOrUpdate(xuly xuly);

    void delete(int id);
}
