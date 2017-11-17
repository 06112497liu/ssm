/**
 * BBD Service Inc
 * All Rights Reserved @2016
 */
package com.bbd.controller;

import com.bbd.RestResult;
import com.bbd.controller.param.UserPermmisionVo;
import com.bbd.exception.CommonErrorCode;
import com.bbd.service.PermissionService;
import com.bbd.vo.PermissionView;
import com.bbd.util.ValidateUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限
 * @author tjwang
 * @version $Id: PermissionController.java, v 0.1 2017/9/27 0027 17:02 tjwang Exp $
 */
@RestController
@RequestMapping("/api/permission")
@Api(description = "权限模块")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "查询用户权限", httpMethod = "GET")
    @ApiImplicitParams({ @ApiImplicitParam(value = "用户ID", name = "userId", dataType = "Long", paramType = "query", required = false) })
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public RestResult queryUserPermission(Long userId) {
        ValidateUtil.checkNull(userId, CommonErrorCode.PARAM_ERROR, "用户不能为空");
        List<PermissionView> result = permissionService.queryUserPermissions(userId);
        return RestResult.ok(result);
    }

    @ApiOperation(value = "设置用户权限", httpMethod = "POST")
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public RestResult setUserPermissions(@RequestBody @Valid UserPermmisionVo vo) {
        Long userId = vo.getUserId();
        List<Long> pIds = vo.getPermissionIds();

        if (pIds == null) {
            pIds = Lists.newArrayList();
        }
        permissionService.setUserPermission(vo.isAdmin(), userId, pIds);
        return RestResult.ok();
    }

    @ApiOperation(value = "查询权限列表", httpMethod = "GET")
    @RequestMapping(value = "/search/list", method = RequestMethod.GET)
    public RestResult queryPermissionList() {
        List<PermissionView> result = permissionService.queryPermissionViewList();
        return RestResult.ok(result);
    }

}
