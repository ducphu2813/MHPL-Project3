package com.project3.project3.Service.Impl;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.thanhvien;
import com.project3.project3.Repository.khoaRepository;
import com.project3.project3.Repository.nganhRepository;
import com.project3.project3.Repository.thanhvienRepository;
import com.project3.project3.Repository.thanhvienSequenceRepository;
import com.project3.project3.Service.thanhvienService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class thanhvienServiceImpl implements thanhvienService {

    thanhvienRepository thanhvienRepository;
    thanhvienSequenceRepository thanhvienSequenceRepository;
    nganhRepository nganhRepository;
    khoaRepository khoaRepository;
    ModelMapper modelMapper;


    public thanhvienServiceImpl(thanhvienRepository thanhvienRepository,
                                thanhvienSequenceRepository thanhvienSequenceRepository,
                                nganhRepository nganhRepository,
                                khoaRepository khoaRepository,
                                ModelMapper modelMapper) {
        this.thanhvienRepository = thanhvienRepository;
        this.thanhvienSequenceRepository = thanhvienSequenceRepository;
        this.nganhRepository = nganhRepository;
        this.khoaRepository = khoaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<thanhvien> getAll() {
        return thanhvienRepository.findAll();
    }

    @Override
    public thanhvien findById(Long id) {
        return thanhvienRepository.findById(id).get();
    }

    @Override
    public thanhvien findByEmail(String email) {
        return thanhvienRepository.getByEmail(email);
    }

    @Override
    public ThanhVienDTO modelToDTO(thanhvien tv){
        ThanhVienDTO tvDTO = modelMapper.map(tv, ThanhVienDTO.class);
        tvDTO.setRepassword(tv.getPassword()); // set repassword bằng password
        return tvDTO;

    }

    @Override
    public thanhvien dtoToModel(ThanhVienDTO tvDTO) {
        return modelMapper.map(tvDTO, thanhvien.class);
    }


    @Override
    public thanhvien findByTen(String ten) {
        return thanhvienRepository.getByTen(ten);
    }

    @Override
    @Transactional
    public thanhvien save(ThanhVienDTO tvDTO) {

        thanhvien tv = new thanhvien();

        if(tvDTO.getId() == null){
            Long tvId = generateTvId(tvDTO.getKhoaId(), tvDTO.getNganhId());
            tv.setId(tvId);
        }
        else{
            tv.setId(tvDTO.getId());
        }

        tv.setTen(tvDTO.getTen());
        tv.setSodienthoai(tvDTO.getSodienthoai());
        tv.setKhoa(khoaRepository.findById(tvDTO.getKhoaId()).get());
        tv.setNganh(nganhRepository.findById(tvDTO.getNganhId()).get());

        tv.setEmail(tvDTO.getEmail());
        tv.setPassword(tvDTO.getPassword());

        thanhvienRepository.save(tv);
        thanhvienSequenceRepository.updateIndex();

        return tv;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        thanhvienRepository.deleteById(id);
    }


//    @Transactional
//    public void deleteById(Long id){
//        thanhvienRepository.deleteById(id);
//    }


    @Override
    public thanhvien checkLogin(Long id, String password) {

        try{
            return thanhvienRepository.checkLogin(id, password);
        }
        catch (Exception e) {
            return null;
        }

    }

    private String parseDozen(int num) {
        if(num < 10){
            return "0" + num;
        }
        else{
            return String.valueOf(num);
        }
    }

    private Long generateTvId(Integer khoaId, Integer nganhId){
        String id = "31" + parseDozen(khoaId) + parseDozen(nganhId) + parseDozen(thanhvienSequenceRepository.getIndex().intValue());
        return Long.parseLong(id);
    }
}
