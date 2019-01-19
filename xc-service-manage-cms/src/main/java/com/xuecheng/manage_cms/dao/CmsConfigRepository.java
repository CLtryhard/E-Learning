package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Lacne
 * @Date: 2019/1/18 18:53
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
