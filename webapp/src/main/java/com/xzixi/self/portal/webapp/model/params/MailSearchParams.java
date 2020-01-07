package com.xzixi.self.portal.webapp.model.params;

import com.xzixi.self.portal.framework.model.search.BaseSearchParams;
import com.xzixi.self.portal.webapp.model.po.Mail;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "邮件查询参数")
public class MailSearchParams extends BaseSearchParams<Mail> {
}