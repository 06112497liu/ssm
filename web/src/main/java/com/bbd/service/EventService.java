package com.bbd.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.constant.EsConstant;
import com.bbd.dao.AccountDao;
import com.bbd.dao.OpinionDictionaryDao;
import com.bbd.dao.OpinionEventDao;
import com.bbd.dao.OpinionEventMediaStatisticDao;
import com.bbd.dao.OpinionEventSourceTrendDao;
import com.bbd.dao.OpinionEventStatisticDao;
import com.bbd.dao.OpinionEventTrendStatisticDao;
import com.bbd.dao.OpinionEventWholeTrendStatisticDao;
import com.bbd.dao.OpinionEventWordsDao;
import com.bbd.dao.WarnSettingDao;
import com.bbd.domain.Account;
import com.bbd.domain.AccountExample;
import com.bbd.domain.Graph;
import com.bbd.domain.OpinionDictionary;
import com.bbd.domain.OpinionDictionaryExample;
import com.bbd.domain.OpinionEvent;
import com.bbd.domain.OpinionEventExample;
import com.bbd.domain.OpinionEventExample.Criteria;
import com.bbd.domain.OpinionEventMediaStatistic;
import com.bbd.domain.OpinionEventMediaStatisticExample;
import com.bbd.domain.OpinionEventStatistic;
import com.bbd.domain.OpinionEventStatisticExample;
import com.bbd.domain.OpinionEventTrendStatisticExample;
import com.bbd.domain.OpinionEventWords;
import com.bbd.domain.OpinionEventWordsExample;
import com.bbd.domain.WarnSetting;
import com.bbd.service.vo.KeyValueVO;
import com.bbd.service.vo.OpinionEsSearchVO;
import com.bbd.service.vo.OpinionOpRecordVO;
import com.bbd.service.vo.OpinionVO;
import com.bbd.util.BeanMapperUtil;
import com.bbd.util.UserContext;
import com.mybatis.domain.PageBounds;


/** 
 * @author daijinlong 
 * @version $Id: EventService.java, v 0.1 2017年10月25日 下午2:06:26 daijinlong Exp $ 
 */
@Service
public class EventService{

