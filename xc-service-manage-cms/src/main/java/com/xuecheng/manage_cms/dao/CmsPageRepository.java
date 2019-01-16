package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Lacne
 * @Date: 2019/1/14 10:23
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    /**
     * @Description 根据页面名称查询
     * @Author Lance
     * @Date  17:25
     * @param pageName
     * @return com.xuecheng.framework.domain.cms.CmsPage
     * @throws
     **/
    CmsPage findByPageName(String pageName);

    /**
     * @Description 根据页面名称,页面webpath以及站点Id查询
     * @Author Lance 
     * @Date  17:26
     * @param pageName
     * @param pageWebPath
     * @param siteId
     * @return com.xuecheng.framework.domain.cms.CmsPage
     * @throws 
     **/
    CmsPage findByPageNameAndPageWebPathAndSiteId(String pageName, String pageWebPath, String siteId);
}
