package com.project3.project3.Repository;

import com.project3.project3.Model.khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface khoaRepository extends JpaRepository<khoa, Integer>{

    @Query("SELECT s FROM khoa s WHERE " +
            " s.ten LIKE %:query% ")
    khoa findByName(@Param("query") String query);
}
