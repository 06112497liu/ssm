package com.bbd.service;

import com.bbd.bean.AccountEsVo;
import com.bbd.bean.EsMetadata;
import com.google.common.collect.Maps;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class EsServiceTest extends BaseServiceTest {

    @Autowired
    private EsService esService;

    @Test
    public void testQueryAccount() {
        GetResponse resp = esService.queryGetResp(EsMetadata.BANK_INDEX, "4");
        long version = resp.getVersion();
        Map<String, Object> doc = Maps.newHashMap();
        doc.put("age", 999);
        doc.put("birthday", DateTime.now());
        doc.put("address", "江油");
        UpdateResponse rs = esService.updateDoc(EsMetadata.BANK_INDEX, EsMetadata.Bank_type, "4", version, doc);
        System.out.println(rs.toString());
    }

}
