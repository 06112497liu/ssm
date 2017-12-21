package com.bbd.controller;

import com.bbd.RestResult;
import com.bbd.bean.EsMetadata;
import com.bbd.exception.ApplicationException;
import com.bbd.exception.CommonErrorCode;
import com.bbd.service.EsService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/es/")
@Api(description = "es控制器")
public class EsController {


    @Autowired
    private EsService esService;


    @ApiOperation(value = "es修改测试", httpMethod = "GET")
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public RestResult update() throws InterruptedException {
        for (int i=0; i<100; i++) {
            new Thread(() -> {
                try {
                    GetResponse resp = esService.queryGetResp(EsMetadata.BANK_INDEX, "4");
                    long version = resp.getVersion();
                    Map<String, Object> doc = Maps.newHashMap();
                    doc.put("age", Thread.currentThread().getId());
                    doc.put("birthday", DateTime.now());
                    doc.put("address", "江油");
                    UpdateResponse rs = esService.updateDoc(EsMetadata.BANK_INDEX, EsMetadata.Bank_type, "4", version, doc);
                } catch (RuntimeException e) {
                    throw new ApplicationException(CommonErrorCode.BIZ_ERROR, "修改冲突");
                }
            }).start();
        }
        Thread.currentThread().join();
        return RestResult.ok();
    }
}
