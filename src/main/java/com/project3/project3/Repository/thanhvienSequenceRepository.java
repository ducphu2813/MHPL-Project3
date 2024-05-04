package com.project3.project3.Repository;

import com.project3.project3.Model.thanhvien_sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface thanhvienSequenceRepository extends JpaRepository<thanhvien_sequence, Long> {

    @Query("SELECT s.num FROM thanhvien_sequence s WHERE s.name = 'index'")
    Long getIndex();

    @Query("SELECT s FROM thanhvien_sequence s WHERE s.name = 'index'")
    thanhvien_sequence findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE thanhvien_sequence s SET s.num = s.num + 1 WHERE s.name = 'index'")
    void updateIndex();
}
