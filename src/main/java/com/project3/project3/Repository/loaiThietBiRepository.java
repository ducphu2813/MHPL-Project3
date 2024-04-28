package com.project3.project3.Repository;

import com.project3.project3.Model.loai_thietbi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface loaiThietBiRepository extends JpaRepository<loai_thietbi, Integer> {

    @Query("SELECT s FROM loai_thietbi s WHERE " +
            " s.ten LIKE %:query% ")
    loai_thietbi findByName(@Param("query") String query);
}
