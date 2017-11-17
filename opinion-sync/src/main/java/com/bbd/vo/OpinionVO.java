/**
 * BBD Service Inc
 * All Rights Reserved @2017
 */
package com.bbd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Kafka推送的舆情数据
 * @author tjwang
 * @version $Id: OpinionVO.java, v 0.1 2017年10月31日 下午3:07:22 tjwang Exp $
 */
public class OpinionVO {

    private String     uuid;

    private String     title;

    private String     summary;

    private Float      hot;

    private String     content;

    private String     source;

    private String     link;

    private Integer    similiarCount;

    private Integer    commentCount;

    private Float      emotion;

    private String     keyword;

    private String     keys;

    private String     website;

    private Integer    mediaType;

    private List<Long> events;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date       publishTime;

    @Override
    public String toString() {
        return "OpinionVO{" + "uuid='" + uuid + '\'' + ", title='" + title + '\'' + ", summary='" + summary + '\'' + ", hot=" + hot + ", content='" + content + '\'' + ", source='" + source + '\''
               + ", link='" + link + '\'' + ", similiarCount=" + similiarCount + ", commentCount=" + commentCount + ", emotion=" + emotion + ", keyword=" + keyword + ", keys=" + keys + ", website='"
               + website + '\'' + ", mediaType=" + mediaType + ", events=" + events + ", publishTime=" + publishTime + '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getHot() {
        //return hot;
        if (hot < 0) {
            return 0;
        }
        return Float.valueOf(hot).intValue();
    }

    public void setHot(Float hot) {
        this.hot = hot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getSimiliarCount() {
        return similiarCount;
    }

    public void setSimiliarCount(Integer similiarCount) {
        this.similiarCount = similiarCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getEmotion() {
        return emotion == 0 ? 0 : emotion > 0 ? 1 : -1;
    }

    public void setEmotion(Float emotion) {
        this.emotion = emotion;
    }

    public List<String> getKeyword() {
        if (keyword != null && !keyword.equals("")) {
            return Arrays.asList(keyword.split(","));
        }
        return Lists.newArrayList();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getKeys() {
        if (keys != null && !keys.equals("")) {
            return Arrays.asList(keys.split(","));
        }
        return Lists.newArrayList();
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
