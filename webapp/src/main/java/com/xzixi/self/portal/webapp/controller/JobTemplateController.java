package com.xzixi.self.portal.webapp.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/job-template", produces = "application/json; charset=UTF-8")
@Api(tags = "任务模板")
@Validated
public class JobTemplateController {
}
