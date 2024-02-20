package com.yupi.springbootinit.job.once;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取初始帖子列表
 */
// todo 取消注释开启任务
    //取消Component，每次项目启动都将执行一遍
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        //1.获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
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
        //System.out.println(records);
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

        //3.写入数据库
        boolean b = postService.saveBatch(postList);
        if(b)
            log.info("成功获取帖子,条数:{}",postList.size());
        else log.error("获取帖子失败!");
    }
}
