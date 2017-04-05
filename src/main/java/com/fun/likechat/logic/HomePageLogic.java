package com.fun.likechat.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.Column;
import com.fun.likechat.persistence.po.Tag;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.ColumnService;
import com.fun.likechat.service.TagService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.BannerVo;
import com.fun.likechat.vo.TagVo;

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
    
    /**
     * 获取标签列表（系统标签和自定义标签）
     * @return
     * @throws SQLException
     */
    public List<Tag> getAllTag() throws SQLException {
    	return tagService.getListByPo(new Tag());
    }
    
    /**
     * 获取所有系统标签列表
     * @return
     * @throws SQLException
     */
    public List<TagVo> getAllSystemTagVo() throws SQLException {
    	Tag tag = new Tag();
    	tag.setProperty(1);//1-system tag, 2-user self definition
    	tag.setState(1);
    	List<Tag> poList = tagService.getListByPo(tag);
    	List<TagVo> voList = new ArrayList<TagVo>();
    	for(Tag po : poList) {
    		TagVo vo = new TagVo();
    		vo.setIdentifying(po.getIdentifying());
    		vo.setPic(po.getPic());
    		vo.setTagName(po.getTagName());
    		voList.add(vo);
    	}
    	return voList;
    }
    
    /**
     * 根据频道标识，获取子频道列表；（很多栏目的展现都是通过频道来设定）
     * @param identifying
     * @return
     * @throws Exception
     */
    public List<Column> getSubColumnsById(String identifying) throws Exception {
    	List<Column> columns = columnService.getSonColumnByIdentifying(identifying);
    	return columns;
    }
    
    /**
     * 根据banner列表，通过频道的概念实现；
     * @param identifying
     * @return
     * @throws Exception
     */
    public List<BannerVo> getBannerListVo(String identifying) throws Exception {
    	List<Column> columns = columnService.getSonColumnByIdentifying(identifying);
    	List<BannerVo> vos = new ArrayList<BannerVo>();
    	for(Column po : columns) {
    		BannerVo vo = new BannerVo();
    		vo.setIdentifying(po.getIdentifying());
    		vo.setDisplayName(po.getDisplayName());
    		vo.setIcon(po.getIcon());
    		vo.setVisitUrl(po.getVisitUrl());
    		vos.add(vo);
    	}
     	return vos;
    }
    
    /**
     * 首页随机获取20个主播列表--没有tag条件；
     * @param limit
     * @return
     * @throws Exception
     */
    public List<ActorVo> getRandomActorsVo(int limit) throws Exception {
    	List<Actor> actors = actorService.getRandomActors(limit);
    	List<ActorVo> vos = new ArrayList<ActorVo>();
    	for(Actor po : actors) {
    		ActorVo vo = new ActorVo();
    		vo.setId(po.getId());
    		vo.setIdcard(po.getIdcard());
    		vo.setNickname(po.getNickname());
    		vo.setSex(po.getSex());
    		vo.setAge(DateUtil.getPersonAgeByBirthDate(po.getBirthday()));
    		vo.setIcon(po.getIcon());
    		vo.setSignature(po.getSignature());
    		vos.add(vo);
    	}
    	return vos;
    }
    
    /**
     * 根据tag标签随机获取指定数量的主播
     * @param map
     * @return
     * @throws Exception
     */
    public List<ActorVo> getRandomActorsByCondition(Map<String, Object> map) throws Exception {
    	List<Actor> actors = actorService.getRandomActorsByCondition(map);
    	List<ActorVo> vos = new ArrayList<ActorVo>();
    	for(Actor po : actors) {
    		ActorVo vo = new ActorVo();
    		vo.setId(po.getId());
    		vo.setIdcard(po.getIdcard());
    		vo.setNickname(po.getNickname());
    		vo.setSex(po.getSex());
    		vo.setAge(DateUtil.getPersonAgeByBirthDate(po.getBirthday()));
    		vo.setIcon(po.getIcon());
    		vo.setSignature(po.getSignature());
    		vos.add(vo);
    	}
    	return vos;
    }
    
    
}
