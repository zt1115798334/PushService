package com.zt;

import com.zt.pushservice.utils.DateUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;


/**
 * @author zhangtong
 * Created by on 2018/3/20
 */
public class MyTest {
    @Test
    public void test1() throws IOException {
//        long timestamp = System.currentTimeMillis();
//
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("topic", "test");
//        headers.put("timestamp", timestamp);
//
//        while (true) {
//
//            List<Map<String, Object>> datas = new ArrayList<>();
//            for (int i = 0; i < 2; i++) {
//                Map<String, Object> $data = new HashMap<>();
//                $data.put("ID", "1234567890" + i);
//                $data.put("Title", "Title_五一放假中信_" + i);
//                $data.put("Content", "Content_放假了五一_" + i);
//                $data.put("Timedatetime", "");
//                datas.add($data);
//            }
//
//
//            byte[] jdata = HttpClientUtil.buildFlumeData(headers, datas, true);
//            HttpPost httpPost = new HttpPost("http://60.208.86.203:28051");
////            HttpPost httpPost = new HttpPost("http://120.77.124.9:80");
////        HttpPost httpPost = new HttpPost("http://qdsw17:16999");
////            HttpPost httpPost = new HttpPost("http://110.249.152.73:7114");
//
////            byte[] jdata = buildFlumeData(headers, datas, true);
////            HttpPost httpPost = new HttpPost("http://123.232.102.241:28051");
//
////            System.out.println("压缩后：" + new String(jdata, "utf-8").length());
////            System.out.println("解压后：" + new String(uncompress(jdata), "utf-8").length());
//
//            httpPost.setEntity(new ByteArrayEntity(jdata, ContentType.APPLICATION_JSON));
//            System.out.println("返回结果为" + HttpClientUtil.getResult(httpPost));
////            TimeUnit.MINUTES.sleep(1);


        Date currentDate = DateUtils.currentDate();
        Date startDate = DateUtils.getStartDateTimeOfDay(currentDate);
        Date endDate = DateUtils.getEndDateTimeOfDay(currentDate);
        String startTime = DateUtils.formatDate(startDate,DateUtils.GREENWICH_DATE_FORMAT);
        String endTime = DateUtils.formatDate(endDate,DateUtils.GREENWICH_DATE_FORMAT);
        System.out.println("startTime = " + startTime);
        System.out.println("endDate = " + endTime);

    }

}
