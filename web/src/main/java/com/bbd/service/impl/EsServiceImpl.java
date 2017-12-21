package com.bbd.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bbd.bean.AccountEsVo;
import com.bbd.exception.ApplicationException;
import com.bbd.exception.CommonErrorCode;
import com.bbd.service.EsService;
import com.bbd.util.EsUtil;
import org.apache.http.client.methods.RequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.engine.VersionConflictEngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private EsUtil esUtil;

    @Override
    public AccountEsVo queryAccount(String index, String id) {
        GetResponse resp = queryGetResp(index, id);
        String source = resp.getSourceAsString();
        AccountEsVo account = JSONObject.parseObject(source, AccountEsVo.class);
        return account;
    }

    @Override
    public GetResponse queryGetResp(String index, String id) {
        TransportClient client = esUtil.getClient();
        GetResponse resp = client.prepareGet().setIndex(index).setId(id).execute().actionGet();
        return resp;
    }

    @Override
    public Long queryVersionById(String index, String id) {
        GetResponse resp = queryGetResp(index, id);
        return resp.getVersion();
    }

    @Override
    public UpdateResponse updateDoc(String index, String type, String id, Long version, Map<String, Object> doc) {
        TransportClient client = esUtil.getClient();
        UpdateRequest request = new UpdateRequest();
        request.index(index);
        request.type(type);
        request.id(id);
        request.doc(doc);
        request.version(version);
        UpdateResponse resp = null;
        try {
            resp = client.update(request).actionGet();
        } catch (VersionConflictEngineException e) {
            throw new ApplicationException(CommonErrorCode.BIZ_ERROR, "修改冲突");
        }
        return resp;
    }
}
