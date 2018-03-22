package com.zt.pushservice.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zkdj.search.fielditem.QueryString;
import com.zt.pushservice.mysql.entity.DicAbout;
import com.zt.pushservice.mysql.service.DicAboutService;
import com.zt.pushservice.solr.IndexQuery;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author zhangtong
 * Created by on 2018/3/20
 */
@Component
public class SendData {
    private static final Logger logger = Logger.getLogger(SendData.class);

    @Autowired
    private DicAboutService dicAboutService;

    public ArrayList<HashMap<String, String>> getList(String postUrl, String page, int pageSize, IndexQuery indexQuery, QueryString queryString) throws IOException {

        final String definedSite = "002";

        String order = "date asc";
        HashMap<String, Serializable> hashMap = indexQuery.QueryPageByString(queryString, new String[]{}, order, page, pageSize);
        /**
         * list是2015-11-01全天数据的前十条
         */
        ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) hashMap.get("list");
        HashMap<String, String> data = null;
        long timestampL = System.currentTimeMillis();

        Map<String, Object> headers = new HashMap<>();
        headers.put("topic", "article");
        headers.put("timestamp", timestampL);


        List<Map<String, Object>> datas = Lists.newArrayList();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String id = GETuuid.getId();
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
                String negative = data.get("negative");
                String ngfunnel = data.get("ngfunnel");
                String ngvalue = data.get("ngvalue");
                String positive = data.get("positive");
                String postdate = data.get("postdate");
                Date date = DateUtils.parseDate(postdate);
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
                Map<String, Object> bodyInfo = Maps.newHashMap();
                bodyInfo.put("ID", id);
//            bodyInfo.put("Url", url);
                bodyInfo.put("Title", title);
                bodyInfo.put("Content", body);
                bodyInfo.put("Author", author);
                bodyInfo.put("From", "");
                bodyInfo.put("Time", date.getTime());
                bodyInfo.put("Tag", "");
                bodyInfo.put("Images", "");
                bodyInfo.put("ImageUrl", "");
                bodyInfo.put("Comments", reply);
                bodyInfo.put("Views", clink);
                bodyInfo.put("Praises", "");
                bodyInfo.put("Place", "");
                bodyInfo.put("Person", "");
                bodyInfo.put("Keyword", aboutKeywords);
                bodyInfo.put("Hash", "");
                bodyInfo.put("ParagraphHash", "");
                bodyInfo.put("Segment", "");
                bodyInfo.put("TitleSegment", "");
                bodyInfo.put("TaskID", "");
                bodyInfo.put("TaskName", "");
                bodyInfo.put("GroupID", "");
                bodyInfo.put("GroupName", "");
                bodyInfo.put("SiteID", "");
                bodyInfo.put("Country", "");
                bodyInfo.put("Language", "");
                bodyInfo.put("SiteType", "");
                bodyInfo.put("Channel", "");
                bodyInfo.put("DefinedType", "");
                bodyInfo.put("DefinedSite", definedSite);
                bodyInfo.put("PR", "");
                bodyInfo.put("Headline", "");
                bodyInfo.put("Overseas", "");
                bodyInfo.put("TopicID", "");
                bodyInfo.put("HotChannel", "");
                bodyInfo.put("AddOn", "");
                bodyInfo.put("CustomerID", "");
                bodyInfo.put("SChannel", "");
                bodyInfo.put("SCatagory", "");
                bodyInfo.put("Continent", "");
                bodyInfo.put("Tissue", "");
                bodyInfo.put("IsTopic", "");
                bodyInfo.put("Pure", "");
                bodyInfo.put("Pureadj", "");
                bodyInfo.put("Pun", "");
                bodyInfo.put("Nounnum", "");
                bodyInfo.put("KeywordPOS", "");
                bodyInfo.put("postdate", postdate);
                datas.add(bodyInfo);
            }
            if (datas != null && datas.size() > 0) {
                byte[] jdata = HttpClientUtil.buildFlumeData(headers, datas, true);
                HttpPost httpPost = new HttpPost("http://60.208.86.203:28051");

                httpPost.setEntity(new ByteArrayEntity(jdata, ContentType.APPLICATION_JSON));
                logger.info(HttpClientUtil.getResult(httpPost));
            }
        }else{
            logger.info("此时间段暂无数据");
        }
        return list;
    }

}
