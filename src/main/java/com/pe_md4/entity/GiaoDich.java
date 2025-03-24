package com.pe_md4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "giao_dich")
public class GiaoDich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_giao_dich")
    private String maGiaoDich;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @Column(name = "loai_dich_vu")
    private String loaiDichVu;

    @Column(name = "ngay_giao_dich")
    private String ngayGiaoDich;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "dien_tich")
    private Double dienTich;

    // Constructors
    public GiaoDich() {
    }

    public GiaoDich(String maGiaoDich, KhachHang khachHang, String loaiDichVu, String ngayGiaoDich, Double donGia, Double dienTich) {
        this.maGiaoDich = maGiaoDich;
        this.khachHang = khachHang;
        this.loaiDichVu = loaiDichVu;
        this.ngayGiaoDich = ngayGiaoDich;
        this.donGia = donGia;
        this.dienTich = dienTich;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaGiaoDich() {
        return maGiaoDich;
    }
    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }
    public KhachHang getKhachHang() {
        return khachHang;
    }
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
    public String getLoaiDichVu() {
        return loaiDichVu;
    }
    public void setLoaiDichVu(String loaiDichVu) {
        this.loaiDichVu = loaiDichVu;
    }
    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }
    public void setNgayGiaoDich(String ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }
    public Double getDonGia() {
        return donGia;
    }
    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }
    public Double getDienTich() {
        return dienTich;
    }
    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }
}
