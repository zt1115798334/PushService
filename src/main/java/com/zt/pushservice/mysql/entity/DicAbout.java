package com.zt.pushservice.mysql.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
@Entity
@Table(name = "yq_dicabout")
public class DicAbout implements Serializable {
    private static final long serialVersionUID = 9201034849892179274L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ABOUT_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ABOUT_KEYWORD")
    private String aboutKeyword;

    @Column(name = "ABOUT_RULE")
    private String aboutRule;

    @Column(name = "ABOUT_COM_ID")
    private Integer aboutComId;

    @Column(name = "ABOUT_BEUSED")
    private Integer aboutBeused;

    @Column(name = "ABOUT_DELFLAG")
    private Integer aboutDelflag;

    @Column(name = "ABOUT_USER_ID")
    private Integer aboutUserId;

    @Column(name = "ABOUT_FUN_ID")
    private Integer aboutFunId;

    @Column(name = "ABOUT_ORDERID")
    private Integer aboutOrderId;

    @Column(name = "ABOUT_STATE")
    private Integer aboutState;

    @Column(name = "ABOUT_LEVEL")
    private Integer aboutLevel;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;

    @Column(name = "REMARK3")
    private String remark3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutKeyword() {
        return aboutKeyword;
    }

    public void setAboutKeyword(String aboutKeyword) {
        this.aboutKeyword = aboutKeyword;
    }

    public String getAboutRule() {
        return aboutRule;
    }

    public void setAboutRule(String aboutRule) {
        this.aboutRule = aboutRule;
    }

    public Integer getAboutComId() {
        return aboutComId;
    }

    public void setAboutComId(Integer aboutComId) {
        this.aboutComId = aboutComId;
    }

    public Integer getAboutBeused() {
        return aboutBeused;
    }

    public void setAboutBeused(Integer aboutBeused) {
        this.aboutBeused = aboutBeused;
    }

    public Integer getAboutDelflag() {
        return aboutDelflag;
    }

    public void setAboutDelflag(Integer aboutDelflag) {
        this.aboutDelflag = aboutDelflag;
    }

    public Integer getAboutUserId() {
        return aboutUserId;
    }

    public void setAboutUserId(Integer aboutUserId) {
        this.aboutUserId = aboutUserId;
    }

    public Integer getAboutFunId() {
        return aboutFunId;
    }

    public void setAboutFunId(Integer aboutFunId) {
        this.aboutFunId = aboutFunId;
    }

    public Integer getAboutOrderId() {
        return aboutOrderId;
    }

    public void setAboutOrderId(Integer aboutOrderId) {
        this.aboutOrderId = aboutOrderId;
    }

    public Integer getAboutState() {
        return aboutState;
    }

    public void setAboutState(Integer aboutState) {
        this.aboutState = aboutState;
    }

    public Integer getAboutLevel() {
        return aboutLevel;
    }

    public void setAboutLevel(Integer aboutLevel) {
        this.aboutLevel = aboutLevel;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}
