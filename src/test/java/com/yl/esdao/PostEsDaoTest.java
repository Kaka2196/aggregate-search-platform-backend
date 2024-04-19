package com.yl.esdao;

import com.yl.model.entity.Post;
import com.yl.model.dto.post.PostEsDTO;
import com.yl.model.dto.post.PostQueryRequest;
import com.yl.service.PostService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 帖子 ES 操作测试
 *
 */
@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;

    @Test
    void test() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> page =
                postService.searchFromEs(postQueryRequest);
        System.out.println(page);
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> PostPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = PostPage.getContent();
        System.out.println(postList);
    }

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1759536668751589385L);
        postEsDTO.setTitle("数学");
        postEsDTO.setContent("线性代数中的数学,你懂撒发涩发多少大师傅大师傅数学，的SAD啊士大夫但是在fear的数学疯狂减肥姐姐估计都会返回内地会引发很多数学大苏打实打实的撒大苏打阿三大苏打实打实大苏打萨达大苏打实打实大苏打实打实大苏打。");
        postEsDTO.setTags(Arrays.asList("文章","前端","教程"));
        postEsDTO.setUserId(1759251571326459906L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testCount() {
        //System.out.println(postEsDao.count());
        postEsDao.deleteById(9L);
    }

    @Test
    void testFindByCategory() {
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByUserId(1L);
        System.out.println(postEsDaoTestList);
    }

    @Test
    void testFindByTags(){
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByTags("java");
        System.out.println(postEsDaoTestList);
    }
}
