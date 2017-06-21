package com.t3h.whiyew.loophabit;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hoadu on 09/05/2017.
 */

public class NameHabit implements Serializable {
    private int id;
    private String tenThuocTinh;
    private String questision;
    private int color;
    private int repeat;
    private int gio;
    private int phut;
    private String ngay;

    public NameHabit( int id, String tenThuocTinh, String questision,String ngay ,int color, int repeat, int gio, int phut) {
        this.id=id;
        this.color = color;
        this.gio = gio;
        this.phut = phut;
        this.repeat = repeat;
        this.questision = questision;
        this.tenThuocTinh = tenThuocTinh;
        this.ngay=ngay;
    }

    public NameHabit(  String tenThuocTinh, String questision,String ngay ,int color, int repeat, int gio, int phut) {
        this.color = color;
        this.gio = gio;
        this.phut = phut;
        this.repeat = repeat;
        this.questision = questision;
        this.tenThuocTinh = tenThuocTinh;
        this.ngay=ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getGio() {
        return gio;
    }

    public void setGio(int gio) {
        this.gio = gio;
    }

    public int getPhut() {
        return phut;
    }

    public void setPhut(int phut) {
        this.phut = phut;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getQuestision() {
        return questision;
    }

    public void setQuestision(String questision) {
        this.questision = questision;
    }

    public String getTenThuocTinh() {
        return tenThuocTinh;
    }

    public void setTenThuocTinh(String tenThuocTinh) {
        this.tenThuocTinh = tenThuocTinh;
    }
}