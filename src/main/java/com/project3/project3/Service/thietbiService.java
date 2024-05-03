package com.project3.project3.Service;

import com.project3.project3.DTO.ThietBiDTO;
import com.project3.project3.Model.thietbi;

import java.util.List;

public interface thietbiService {

    thietbi findByName(String name);

    List<thietbi> findAll();

    List<thietbi> findAvailable();

    List<thietbi> findByTenContaining(String name);

    List<thietbi> findNotBorrowedByName(String name);

    List<thietbi> findBorrowedByName(String name);

    thietbi findById(Integer id);

    void save(ThietBiDTO tbDto);

    void deleteById(Integer id);
}
