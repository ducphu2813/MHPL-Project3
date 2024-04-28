package com.project3.project3.Repository;

import com.project3.project3.Model.nganh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface nganhRepository extends JpaRepository<nganh, Integer>{

    nganh getByTen(String ten);
}
