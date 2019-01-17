package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.config.com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lacne
 * @Date: 2019/1/13 23:24
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService service;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return service.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return service.add(cmsPage);
    }

    @Override
    @GetMapping("/query/{id}")
    public CmsPage findById(String id) {
        return service.getById(id);
    }

    /**
     * 更新使用put方法,http中put表示更新
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    @PutMapping("/update/{id}")
    public CmsPageResult updatePage(@PathVariable String id,@RequestBody CmsPage cmsPage) {
        return service.update(id, cmsPage);
    }
}
