/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.system.controller;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.system.service.IUserLinkService;
import com.xzixi.framework.webapps.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

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
//        Integer currentUserId = SecurityUtils.getCurrentUserId();
//        List<UserLink> links = userLinkService.list(new QueryParams<>(new UserLink().setFollowerId(currentUserId)).orderBy("followTime asc"));
//        if (CollectionUtils.isEmpty(links)) {
//            return new Result<>(new ArrayList<>());
//        }
//        List<Integer> idolIds = links.stream().map(UserLink::getIdolId).collect(Collectors.toList());
//        Collection<User> idols = userService.listByIds(idolIds);
//        if (CollectionUtils.isNotEmpty(idols)) {
//            idols.forEach(idol -> idol.setPassword(null));
//        }
//        return new Result<>(idols);
        return null;
    }

    @GetMapping("/followers")
    @ApiOperation(value = "查询当前用户的粉丝")
    public Result<Collection<User>> followers() {
//        Integer currentUserId = SecurityUtils.getCurrentUserId();
//        List<UserLink> links = userLinkService.list(new QueryParams<>(new UserLink().setIdolId(currentUserId)).orderBy("followTime asc"));
//        if (CollectionUtils.isEmpty(links)) {
//            return new Result<>(new ArrayList<>());
//        }
//        List<Integer> followerIds = links.stream().map(UserLink::getFollowerId).collect(Collectors.toList());
//        Collection<User> followers = userService.listByIds(followerIds);
//        if (CollectionUtils.isNotEmpty(followers)) {
//            followers.forEach(follow -> follow.setPassword(null));
//        }
//        return new Result<>(followers);
        return null;
    }

    @PostMapping("/{idolId}")
    @ApiOperation(value = "添加关注")
    public Result<?> follow(
            @ApiParam(value = "偶像id", required = true) @NotNull(message = "偶像id不能为空！") @PathVariable Integer idolId) {
//        Integer currentUserId = SecurityUtils.getCurrentUserId();
//        UserLink link = new UserLink(idolId, currentUserId);
//        UserLink checkLink = userLinkService.getOne(new QueryParams<>(link), false);
//        if (checkLink != null) {
//            return new Result<>();
//        }
//        link.setFollowTime(System.currentTimeMillis());
//        if (userLinkService.save(link)) {
//            return new Result<>();
//        }
//        throw new ServerException(link, "添加关注失败！");
        return null;
    }

    @DeleteMapping("/{idolId}")
    @ApiOperation(value = "取消关注")
    public Result<?> cancelFollow(
            @ApiParam(value = "偶像id", required = true) @NotNull(message = "偶像id不能为空！") @PathVariable Integer idolId) {
//        Integer currentUserId = SecurityUtils.getCurrentUserId();
//        UserLink link = userLinkService.getOne(new QueryParams<>(new UserLink(idolId, currentUserId)), false);
//        if (link == null) {
//            return new Result<>();
//        }
//        if (userLinkService.removeById(link.getId())) {
//            return new Result<>();
//        }
//        throw new ServerException(link, "取消关注失败！");
        return null;
    }
}
