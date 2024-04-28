package com.project3.project3.DTO;

public class ThietBiDTO{

    private Integer id;
    private String ten;
    private String mota;

    private Integer loaiThietBiId;

    private String tenLoaiTb;



    public ThietBiDTO() {
    }

    public ThietBiDTO(Integer id, String ten, String mota, Integer loaiThietBiId, String tenLoaiTb) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.loaiThietBiId = loaiThietBiId;
        this.tenLoaiTb = tenLoaiTb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Integer getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Integer loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

    public String getTenLoaiTb() {
        return tenLoaiTb;
    }

    public void setTenLoaiTb(String tenLoaiTb) {
        this.tenLoaiTb = tenLoaiTb;
    }
}
