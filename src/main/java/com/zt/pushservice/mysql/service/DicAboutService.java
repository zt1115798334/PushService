package com.zt.pushservice.mysql.service;

import com.zt.pushservice.mysql.entity.DicAbout;

import java.util.List;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
public interface DicAboutService {

    List<DicAbout> findByIdIn(List<Long> id);
}
