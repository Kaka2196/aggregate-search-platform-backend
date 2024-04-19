package com.yl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yl.model.entity.Picture;
import com.yl.model.entity.Post;
import com.yl.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.lang.Console.log;

@SpringBootTest
public class CrawlerTest {

    @Autowired
    private PostService postService;


    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://www.bing.com/images/search?q=%E4%B8%81%E7%9C%9F&form=HDRSC3&first=1"+current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.varh.isv");
        List<Picture> list = new ArrayList<>();
        for (Element element : elements) {
            //取图片url
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            //取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            list.add(picture);
        }
        System.out.println(list);
    }
    @Test
    void testFetchPassage() {
        //1.获取数据
//        String json = "{\n" +
//                "  \"current\": 1,\n" +
//                "  \"pageSize\": 8,\n" +
//                "  \"sortField\": \"createTime\",\n" +
//                "  \"sortOrder\": \"descend\",\n" +
//                "  \"category\": \"文章\",\n" +
//                "  \"reviewStatus\": 1\n" +
//                "}";
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"thumbNum\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();
        System.out.println(result);
        //2.json转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);

        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        System.out.println(records);
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();

            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));

            post.setThumbNum(tempRecord.getInt("thumbNum"));
            post.setUserId(1759251571326459906L);
            post.setFavourNum(tempRecord.getInt("favourNum"));
            post.setContent(tempRecord.getStr("content"));
            post.setTitle(tempRecord.getStr("title"));
            post.setCreateTime(tempRecord.getDate("createTime"));
            post.setUpdateTime(tempRecord.getDate("updateTime"));
            postList.add(post);
        }
        System.out.println(postList);
        //3.写入数据库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }

}
