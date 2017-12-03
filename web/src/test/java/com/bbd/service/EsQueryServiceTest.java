/**
 * BBD Service Inc
 * All Rights Reserved @2016
 */
package com.bbd.service;

import com.bbd.service.vo.KeyValueVO;
import com.bbd.service.vo.OpinionEsSearchVO;
import com.bbd.util.EsUtil;
import com.mybatis.domain.PageBounds;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author tjwang
 * @version $Id: EsQueryServiceTest.java, v 0.1 2017/10/31 0031 18:00 tjwang Exp $
 */
public class EsQueryServiceTest extends BaseServiceTest {

    @Resource
    private EsQueryService esQueryService;

    @Autowired
    private EsUtil esUtil;

    private List<String> list;

    @Test
    public void testGetOpinionCountStatistic() throws NoSuchFieldException, IllegalAccessException {
        DateTime now = new DateTime();
        now = now.plusMonths(-3);
        esQueryService.getOpinionCountStatistic(now, now);
    }

    @Test
    public void testGetKeywordsTopTen() {
        List<KeyValueVO> r = esQueryService.getKeywordsTopTen();
        assertTrue(r.size() >= 0);
    }

    @Test
    public void testGetEventSpreadChannelInfo() {
        List<KeyValueVO> r = esQueryService.getOpinionMediaSpread();
        assertTrue(r.size() >= 0);
    }

    @Test
    public void testQueryWarningOpinion() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusMonths(-3);

        Integer emotion = null;
        PageBounds pb = new PageBounds(1, 10);
        OpinionEsSearchVO r = esQueryService.queryWarningOpinion(dateTime, emotion, 2, pb);
        assertNotNull(r);
    }

    @Test
    public void testQueryTop100HotOpinion() {
        String param = "会";
        DateTime startTime = new DateTime().plusMonths(-3);
        Integer emotion = null;
        OpinionEsSearchVO r = esQueryService.queryTop100HotOpinion(startTime, emotion);
        assertNotNull(r);
    }

    @Test
    public void testQueryEventOpinionCounts() {
        List<KeyValueVO> r = esQueryService.getEventOpinionCounts();
        assertNotNull(r);
    }

    @Test
    public void testGetEventOpinionMediaSpread() {
        long eventId = 6L;
        List<KeyValueVO> r = esQueryService.getEventOpinionMediaSpread(eventId, DateTime.now(), null);
        assertNotNull(r);
    }

    @Test
    public void testGetEventWebsiteSpread() {
        long eventId = 6L;
        List<KeyValueVO> r = esQueryService.getEventWebsiteSpread(eventId, DateTime.now(), null);
        assertNotNull(r);
    }

    @Test
    public void testGetEventEmotionSpread() {
        long eventId = 6L;
        List<KeyValueVO> r = esQueryService.getEventEmotionSpread(eventId, DateTime.now(), null);
        assertNotNull(r);
    }

    @Test
    public void testGetOpinionByUUID() {
        esQueryService.getOpinionByUUID("88");
    }

    @Test
    public void testGetOpinionCountStatisticGroupTime() {
        DateTime dateTime = new DateTime();
        DateTime startTime = dateTime.plusDays(-10);
        DateTime endTime = dateTime;
        DateHistogramInterval interval = DateHistogramInterval.HOUR;

        Map<String, List<KeyValueVO>> map = esQueryService.getOpinionCountStatisticGroupTime(startTime, endTime, interval);
        System.out.println("size: " + map.size());
    }

    @Test
    public void testMultGet() {
        TransportClient client = esUtil.getClient();
        MultiGetRequestBuilder request = client.prepareMultiGet();
        request.add("bank", "account", "1", "2");
        MultiGetResponse resp = request.get();
        for (MultiGetItemResponse r : resp) {
            GetResponse getResponse = r.getResponse();
            if(getResponse.isExists()) {
                String json = getResponse.getSourceAsString();
                System.out.println(json);
            }
        }
    }

    @Test
    public void testGet() {
        TransportClient client = esUtil.getClient();
        GetResponse resp = client.prepareGet().setIndex("bank").setId("1").get();
        if(resp.isExists()) {
            String json = resp.getSourceAsString();
            System.out.println(json);
        }
    }

    @Test
    public void testDelField() {
        TransportClient client = esUtil.getClient();
        UpdateRequestBuilder update = client.prepareUpdate();
        update.setIndex("bank").setType("account").setId("1").setScript(new Script("ctx._source.remove('email')"));
        UpdateResponse resp = update.get();
        ReplicationResponse.ShardInfo source = resp.getShardInfo();
        System.out.println(source);
    }

    @Test
    public void testDelFields() {
        TransportClient client = esUtil.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (String id : list) {
            UpdateRequest up = new UpdateRequest("bank", "account", id);
            up.script(new Script("ctx._source.remove('state')"));
            bulkRequest.add(up);
        }
        BulkResponse resp = bulkRequest.execute().actionGet();
    }

    @Before
    public void testGetId() {
        TransportClient client = esUtil.getClient();
        SearchResponse resp = client.prepareSearch("bank").setFetchSource("account_number", null).setSize(1000).execute().actionGet();
        SearchHit[] hit = resp.getHits().getHits();
        List<String> idList = Arrays.stream(hit).map(SearchHit::getId).collect(Collectors.toList());
        System.out.println(idList);
        list = idList;
    }
}
