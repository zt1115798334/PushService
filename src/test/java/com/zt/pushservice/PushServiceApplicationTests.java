package com.zt.pushservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zkdj.search.fielditem.QueryString;
import com.zt.base.BaseTest;
import com.zt.pushservice.mysql.entity.DicAbout;
import com.zt.pushservice.mysql.service.DicAboutService;
import com.zt.pushservice.solr.IndexQuery;
import com.zt.pushservice.utils.GETuuid;
import com.zt.pushservice.utils.HttpClientUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

public class PushServiceApplicationTests extends BaseTest {

    @Autowired
    private DicAboutService dicAboutService;

    @Test
    public void contextLoads() {

        final String postUrl = "http://60.208.86.203:28051";

        //实例化索引服务
        IndexQuery indexQuery = new IndexQuery();
        /**
         * rule自定义规则
         * 此规则意思2015-11-01一天数据的规则
         */
        String rule = "(*:* AND postdate:[2015-11-01T00:00:00.000Z TO 2015-11-01T23:59:59.999Z])";
        QueryString queryString = new QueryString();
        queryString.setQueryStr(rule);
        /**
         * order是排序方式
         * page是第几页
         * pageSize是一次取10条数据（如果想要这天全部的数据，有两种方法：
         * 1、比如5000条，就把10改成10000，只要大于当天数据量即可，如果一次性取出的数据太大，这个方式不合适容易报错；
         * 2、模拟翻页，把page和pageSize设置成变量，判断下面的list是否为空，如为空则数据取完）
         *
         */
        String page = "1";
        int pageSize = 10;
        String order = "date asc";
        HashMap<String, Serializable> hashMap = indexQuery.QueryPageByString(queryString, new String[]{}, order, page, pageSize);
        /**
         * list是2015-11-01全天数据的前十条
         */
        ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) hashMap.get("list");
        HashMap<String, String> data = null;
        for (int i = 0; i < list.size(); i++) {
            data = list.get(i);
            String author = data.get("author");
            String body = data.get("body");
            String carrier = data.get("carrier");
            String catwords = data.get("catwords");
            String channel = data.get("channel");
            String clink = data.get("clink");
            String commenturl = data.get("commenturl");
            String country = data.get("country");
            String hot = data.get("hot");
            String id = data.get("id");
            String negative = data.get("negative");
            String ngfunnel = data.get("ngfunnel");
            String ngvalue = data.get("ngvalue");
            String positive = data.get("positive");
            String postdate = data.get("postdate");
            String property = data.get("property");
            String psfunnel = data.get("psfunnel");
            String psvalue = data.get("psvalue");
            String read = data.get("read");
            String refunnel = data.get("refunnel");
            String related = data.get("related");
            String reply = data.get("reply");
            String revalue = data.get("revalue");
            String score = data.get("score");
            String site = data.get("site");
            String source = data.get("source");
            String stopwords = data.get("stopwords");
            String summary = data.get("summary");
            String timestamp = data.get("timestamp");
            String title = data.get("title");
            String url = data.get("url");
            String yqfunnel = data.get("yqfunnel");
            String yqvalue = data.get("yqvalue");
            String yuqing = data.get("yuqing");
            List<String> relatedList = Arrays.asList(related.split(","));
            List<Long> relateds = Lists.newArrayList();
            for (String reId : relatedList) {
                relateds.add(Long.valueOf(reId));
            }
            List<DicAbout> dicAbouts = dicAboutService.findByIdIn(relateds);
            List<String> aboutKeywords = Lists.newArrayList();
            for (DicAbout dic : dicAbouts) {
                aboutKeywords.add(dic.getAboutKeyword());
            }

            Map<String, String> params = Maps.newHashMap();

            JSONObject headersInfo = new JSONObject();
            headersInfo.put("key", GETuuid.getId());
            headersInfo.put("topic", "article");
            params.put("headers", headersInfo.toJSONString());

            JSONObject bodyInfo = new JSONObject();
            bodyInfo.put("ID", id);
            bodyInfo.put("Url", url);
            bodyInfo.put("Title", title);
            bodyInfo.put("Content", body);
            bodyInfo.put("Author", author);
            bodyInfo.put("From", null);
            bodyInfo.put("Time", postdate);
            bodyInfo.put("Tag", null);
            bodyInfo.put("Images", null);
            bodyInfo.put("ImageUrl", null);
            bodyInfo.put("Comments", reply);
            bodyInfo.put("Views", clink);
            bodyInfo.put("Praises", null);
            bodyInfo.put("Place", null);
            bodyInfo.put("Person", null);
            bodyInfo.put("Keyword", aboutKeywords);
            bodyInfo.put("Hash", null);
            bodyInfo.put("ParagraphHash", null);
            bodyInfo.put("Segment", null);
            bodyInfo.put("TitleSegment", null);
            bodyInfo.put("TaskID", null);
            bodyInfo.put("TaskName", null);
            bodyInfo.put("GroupID", null);
            bodyInfo.put("GroupName", null);
            bodyInfo.put("SiteID", null);
            bodyInfo.put("Country", null);
            bodyInfo.put("Language", null);
            bodyInfo.put("SiteType", null);
            bodyInfo.put("Channel", null);
            bodyInfo.put("DefinedType", null);
            bodyInfo.put("DefinedSite", null);
            bodyInfo.put("PR", null);
            bodyInfo.put("Headline", null);
            bodyInfo.put("Overseas", null);
            bodyInfo.put("TopicID", null);
            bodyInfo.put("HotChannel", null);
            bodyInfo.put("AddOn", null);
            bodyInfo.put("CustomerID", null);
            bodyInfo.put("SChannel", null);
            bodyInfo.put("SCatagory", null);
            bodyInfo.put("Continent", null);
            bodyInfo.put("Tissue", null);
            bodyInfo.put("IsTopic", null);
            bodyInfo.put("Pure", null);
            bodyInfo.put("Pureadj", null);
            bodyInfo.put("Pun", null);
            bodyInfo.put("Nounnum", null);
            bodyInfo.put("KeywordPOS", null);
            params.put("body", bodyInfo.toJSONString());
            System.out.println(JSONObject.toJSON(params));
            String msg = HttpClientUtil.getInstance().httpPost(postUrl, params);
            System.out.println("msg = " + msg);
        }
    }

}