    @Autowired
    OpinionEventDao opinionEventDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    OpinionDictionaryDao opinionDictionaryDao;
    @Autowired
    WarnSettingDao warnSettingDao;
    @Autowired
    OpinionEventSourceTrendDao opinionEventSourceTrendDao;
    @Autowired
    OpinionEventWordsDao opinionEventWordsDao;
    @Autowired
    OpinionEventTrendStatisticDao opinionEventTrendStatisticDao;
    @Autowired
    OpinionEventMediaStatisticDao opinionEventMediaStatisticDao;
    @Autowired
    OpinionEventStatisticDao opinionEventStatisticDao;
    @Autowired
    OpinionEventWholeTrendStatisticDao opinionEventWholeTrendStatisticDao;
    @Autowired
    private EsQueryService esQueryService;
    @Autowired
    private EsModifyService esModifyService;
    @Autowired
    private SystemSettingService systemSettingService;
    /**  
     * 创建事件
     * @param opinionEvent 
     * @throws InterruptedException 
     * @throws ExecutionException 
     * @throws IOException 
     */
    public void createEvent(OpinionEvent opinionEvent) throws IOException, ExecutionException, InterruptedException {
        opinionEvent.setIsDelete((byte)0);
        opinionEvent.setGmtCreate(new Date());
        opinionEventDao.insert(opinionEvent);
        //设置事件预警初始值
        WarnSetting recordNew = new WarnSetting();
        recordNew.setEventId(opinionEvent.getId());
        recordNew.setType(1);
        recordNew.setTargetType(2);
        recordNew.setPopup(1);
        recordNew.setLevel(1);
        recordNew.setMin(60);
        recordNew.setMax(100);
        recordNew.setName("事件新增观点预警");
        recordNew.setCreateBy(opinionEvent.getCreateBy());
        warnSettingDao.insert(recordNew);
        
        WarnSetting recordWhole1 = new WarnSetting();
        recordWhole1.setEventId(opinionEvent.getId());
        recordWhole1.setType(2);
        recordWhole1.setTargetType(2);
        recordWhole1.setPopup(1);
        recordWhole1.setLevel(1);
        recordWhole1.setMin(80);
        recordWhole1.setMax(100);
        recordWhole1.setName("事件总体热度预警");
        recordWhole1.setCreateBy(opinionEvent.getCreateBy());
        warnSettingDao.insert(recordWhole1);
        
        WarnSetting recordWhole2 = new WarnSetting();
        recordWhole2.setEventId(opinionEvent.getId());
        recordWhole2.setType(2);
        recordWhole2.setTargetType(2);
        recordWhole2.setPopup(1);
        recordWhole2.setLevel(2);
        recordWhole2.setMin(60);
        recordWhole2.setMax(79);
        recordWhole2.setName("事件总体热度预警");
        recordWhole2.setCreateBy(opinionEvent.getCreateBy());
        warnSettingDao.insert(recordWhole2);
        
        WarnSetting recordWhole3 = new WarnSetting();
        recordWhole3.setEventId(opinionEvent.getId());
        recordWhole3.setType(2);
        recordWhole3.setTargetType(2);
        recordWhole3.setPopup(1);
        recordWhole3.setLevel(3);
        recordWhole3.setMin(40);
        recordWhole3.setMax(59);
        recordWhole3.setName("事件总体热度预警");
        recordWhole3.setCreateBy(opinionEvent.getCreateBy());
        warnSettingDao.insert(recordWhole3);
        
        //更新事件关联舆情
        OpinionOpRecordVO recordVO = new OpinionOpRecordVO();
        recordVO.setOperator(UserContext.getUser().getUsername());
        recordVO.setOpTime(new Date());
        recordVO.setOpType(3);
        recordVO.setUuid(opinionEvent.getUuid());
        esModifyService.recordOpinionOp(recordVO);
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put(EsConstant.opStatusField, 3);
        esModifyService.updateOpinion(UserContext.getUser(), opinionEvent.getUuid(), fieldMap);
    }
    
    
    /**  
     * 获取事件
     * @param id
     * @return 
     */
    public OpinionEvent getEvent(long id) {
        return opinionEventDao.selectByPrimaryKey(id);
    }
    
    /**  
     * 获取事件
     * @param id
     * @return 
     */
    public OpinionEvent getEventChinese(long id) {
        OpinionEvent evt = opinionEventDao.selectByPrimaryKey(id);
        transToChinese(evt);
        return evt;
    }

    
    /**  
     * 修改事件
     * @param opinionEvent 
     */
    public void modifyEvent(OpinionEvent opinionEvent) {
        opinionEvent.setGmtModified(new Date());
        opinionEventDao.updateByPrimaryKeySelective(opinionEvent);
    }
    
    public Map getEventUser(Long id) {
        HashMap map = new HashMap();
        OpinionEvent evt = opinionEventDao.selectByPrimaryKey(id);
        AccountExample example = new AccountExample();
        example.createCriteria().andUserIdEqualTo(evt.getCreateBy());
        Account createUser = accountDao.selectByExample(example).get(0);
        example.createCriteria().andUserIdEqualTo(evt.getFileBy());
        Account fileUser = accountDao.selectByExample(example).get(0);
        map.put("createUser", createUser);
        map.put("fileUser", fileUser);
        return map;
    }
    
