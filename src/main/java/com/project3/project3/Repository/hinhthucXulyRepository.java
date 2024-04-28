package com.project3.project3.Repository;

import com.project3.project3.Model.hinhthuc_xuly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface hinhthucXulyRepository extends JpaRepository<hinhthuc_xuly, Integer> {

    hinhthuc_xuly getByHinhthuc(String hinhthuc);
}
