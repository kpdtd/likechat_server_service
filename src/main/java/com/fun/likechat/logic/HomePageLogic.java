package com.fun.likechat.logic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.likechat.constant.Constant;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.Column;
import com.fun.likechat.persistence.po.Tag;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.ColumnService;
import com.fun.likechat.service.TagService;
import com.fun.likechat.util.LogFactory;

public class HomePageLogic {
	private static final Logger logger = LogFactory.getInstance().getLogger();

    @Autowired
	TagService tagService;
    
//    @Autowired
//    DictionaryService dictionaryService;
    
    @Autowired
    ColumnService columnService;
    
    @Autowired
    ActorService actorService;
    
    //获取标签列表（系统标签和自定义标签）
    public List<Tag> getAllTag() throws SQLException {
    	return tagService.getListByPo(new Tag());
    }
    
    //获取所有系统标签列表
    public List<Tag> getAllSystemTag() throws SQLException {
    	Tag tag = new Tag();
    	tag.setProperty(1);//1-system tag, 2-user self definition
    	return tagService.getListByPo(tag);
    }
    
    //根据频道标识，获取子频道列表；
    public List<Column> getSubColumnsById(String identifying) throws Exception {
    	List<Column> columns = columnService.getSonColumnByIdentifying(identifying);
    	return columns;
    }
    
    //首页随机获取20个主播列表；
    public List<Actor> getRandomActors() throws Exception {
    	int limit = Constant.ACTOR_RANDOM_LIMIT;
    	List<Actor> actors = actorService.getRandomActors(limit);
    	return actors;
    }
    
    
}
