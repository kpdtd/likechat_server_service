package com.fun.likechat.service;

import java.util.List;

import com.fun.likechat.persistence.po.Column;


public interface ColumnService {
    
    public Column getColumnById(Integer id) throws Exception;

    public Column getColumnByIdentifying(String identifying) throws Exception;
    
    public List<Column> getSonColumnByIdentifying(String identifying) throws Exception;

//    // 按频道规则查询视频表
//    public List<Video> getVideosByRegulation(String regulation) throws Exception;
//
//    // 按频道规则查询主播表
//    public List<Actor> getActorsByRegulation(String regulation) throws Exception;
//
//    // 按人工查询视频关联表,参数为频道ID
//    public List<Video> getVideosByColumnId(Integer columnId) throws Exception;
//
//    // 按人工查询主播关联表,参数为频道ID
//    public List<Actor> getActorsByColumnId(Integer columnId) throws Exception;
//    
//    //对象转换 视频列表转换成PerVideoData
//    public List<PerVideoData> video2PerVideoData(List<Video> videos)throws Exception;
//    
//  //对象转换 主播列表转换成PerVideoData
//    public List<PerVideoData> actor2PerVideoData(List<Actor> actors,int count)throws Exception;
//    
//  //对象转换 主播列表转换成AnchorData
//    public List<AnchorData> actor2AnchorData(List<Actor> actors)throws Exception; 
//    
//    //分页相关
//    // 分页方法 视频关系表
//    int rVideoCount(Map<String, Object> dataMap) throws Exception;
//
//    List<Video> getVideosPageListByColumnId(Map<String, Object> dataMap)
//            throws Exception;
//
//    // 主播关系表
//    int rActorCount(Map<String, Object> dataMap) throws Exception;
//
//    List<Actor> getActorsPageListByColumnId(Map<String, Object> dataMap)
//            throws Exception;

} 