    /**  
     * 归档事件
     * @param opinionEvent 
     * @throws ParseException 
     */
    public void fileEvent(OpinionEvent opinionEvent) throws ParseException {
        opinionEvent.setGmtFile(new Date());
        opinionEventDao.updateByPrimaryKeySelective(opinionEvent);
        OpinionEvent tmp =  opinionEventDao.selectByPrimaryKey(opinionEvent.getId());
        DateTime startTime = new DateTime(tmp.getGmtCreate());
        DateTime endTime = DateTime.now();
        DateTime oneYearBefore = endTime.plusYears(-1);
        if (oneYearBefore.getMillis() > startTime.getMillis()) {
            startTime = oneYearBefore;
        }
        
        long infototal = esQueryService.queryEventInfoTotal(opinionEvent.getId(), startTime, endTime);
        
        //归档事件媒体分布、媒体活跃度、媒体来源、数据情感信息
        List<KeyValueVO> mediaSpreadList = esQueryService.getEventOpinionMediaSpread(opinionEvent.getId(), startTime, endTime);
        StringBuilder mediaSpread = new StringBuilder();
        for (KeyValueVO e : mediaSpreadList) {
            mediaSpread.append(e.getKey()).append(",").append(e.getValue()).append("#");
        }
        List<KeyValueVO> websiteSpreadList = esQueryService.getEventWebsiteSpread(opinionEvent.getId(), startTime, endTime);
        StringBuilder websiteSpread = new StringBuilder();
        for (KeyValueVO e : websiteSpreadList) {
            websiteSpread.append(e.getKey()).append(",").append(e.getValue()).append("#");
        }
        List<KeyValueVO> emotionSpreadList = esQueryService.getEventEmotionSpread(opinionEvent.getId(), startTime, endTime);
        StringBuilder emotionSpread = new StringBuilder();
        for (KeyValueVO e : emotionSpreadList) {
            emotionSpread.append(e.getKey()).append(",").append(e.getValue()).append("#");
        }
        OpinionEventStatistic opinionEventStatistic = new OpinionEventStatistic();
        opinionEventStatistic.setEventId(opinionEvent.getId());
        opinionEventStatistic.setInfoTotal(Integer.valueOf(String.valueOf(infototal)));
        opinionEventStatistic.setDataType(StringUtils.removeEnd(emotionSpread.toString(), "#"));
        opinionEventStatistic.setMediaType(StringUtils.removeEnd(mediaSpread.toString(), "#"));
        opinionEventStatistic.setSource(StringUtils.removeEnd(websiteSpread.toString(), "#"));
        
        OpinionEventWordsExample example = new OpinionEventWordsExample();
        example.createCriteria().andEventIdEqualTo(opinionEvent.getId()).andCycleEqualTo((byte)(int)3);
        List<OpinionEventWords> opinionEventWordsList = opinionEventWordsDao.selectByExampleWithBLOBs(example);
        if (opinionEventWordsList != null && opinionEventWordsList.size() > 0) {
            opinionEventStatistic.setWords(opinionEventWordsList.get(0).getWords());
        }
        opinionEventStatistic.setGmtCreate(new Date());
        opinionEventStatisticDao.insert(opinionEventStatistic);
    }
    
    /**  
     * 删除事件
     * @param opinionEvent 
     */
    public void deleteEvent(OpinionEvent opinionEvent) {
        opinionEvent.setIsDelete((byte)1);
        opinionEventDao.updateByPrimaryKeySelective(opinionEvent);
    }
    
    /**  
     * 获取事件列表
     * @param opinionEvent
     * @param pageNo
     * @param pageSize
     * @return 
     */
    public List<OpinionEvent> eventList(OpinionEvent opinionEvent, Integer pageNo, Integer pageSize) {
        PageBounds pageBounds = new PageBounds(pageNo, pageSize);
        OpinionEventExample  example = new OpinionEventExample();
        example.setOrderByClause("gmt_create DESC");
        Criteria criteria = example.createCriteria();
        if (opinionEvent.getRegion() != null) {
            criteria.andRegionEqualTo(opinionEvent.getRegion());
        }
        if (opinionEvent.getEventGroup() != null) {
            criteria.andEventGroupEqualTo(opinionEvent.getEventGroup());
        }
        criteria.andIsDeleteEqualTo((byte)0)
                .andFileReasonIsNull();
        return opinionEventDao.selectByExampleWithPageBounds(example, pageBounds);
    }
    
    
    /** 
     * 获取字典表 
     * @param parent
     * @return 
     */
    public List<OpinionDictionary> getDictionary(String parent) {
        OpinionDictionaryExample example = new OpinionDictionaryExample();
        example.createCriteria().andParentEqualTo(parent);
        return opinionDictionaryDao.selectByExample(example);
    }
    
    
    /**  
     * 获取事件相关的舆情列表
     * @param id
     * @param cycle
     * @param emotion
     * @param source
     * @param pageNo
     * @param pageSize
     * @return 
     */
    public  HashMap<String, Object> getEventInfoList(Long id, Integer cycle, Integer emotion, Integer source, Integer pageNo, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageBounds pb = new PageBounds(pageNo, pageSize);
        OpinionEsSearchVO esResult = esQueryService.queryEventOpinions(id, new DateTime(getStartDate(cycle)), emotion, 
            source, pb);
        //List<KeyValueVO> mediaTypeSta = esResult.getMediaTypeStats();
        //transToChinese(mediaTypeSta, "F");
        List<OpinionVO> opinions = BeanMapperUtil.mapList(esResult.getOpinions(), OpinionVO.class);
        opinions.forEach(o -> {
            o.setLevel(systemSettingService.judgeOpinionSettingClass(o.getHot()));
        });
        map.put("opinions", opinions);
        map.put("total", esResult.getTotal());
        //map.put("eventHot", opinionEventDao.selectByPrimaryKey(id).getHot());
       // mediaTypeSta.add(0, calTotal(mediaTypeSta));
        //map.put("mediaTypes", mediaTypeSta);
        return map;
        
    }
    
