package com.xzixi.framework.content.controller;

import com.xzixi.framework.common.model.po.User;
import com.xzixi.framework.common.model.po.UserLink;
import com.xzixi.framework.content.util.SecurityUtils;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.content.service.IUserLinkService;
import com.xzixi.framework.content.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.xzixi.framework.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/user-link", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "关注")
@Validated
public class UserLinkController {

    @Autowired
    private IUserLinkService userLinkService;
    @Autowired
    private IUserService userService;

    @GetMapping("/idols")
    @ApiOperation(value = "查询当前用户的偶像")
    public Result<Collection<User>> idols() {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        List<UserLink> links = userLinkService.list(new QueryParams<>(new UserLink().setFollowerId(currentUserId)).orderBy("followTime asc"));
        if (CollectionUtils.isEmpty(links)) {
            return new Result<>(new ArrayList<>());
        }
        List<Integer> idolIds = links.stream().map(UserLink::getIdolId).collect(Collectors.toList());
        Collection<User> idols = userService.listByIds(idolIds);
        if (CollectionUtils.isNotEmpty(idols)) {
            idols.forEach(idol -> idol.setPassword(null));
        }
        return new Result<>(idols);
    }

    @GetMapping("/followers")
    @ApiOperation(value = "查询当前用户的粉丝")
    public Result<Collection<User>> followers() {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        List<UserLink> links = userLinkService.list(new QueryParams<>(new UserLink().setIdolId(currentUserId)).orderBy("followTime asc"));
        if (CollectionUtils.isEmpty(links)) {
            return new Result<>(new ArrayList<>());
        }
        List<Integer> followerIds = links.stream().map(UserLink::getFollowerId).collect(Collectors.toList());
        Collection<User> followers = userService.listByIds(followerIds);
        if (CollectionUtils.isNotEmpty(followers)) {
            followers.forEach(follow -> follow.setPassword(null));
        }
        return new Result<>(followers);
    }

    @PostMapping("/{idolId}")
    @ApiOperation(value = "添加关注")
    public Result<?> follow(
            @ApiParam(value = "偶像id", required = true) @NotNull(message = "偶像id不能为空！") @PathVariable Integer idolId) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        UserLink link = new UserLink(idolId, currentUserId);
        UserLink checkLink = userLinkService.getOne(new QueryParams<>(link), false);
        if (checkLink != null) {
            return new Result<>();
        }
        link.setFollowTime(System.currentTimeMillis());
        if (userLinkService.save(link)) {
            return new Result<>();
        }
        throw new ServerException(link, "添加关注失败！");
    }

    @DeleteMapping("/{idolId}")
    @ApiOperation(value = "取消关注")
    public Result<?> cancelFollow(
            @ApiParam(value = "偶像id", required = true) @NotNull(message = "偶像id不能为空！") @PathVariable Integer idolId) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        UserLink link = userLinkService.getOne(new QueryParams<>(new UserLink(idolId, currentUserId)), false);
        if (link == null) {
            return new Result<>();
        }
        if (userLinkService.removeById(link.getId())) {
            return new Result<>();
        }
        throw new ServerException(link, "取消关注失败！");
    }
}
