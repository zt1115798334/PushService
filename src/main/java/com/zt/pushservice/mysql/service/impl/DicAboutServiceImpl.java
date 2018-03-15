package com.zt.pushservice.mysql.service.impl;

import com.zt.pushservice.mysql.entity.DicAbout;
import com.zt.pushservice.mysql.repository.DicAboutRepository;
import com.zt.pushservice.mysql.service.DicAboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
@Service
public class DicAboutServiceImpl implements DicAboutService {

    @Autowired
    private DicAboutRepository dicAboutRepository;

    @Override
    public List<DicAbout> findByIdIn(List<Long> ids) {
        return dicAboutRepository.findByIdIn(ids);
    }
}
