package com.project3.project3.Repository;

import com.project3.project3.Model.xuly;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface xulyRepository extends JpaRepository<xuly, Integer> {

    //tìm xử lý theo id thành viên
    List<xuly> findByThanhvienId(Long id);

    
}
