package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Lacne
 * @Date: 2019/1/19 15:56
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