    public KeyValueVO calTotal(List<KeyValueVO> mediaTypeSta) {
        long total = 0;
        for (KeyValueVO vo : mediaTypeSta) {
            total = total + (long)vo.getValue();
        }
        KeyValueVO tmp = new KeyValueVO();
        tmp.setName("全部");
        tmp.setValue(total);
        return tmp;
        
    }
   
    
    /**  
     * 获取事件舆情列表的媒体标签
     * @param id
     * @param cycle
     * @param emotion
     * @return 
     */
    public  List<KeyValueVO> eventLabelList(Long id, Integer cycle, Integer emotion){
        PageBounds pb = new PageBounds(1, 1);
        OpinionEsSearchVO esResult = esQueryService.queryEventOpinions(id, new DateTime(getStartDate(cycle)), emotion, null, pb);
        return calAllMedia(esResult.getMediaTypeStats());
    }
    
    public List<KeyValueVO> calAllMedia(List<KeyValueVO> mediaTypeSta) {
        List<KeyValueVO> allMediaType =  new ArrayList<KeyValueVO>();
        List<OpinionDictionary> opinionDictionaryList = getDictionary("F");
        for (OpinionDictionary e : opinionDictionaryList) {
            for (KeyValueVO f : mediaTypeSta) {
                if (e.getCode().equals(String.valueOf(f.getKey()))) {
                    f.setName(e.getName());
                    allMediaType.add(f);
                } else {
                    KeyValueVO vo = new KeyValueVO();
                    vo.setKey(e.getCode());
                    vo.setName(e.getName());
                    vo.setValue(0l);
                    allMediaType.add(vo);
                }
            }
        }
        KeyValueVO tmp = calTotal(allMediaType);
        allMediaType.add(0, tmp);
        return allMediaType;
    }
    
