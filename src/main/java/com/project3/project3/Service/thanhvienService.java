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

    String saveList(List<ThanhVienDTO> tvDTOs);

    void deleteById(Long id);

    void deleteByYear(int year);

    List<thanhvien> findByYear(int year);

    thanhvien checkLogin(Long id, String password);

    boolean isBanned(Long id);
}
