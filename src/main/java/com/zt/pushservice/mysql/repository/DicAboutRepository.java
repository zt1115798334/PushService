package com.zt.pushservice.mysql.repository;

import com.zt.pushservice.mysql.entity.DicAbout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
public interface DicAboutRepository extends CrudRepository<DicAbout, Long> {

    List<DicAbout> findByIdIn(List<Long> ids);
}