    /**
     * 获取事件对应周期的信息总量  
     * @param id
     * @param cycle
     * @return 
     */
    public  long eventInfoTotal(Long id, Integer cycle){
        if (cycle != 4) {
            return esQueryService.queryEventInfoTotal(id, new DateTime(getStartDate(cycle)), null);
        } else {
            OpinionEventStatisticExample example = new OpinionEventStatisticExample();
            example.createCriteria().andEventIdEqualTo(id);
            return opinionEventStatisticDao.selectByExample(example).get(0).getInfoTotal();
        }
    }
    
    
    /**  
     * 获取事件的热度
     * @param id
     * @return 
     */
    public  Integer eventHotValue(Long id){
        return opinionEventDao.selectByPrimaryKey(id).getHot();
    }
    
    
    /**  
     * 获取事件总体走势
     * @param id
     * @param cycle
     * @return 
     */
    public Map<String, List<KeyValueVO>> eventWholeTrend(Long id, Integer cycle) {
        Map<String, List<KeyValueVO>> map = new HashMap<String, List<KeyValueVO>>();
        OpinionEvent opinionEvent = opinionEventDao.selectByPrimaryKey(id);
        List<KeyValueVO> infoList = esQueryService.queryEventInfoTotal(opinionEvent, false, cycle);
        List<KeyValueVO> warnList = esQueryService.queryEventInfoTotal(opinionEvent, true, cycle);
        map.put("infoList", infoList);
        map.put("warnList", warnList);
        return map;
    }
    
    
    /**  
     * 事件媒体分布
     * @param id
     * @param cycle
     * @return 
     */
    public List<KeyValueVO> eventSrcDis(Long id, Integer cycle) {
        List<KeyValueVO> rs = null;
        if (cycle.intValue() != 4) {
            rs = esQueryService.getEventOpinionMediaSpread(id, new DateTime(getStartDate(cycle)), null);
        } else {
            OpinionEventStatisticExample example = new OpinionEventStatisticExample();
            example.createCriteria().andEventIdEqualTo(id);
            OpinionEventStatistic opinionEventStatistic = opinionEventStatisticDao.selectByExampleWithBLOBs(example).get(0);
            rs = new ArrayList<KeyValueVO>();
            for (String e : opinionEventStatistic.getMediaType().split("#")) {
                KeyValueVO vo = new KeyValueVO();
                vo.setKey(e.split(",")[0]);
                vo.setValue(e.split(",")[1]);
                rs.add(vo);
            }
        }
        transToChinese(rs, "F");
        return rs;
       
    }
    
    
    /**  
     * 事件信息走势图
     * @param id
     * @param cycle
     * @return 
     */
    public List<List<KeyValueVO>> eventInfoTrend(Long id, Integer cycle) {
        List<OpinionDictionary> opinionDictionaryList = getDictionary("F");
        addAllToDic(opinionDictionaryList);
        //Date startDate = getStartDate(cycle);
        OpinionEventMediaStatisticExample example = new OpinionEventMediaStatisticExample();
        example.setOrderByClause("pick_time ASC");
        com.bbd.domain.OpinionEventMediaStatisticExample.Criteria criteria =  example.createCriteria();
        criteria.andEventIdEqualTo(id).andPickTimeIn(getDates(cycle, opinionEventDao.selectByPrimaryKey(id)));
        List<List<KeyValueVO>> list = new ArrayList<List<KeyValueVO>>();
        for (OpinionDictionary e : opinionDictionaryList) {
            criteria.andMediaCodeEqualTo(e.getCode());
            List<OpinionEventMediaStatistic> evtMediaStaList = opinionEventMediaStatisticDao.selectByExample(example);
            List<KeyValueVO> listSub = new ArrayList<KeyValueVO>();
            for (OpinionEventMediaStatistic f : evtMediaStaList) {
                KeyValueVO vo = new KeyValueVO();
                vo.setKey(f.getPickTime());
                vo.setName(e.getName());
                vo.setValue(f.getMediaCount());
                listSub.add(vo);
            }
            list.add(listSub);
        }
      return list;
       
    }
    
    public void addAllToDic(List<OpinionDictionary> opinionDictionaryList) {
        OpinionDictionary opinionDictionary = new OpinionDictionary();
        opinionDictionary.setCode("all");
        opinionDictionary.setName("全部");
        opinionDictionaryList.add(0, opinionDictionary);
    }
    
     public List<Date> getDates(int cycle, OpinionEvent opinionEvent) {
         DateTime now = DateTime.now();
         DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
         DateTime createTime = new  DateTime(opinionEvent.getGmtCreate());
         String yearMonDay = now.getYear() + "-" + now.getMonthOfYear() + "-" + now.getDayOfMonth() + " ";
         DateTime latestTime;
         List<Date> dates = new ArrayList<Date>();
         if (cycle == 1) {
             latestTime = DateTime.parse(
                 yearMonDay + (now.getHourOfDay() % 2 == 0 ? now.getHourOfDay() : now.getHourOfDay() - 1) + ":00:00", format);
             addToDates(dates, createTime, null, latestTime, 12, 2);
         } else if (cycle == 2) {
             latestTime = DateTime.parse(yearMonDay + (now.getHourOfDay() / 12 == 0 ? 0 : 12 + ":00:00"), format);
             addToDates(dates, createTime, null, latestTime, 14, 12);
         } else if (cycle == 3) {
             latestTime = DateTime.parse(yearMonDay + "00:00:00", format);
             addToDates(dates, createTime, null, latestTime, 30, 24);
         } else {
             now = new DateTime(opinionEvent.getGmtFile());
             latestTime = DateTime.parse(now.getYear() + "-" + now.getMonthOfYear() + "-" + now.getDayOfMonth() + " " + "00:00:00", format);
             addToDates(dates, createTime, now, latestTime, 365, 24);
         }
         return dates;
     } 
     

