package com.zt.pushservice;

import com.zkdj.search.fielditem.QueryString;
import com.zt.base.BaseTest;
import com.zt.pushservice.bean.MyBean;
import com.zt.pushservice.solr.IndexQuery;
import com.zt.pushservice.utils.DateUtils;
import com.zt.pushservice.utils.SendData;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PushServiceApplicationTests extends BaseTest {
    private static final Logger logger = Logger.getLogger(PushServiceApplicationTests.class);

    @Autowired
    private MyBean myBean;
    @Autowired
    private SendData sendData;

    //实例化索引服务
    @Autowired
    IndexQuery indexQuery;

    @Test
    public void contextLoads() throws IOException {

        final String postUrl = myBean.getPushUrl();

        System.out.println("postUrl = " + postUrl);
        String page = "1";
        int pageSize = 10;


        /**
         * rule自定义规则
         * 此规则意思2015-11-01一天数据的规则
         */
        Date currentDate = DateUtils.currentDate();
        Date startDate = DateUtils.currentDateAddMinute(-10, currentDate);
        String startTime = DateUtils.formatDate(startDate, DateUtils.GREENWICH_DATE_FORMAT);
        String endTime = DateUtils.formatDate(currentDate, DateUtils.GREENWICH_DATE_FORMAT);
        logger.info("查询开始时间： " + startTime + ",到结束时间：" + endTime);
//        String rule = "(*:* AND postdate:[" + startTime + " TO " + endTime + "])";
        String rule = "(*:* AND postdate:[2015-11-01T00:00:00.000Z TO 2015-11-01T23:59:59.999Z])";
        QueryString queryString = new QueryString();
        queryString.setQueryStr(rule);
        ArrayList<HashMap<String, String>> list = sendData.getList(postUrl, page, pageSize, indexQuery, queryString);
        Integer pageInt = 1;
        while (list.size() != 0) {
            pageInt++;
            list = sendData.getList(postUrl, String.valueOf(pageInt), pageSize, indexQuery, queryString);
            logger.info(" 查询第" + pageInt + "页");
        }

    }

}
