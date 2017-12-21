package com.bbd.service;

import com.bbd.bean.AccountEsVo;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface EsService {

    /**
     * 根据id查询account信息
     * @param index
     * @param id
     * @return
     */
    AccountEsVo queryAccount(String index, String id);

    /**
     * 根据id查询某个文档的所有信息
     * @param index
     * @param id
     * @return
     */
    GetResponse queryGetResp(String index, String id);

    /**
     * 根据id查询某个文档的版本号
     * @param index
     * @param id
     * @return
     */
    Long queryVersionById(String index, String id);

    /**
     * 更新文档信息
     * @param index
     * @param id
     * @param version
     * @param doc
     */
    UpdateResponse updateDoc(String index, String type, String id, Long version, Map<String, Object> doc);
}
