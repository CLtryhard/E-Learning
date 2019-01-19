package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

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
        //设置条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //判断并设置站点id
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //判断是否有页面别名并设置
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

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
        Page<CmsPage> cmsPages = repository.findAll(example, pageable);
        QueryResult queryResult = new QueryResult();
        //数据列表
        queryResult.setList(cmsPages.getContent());
        //数据总记录数
        queryResult.setTotal(cmsPages.getTotalElements());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    /**
     * 添加
     * @param cmsPage
     * @return
     */
    public CmsPageResult add (CmsPage cmsPage){
        if (cmsPage==null){
            //非法参数
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //校验索引是否重复
        CmsPage cms = repository.findByPageNameAndPageWebPathAndSiteId(cmsPage.getPageName(), cmsPage.getPageWebPath(), cmsPage.getSiteId());
        if (cms!=null){
            //页面已存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }else {
            //首先要设置id为null,让MongoDB自增主键
            cmsPage.setPageId(null);
            repository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        //添加失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public CmsPage getById(String id){
        Optional<CmsPage> optional = repository.findById(id);
        if (optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    /**
     * 更新页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage){
        //先根据页面id从数据库查询页面信息
        CmsPage findPage = this.getById(id);
        //判断是否存在页面
        if (findPage!=null){
            //如果不存在就可以开始修改数据了
            //更新模板id
            findPage.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            findPage.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            findPage.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            findPage.setPageName(cmsPage.getPageName());
            //更新访问路径
            findPage.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            findPage.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            findPage.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage savePage = repository.save(cmsPage);
            if (savePage != null){
                //保存成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, savePage);
                return  cmsPageResult;
            }
        }
        //修改失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id){
        CmsPage one = this.getById(id);
        if(one!=null){
            //删除页面
            repository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

}
