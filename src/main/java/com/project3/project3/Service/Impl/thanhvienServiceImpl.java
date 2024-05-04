package com.project3.project3.Service.Impl;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.*;
import com.project3.project3.Repository.*;
import com.project3.project3.Service.thanhvienService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class thanhvienServiceImpl implements thanhvienService {

    thanhvienRepository thanhvienRepository;
    thanhvienSequenceRepository thanhvienSequenceRepository;
    nganhRepository nganhRepository;
    khoaRepository khoaRepository;
    ModelMapper modelMapper;
    xulyRepository xulyRepository;


    public thanhvienServiceImpl(thanhvienRepository thanhvienRepository,
                                thanhvienSequenceRepository thanhvienSequenceRepository,
                                nganhRepository nganhRepository,
                                khoaRepository khoaRepository,
                                ModelMapper modelMapper,
                                xulyRepository xulyRepository) {
        this.thanhvienRepository = thanhvienRepository;
        this.thanhvienSequenceRepository = thanhvienSequenceRepository;
        this.nganhRepository = nganhRepository;
        this.khoaRepository = khoaRepository;
        this.modelMapper = modelMapper;
        this.xulyRepository = xulyRepository;
    }

    @PostConstruct
    public void init() {
        checkAndCreateIndex();
    }

    @Override
    @Transactional
    public void checkAndCreateIndex() {
        thanhvien_sequence sequence = thanhvienSequenceRepository.findByName("index");
        if (sequence == null) {
            sequence = new thanhvien_sequence();
            sequence.setName("index");
            sequence.setNum(1L);
            thanhvienSequenceRepository.save(sequence);
        }
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
        tv.setCreated_date(LocalDateTime.now());

        thanhvienRepository.save(tv);
        thanhvienSequenceRepository.updateIndex();

        return tv;
    }

    @Override
    @Transactional
    public String saveList(List<ThanhVienDTO> tvDTOs) {

        for (ThanhVienDTO tvDTO : tvDTOs) {
            thanhvien tv = new thanhvien();
            tv.setTen(tvDTO.getTen());
            tv.setSodienthoai(tvDTO.getSodienthoai());
            tv.setCreated_date(LocalDateTime.now());
            khoa khoa = khoaRepository.findByName(tvDTO.getTenKhoa());
            nganh nganh = nganhRepository.getByTen(tvDTO.getTenNganh());

            System.out.println(khoa.getTen() + " " + nganh.getTen());

            tv.setKhoa(khoa);
            tv.setNganh(nganh);

            tv.setPassword(tvDTO.getPassword());
            tv.setEmail(tvDTO.getEmail());
            Long tvId = generateTvId(tv.getKhoa().getId(), tv.getNganh().getId());
            tv.setId(tvId);

            thanhvienRepository.save(tv);
            thanhvienSequenceRepository.updateIndex();
        }
        return "success";
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        thanhvienRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByYear(int year) {

        thanhvienRepository.deleteByYear(year);
    }

    @Override
    public List<thanhvien> findByYear(int year) {
        return thanhvienRepository.findByYear(year);
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

    //kiểm tra xem thành viên có bị cấm mượn thiết bị không, nếu tìm thấy xử lý nào có trạng thái false thì trả về true
    @Override
    public boolean isBanned(Long id) {

        List<xuly> dsXL = xulyRepository.findByThanhvienIdAndTrangthaiFalse(id);

        return !dsXL.isEmpty();
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