     public void addToDates(List<Date> dates, DateTime createTime, DateTime fileTime,DateTime latestDate, int days, int interval){
         for (int i = 0; i <= days -1; i++) {
             DateTime endTime = latestDate.minusHours(i * interval);
             if (createTime.getMillis() > endTime.getMillis()) {
                 break;
             }
             if (fileTime != null && 
                     endTime.getMillis() < fileTime.minusYears(1).getMillis()){
                 break;
             }
             dates.add(endTime.toDate());
         }
     }
    
     public Date getStartDate(int cycle) {
         Date startDate ;
         if (cycle == 1) {
             startDate = DateUtils.addDays(new Date(), -1);
         } else if (cycle == 2) {
             startDate = DateUtils.addDays(new Date(), -7);
         } else if (cycle == 3) {
             startDate = DateUtils.addDays(new Date(), -30);
         } else {
             startDate = DateUtils.addDays(new Date(), -365);
         }
         return startDate;
     }
    
    /**  
     * 事件媒体活跃度
     * @param id
     * @param cycle
     * @return 
     */
    public List<KeyValueVO> eventSrcActive(Long id, Integer cycle) {
        List<KeyValueVO> rs = null;
        if (cycle.intValue() != 4) {
            rs = esQueryService.getEventWebsiteSpread(id, new DateTime(getStartDate(cycle)), null);
        } else {
            OpinionEventStatisticExample example = new OpinionEventStatisticExample();
            example.createCriteria().andEventIdEqualTo(id);
            OpinionEventStatistic opinionEventStatistic = opinionEventStatisticDao.selectByExampleWithBLOBs(example).get(0);
            rs = new ArrayList<KeyValueVO>();
            for (String e : opinionEventStatistic.getSource().split("#")) {
                KeyValueVO vo = new KeyValueVO();
                vo.setKey(e.split(",")[0]);
                vo.setValue(e.split(",")[1]);
                rs.add(vo);
            }
        }
        return rs;
    }
    
    
    /**  
     * 事件走势
     * @param id
     * @param cycle
     * @param pageNo
     * @param pageSize
     * @return 
     */
    public HashMap<String, Object> eventTrend(Long id, Integer cycle, Integer pageNo, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (cycle != 4) {
            OpinionEsSearchVO vo = esQueryService.queryEventTrendOpinions(id, new DateTime(getStartDate(cycle)), null, new PageBounds(pageNo, pageSize));
            map.put("opinions", vo.getOpinions());
        } else {
            OpinionEventTrendStatisticExample example = new OpinionEventTrendStatisticExample();
            example.createCriteria().andEventIdEqualTo(id);
            map.put("opinions", opinionEventTrendStatisticDao.selectByExampleWithPageBounds(example, new PageBounds(pageNo, pageSize)));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(opinionEventDao.selectByPrimaryKey(id).getGmtCreate());
        map.put("eventTime", dateString);
        return map;
    }
    
    
    /** 
     * 事件关键词云 
     * @param id
     * @param cycle
     * @return 
     */
    public List<KeyValueVO> eventKeywords(Long id, Integer cycle) {
        List<KeyValueVO> words = new ArrayList<KeyValueVO>();
        OpinionEventWordsExample example = new OpinionEventWordsExample();
        example.createCriteria().andEventIdEqualTo(id).andCycleEqualTo((byte)(int)(cycle == 4 ? 3 : cycle));
        List<OpinionEventWords> opinionEventWordsList = opinionEventWordsDao.selectByExampleWithBLOBs(example);
        if (opinionEventWordsList == null || opinionEventWordsList.size() == 0) {
            return words;
        }
        for (String e : opinionEventWordsList.get(0).getWords().split("#")){
            KeyValueVO vo = new KeyValueVO();
            vo.setName(e.split(",")[0]);
            vo.setValue(e.split(",")[1]);
            words.add(vo);
        }
        return words;
    }
    
    
    /**
     * 事件数据类型  
     * @param id
     * @param cycle
     * @return 
     */
    public List<KeyValueVO> eventDataType(Long id, Integer cycle) {
        List<KeyValueVO> rs = null;
        if (cycle.intValue() != 4) {
            rs = esQueryService.getEventEmotionSpread(id, new DateTime(getStartDate(cycle)), null);
        } else {
            OpinionEventStatisticExample example = new OpinionEventStatisticExample();
            example.createCriteria().andEventIdEqualTo(id);
            OpinionEventStatistic opinionEventStatistic = opinionEventStatisticDao.selectByExampleWithBLOBs(example).get(0);
            rs = new ArrayList<KeyValueVO>();
            for (String e : opinionEventStatistic.getDataType().split("#")) {
                KeyValueVO vo = new KeyValueVO();
                vo.setKey(e.split(",")[0]);
                vo.setValue(e.split(",")[1]);
                rs.add(vo);
            }
        }
        transToChinese(rs, "H");
        return rs;
    }  
    
    
    /** 
     * 舆情事件类别分布 
     * @return 
     */
    public List<Graph> eventTypeDis() {
        return opinionEventDao.eventTypeDis();
    }
    /** 
     * 舆情事件地区分布 
     * @return 
     */
    public Map<String, Object> eventRegionDis() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Graph> gs = opinionEventDao.eventRegionDis();
        long max = 0;
        long min = 0;
        for (Graph e : gs) {
            if (max < (long)e.getValue()) {
                max = (long)e.getValue();
            } 
            if (min > (long)e.getValue()) {
                min = (long)e.getValue();
            } 
        }
        map.put("regioins", gs);
        map.put("max", max);
        map.put("min", min);
        return map;
    }
    
    
    public List<OpinionEvent> getHisEventList(String eventLevel, String region, Date startTime, Date endTime, Integer pageNo, Integer pageSize){
        OpinionEventExample example = new OpinionEventExample();
        Criteria criteria = example.createCriteria();
        if (eventLevel != null){
            criteria.andEventLevelEqualTo(eventLevel);
        }
        if (region != null){
            criteria.andRegionEqualTo(region);
        }
        criteria.andGmtCreateGreaterThanOrEqualTo(startTime)
        .andGmtCreateLessThanOrEqualTo(endTime)
        .andIsDeleteEqualTo((byte)0)
        .andFileReasonIsNotNull();
        List<OpinionEvent> eventList = opinionEventDao.selectByExampleWithPageBounds(example, new PageBounds(pageNo, pageSize));
        transToChinese(eventList);
        return eventList;
    }
    

