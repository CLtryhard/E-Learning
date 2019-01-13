package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Lacne
 * @Date: 2019/1/14 10:25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository repository;

    /**
     * 查询所有
     */
    @Test
    public void testFindAll(){
        List<CmsPage> cmsPages = repository.findAll();
        for (CmsPage page : cmsPages) {
            System.out.println(page);
        }
    }

    @Test
    public void testFindPage(){
        int page = 0;//页码从0开始
        int size = 10;
        Pageable pageable= PageRequest.of(page,size);
        Page<CmsPage> cmsPages = repository.findAll(pageable);
        for (CmsPage p : cmsPages) {
            System.out.println(p);
        }
    }

    //添加
    @Test
    public void testInsert(){
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        repository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete() {
        repository.deleteById("5b17a2c511fe5e0c409e5eb3");
    }

    @Test
    public void testUpdateByPageId(){
        Optional<CmsPage> optional = repository.findById("5abefd525b05aa293098fca8");
        if (optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("test01");
            CmsPage save = repository.save(cmsPage);
            System.out.println(save);
        }
    }

    @Test
    public void testFindByPageName(){
        CmsPage 测试页面 = repository.findByPageName("测试页面");
        System.out.println(测试页面);
    }

}
