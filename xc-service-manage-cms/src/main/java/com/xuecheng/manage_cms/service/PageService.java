package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author: Lacne
 * @Date: 2019/1/14 15:30
 */
@Service
public class PageService {

    @Autowired
    CmsPageRepository repository;

    /**
     * 页面查询方法
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        /*分页参数
        如果遇到小于的页码都要使其变回1*/
        if (page<=0){
            page = 1;
        }
        //为了便于用户使用,前端的页码要从1开始,所以从前端传回页码时要-1
        page-=1;
        //重置数据条数
        if (size<=0){
            size=10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> cmsPages = repository.findAll(pageable);
        QueryResult queryResult = new QueryResult();
        //数据列表
        queryResult.setList(cmsPages.getContent());
        //数据总记录数
        queryResult.setTotal(cmsPages.getTotalElements());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }
}
