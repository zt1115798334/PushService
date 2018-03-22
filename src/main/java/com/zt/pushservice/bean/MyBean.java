package com.zt.pushservice.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangtong
 * Created by on 2018/3/22
 */
@Component
public class MyBean {

    @Value("${solr.url}")
    private String solrUrl;

    @Value("${solr.initCount}")
    private String initCount;

    @Value("${push.url}")
    private String pushUrl;

    public String getSolrUrl() {
        return solrUrl;
    }

    public String getInitCount() {
        return initCount;
    }

    public String getPushUrl() {
        return pushUrl;
    }
}
