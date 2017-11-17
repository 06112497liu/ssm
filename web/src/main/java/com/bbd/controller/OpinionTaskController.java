package com.bbd.controller;

import com.bbd.RestResult;
import com.bbd.exception.CommonErrorCode;
import com.bbd.service.OpinionTaskService;
import com.bbd.service.param.TransferParam;
import com.bbd.service.vo.KeyValueVO;
import com.bbd.service.vo.OpinionTaskListVO;
import com.bbd.util.ValidateUtil;
import com.mybatis.domain.PageList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 舆情任务控制器
 * @author Liuweibo
 * @version Id: OpinionTaskController.java, v0.1 2017/11/7 Liuweibo Exp $$
 */
@RestController
@RequestMapping(value = "api/opinion/task/")
@Api(description = "舆情任务模块")
public class OpinionTaskController extends AbstractController{

    @Autowired
    private OpinionTaskService opinionTaskService;

    @ApiOperation(value = "当前用户待处理舆情列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "转发类型: 1/2/3：请示，4/5/6：回复", name = "transferType", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(value = "起始页号", name = "page", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(value = "每页大小", name = "limit", dataType = "Integer", paramType = "query", required = false)
    })
    @RequestMapping(value = "un/processed/list", method = RequestMethod.GET)
    public RestResult getUnProcessedList(Integer transferType) {
        PageList<OpinionTaskListVO> result = opinionTaskService.getUnProcessedList(transferType, getPageBounds());
        return RestResult.ok(result);
    }

    @ApiOperation(value = "当前用户转发、解除、监测列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "1.转发（介入）；2.已解除； 3.已监控", name = "opStatus", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(value = "起始页号", name = "page", dataType = "Integer", paramType = "query", required = false),
            @ApiImplicitParam(value = "每页大小", name = "limit", dataType = "Integer", paramType = "query", required = false)
    })
    @RequestMapping(value = "processed/list", method = RequestMethod.GET)
    public RestResult getProcessedList(Integer opStatus) {
        ValidateUtil.checkNull(opStatus, CommonErrorCode.PARAM_ERROR, "opStatus不能为null");
        PageList<OpinionTaskListVO> result = opinionTaskService.getProcessedList(opStatus, getPageBounds());
        return RestResult.ok(result);
    }

    @ApiOperation(value = "当前用户任务列表统计", httpMethod = "GET")

    @RequestMapping(value = "sta", method = RequestMethod.GET)
    public RestResult getTaskSta() {
        List<KeyValueVO> result = opinionTaskService.getTaskSta();
        return RestResult.ok(result);
    }

    @ApiOperation(value = "转发舆情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "舆情uuid", name = "uuid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "区域", name = "district", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "用户名", name = "username", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "转发类型: 1/2/3：请示，4/5/6：回复", name = "transferType", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(value = "转发备注", name = "transferNote", dataType = "String", paramType = "query", required = false)
    })
    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    public RestResult transferOpinion(TransferParam param) throws IOException, ExecutionException, InterruptedException {
        ValidateUtil.checkAllNull(CommonErrorCode.PARAM_ERROR, param.getDistrict(), param.getUuid(), param.getUsername(), param.getTransferType());
        opinionTaskService.transferOpinion(param);
        return RestResult.ok();
    }

    @ApiOperation(value = "查询处于任务舆情中的舆情详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "舆情uuid", name = "uuid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "详情类型(1-待处理详情、2-转发详情)", name = "type", dataType = "Integer", paramType = "query", required = true)
    })
    @RequestMapping(value = "transfer/detail", method = RequestMethod.GET)
    public RestResult getWarnOpinionDetail(String uuid, Integer type) {
        ValidateUtil.checkNull(uuid, CommonErrorCode.PARAM_ERROR, "uuid不能为空");
        ValidateUtil.checkNull(type, CommonErrorCode.PARAM_ERROR, "type不能为空");
        OpinionTaskListVO result = opinionTaskService.getTransferDetail(uuid, type);
        return RestResult.ok(result);
    }

    @ApiOperation(value = "解除舆情预警", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "舆情uuid", name = "uuid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(value = "解除理由（1. 非敏感舆情；2. 非消费舆情； 3. 非职能范围； 4. 已处理同类舆情）", name = "removeReason", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(value = "解除备注", name = "removeNote", dataType = "String", paramType = "query", required = false)
    })
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public RestResult removeWarn(String uuid, Integer removeReason, String removeNote) throws InterruptedException, ExecutionException, IOException {
        ValidateUtil.checkAllNull(CommonErrorCode.PARAM_ERROR, uuid, removeReason);
        opinionTaskService.removeWarn(uuid, removeReason, removeNote);
        return RestResult.ok();
    }
}
    
    