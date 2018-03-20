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

    @Autowired
    private DicAboutService dicAboutService;

    public ArrayList<HashMap<String, String>> getList(String postUrl, String page, int pageSize, IndexQuery indexQuery, QueryString queryString) throws IOException {
        String order = "date asc";
        HashMap<String, Serializable> hashMap = indexQuery.QueryPageByString(queryString, new String[]{}, order, page, pageSize);
        /**
         * list是2015-11-01全天数据的前十条
         */
        ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) hashMap.get("list");
        HashMap<String, String> data = null;
        long timestampL = System.currentTimeMillis();

        Map<String, Object> headers = new HashMap<>();
        headers.put("topic", "test");
        headers.put("timestamp", timestampL);


        List<Map<String, Object>> datas = new ArrayList<>();
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
            bodyInfo.put("From", null);
            bodyInfo.put("Time", date.getTime());
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
            datas.add(bodyInfo);
        }
        if (datas!=null&&datas.size()>0){
            byte[] jdata = HttpClientUtil.buildFlumeData(headers, datas, true);
            HttpPost httpPost = new HttpPost("http://60.208.86.203:28051");

            httpPost.setEntity(new ByteArrayEntity(jdata, ContentType.APPLICATION_JSON));
            System.out.println(HttpClientUtil.getResult(httpPost));
        }
        return list;
    }

}