    public void transToChinese(List<KeyValueVO> list, String type){
        List<OpinionDictionary> mediaList = getDictionary(type);
        for (KeyValueVO e : list) {
            for (OpinionDictionary f : mediaList) {
                if (e.getKey().equals(f.getCode())) {
                    e.setName(f.getName());
                    break;
                }
            }
        }
    }
    public void transToChinese(OpinionEvent e) {
        List<OpinionDictionary> eventGroupList = getDictionary("A");
        for (OpinionDictionary f : eventGroupList) {
            if (e.getEventGroup().equals(f.getCode())) {
                e.setEventGroup(f.getName());
                break;
            }
        }
        List<OpinionDictionary> monitorList = getDictionary("B");
        for (OpinionDictionary f : monitorList) {
            if (e.getMonitor().equals(f.getCode())) {
                e.setMonitor(f.getName());
                break;
            }
        }
        List<OpinionDictionary> regionList = getDictionary("C");
        for (OpinionDictionary f : regionList) {
            if (e.getRegion().equals(f.getCode())) {
                e.setRegion(f.getName());
                break;
            }
        }
        List<OpinionDictionary> eventLevelList = getDictionary("D");
        for (OpinionDictionary f : eventLevelList) {
            if (e.getEventLevel().equals(f.getCode())) {
                e.setEventLevel(f.getName());
                break;
            }
        }
        List<OpinionDictionary> fileReasonList = getDictionary("E");
        for (OpinionDictionary f : fileReasonList) {
            if (e.getFileReason().equals(f.getCode())) {
                e.setFileReason(f.getName());
                break;
            }
        }
    }
    
    public void transToChinese(List<OpinionEvent> eventList) {
        for (OpinionEvent e : eventList) {
            transToChinese(e);
        }
    }
    
}
