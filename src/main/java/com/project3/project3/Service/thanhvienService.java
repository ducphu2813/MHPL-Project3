package com.project3.project3.Service;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.thanhvien;

import java.time.LocalDateTime;
import java.util.List;

public interface thanhvienService {

    List<thanhvien> getAll();

    thanhvien findById(Long id);

    thanhvien findByEmail(String email);

    thanhvien findByTen(String ten);

    ThanhVienDTO modelToDTO(thanhvien tv);

    thanhvien dtoToModel (ThanhVienDTO tvDTO);

    thanhvien save(ThanhVienDTO tvDTO);

    void deleteById(Long id);

    thanhvien checkLogin(Long id, String password);

    boolean isBanned(Long id);
}
