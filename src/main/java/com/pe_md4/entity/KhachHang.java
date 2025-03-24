package com.pe_md4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String tenKhachHang, String sdt, String email) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.email = email;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    public String getTenKhachHang() {
        return tenKhachHang;
    }
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
