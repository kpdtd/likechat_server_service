package com.fun.likechat.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.Constant;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.persistence.po.Actor;
import com.fun.likechat.persistence.po.ActorDynamicPv;
import com.fun.likechat.persistence.po.Column;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.persistence.po.Tag;
import com.fun.likechat.persistence.po.UserAttention;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.ColumnService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.TagService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.util.DateUtil;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.vo.ActorPageVo;
import com.fun.likechat.vo.ActorVo;
import com.fun.likechat.vo.BannerVo;
import com.fun.likechat.vo.TagVo;

@Service
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

    @Autowired
    ActorDynamicPvService actorDynamicPvService;
    
    @Autowired
    UserAttentionService userAttentionService;
    
    @Autowired
    DictionaryService dictionaryService;
    
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
    	DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
		String httpPath = dictionary.getValue();
    	for(Actor po : actors) {
    		ActorVo vo = new ActorVo();
    		vo.setId(po.getId());
    		vo.setIdcard(po.getIdcard());
    		vo.setNickname(po.getNickname());
    		vo.setSex(po.getSex());
    		vo.setAge(DateUtil.getPersonAgeByBirthDate(po.getBirthday()));
    		vo.setIcon(httpPath + po.getIcon());
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
    	
    	DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
		String httpPath = dictionary.getValue();
    	for(Actor po : actors) {
    		ActorVo vo = new ActorVo();
    		vo.setId(po.getId());
    		vo.setIdcard(po.getIdcard());
    		vo.setNickname(po.getNickname());
    		vo.setSex(po.getSex());
    		vo.setAge(DateUtil.getPersonAgeByBirthDate(po.getBirthday()));
    		vo.setIcon(httpPath + po.getIcon());
    		vo.setSignature(po.getSignature());
    		vos.add(vo);
    	}
    	return vos;
    }
    
    /**
     * 根据id获取主播详情信息
     * ------------缺少价格和币的转换，也可以直接录入币的价格；---地址转换未完成
     * @param map
     * @return
     * @throws Exception
     */
    public ActorPageVo getActorPage(int id) throws Exception {
    	// 获取主播的基本信息
    	Actor actor = actorService.getById(id);
    	if(actor == null || actor.getState() != 1) {
    		return null;//主播不存在或状态为不可用；
    	}
    	// 获取主播最新的8张照片
    	Map<String, Object> condition = new HashMap<String, Object>();
    	condition.put("actorId", id);
    	condition.put("limit", 8);
    	List<ActorDynamicPv> adpvs = actorDynamicPvService.getLimitListByMap(condition);
    	List<String> picList = new ArrayList<String>();//照片地址存储list
    	DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH_HTTP);
		String httpPath = dictionary.getValue();
    	for(ActorDynamicPv po : adpvs) {
    		picList.add(httpPath + po.getSavePath());//这里转成可以访问的地址；
    	}
    	// 获取主播已经通话的时长：--- 需要表计算；未完成
    	
    	
    	// 封装客户端展示的数据vo
    	ActorPageVo apvo = new ActorPageVo();
    	apvo.setIcon(httpPath + actor.getIcon());//这里转成可以访问的地址；
    	apvo.setNickname(actor.getNickname());
    	apvo.setSex(actor.getSex());
    	apvo.setAge(DateUtil.getPersonAgeByBirthDate(actor.getBirthday()));
    	apvo.setIdcard(String.valueOf(actor.getIdcard()));
    	apvo.setProvince(actor.getProvince());
    	apvo.setCity(actor.getCity());
    	apvo.setFans(actor.getFans() == null ? "0" : String.valueOf(actor.getFans()));
    	apvo.setAttention(actor.getAttention() == null ? "0" : String.valueOf(actor.getAttention()));
    	//语音秀地址  和  8张最新照片地址
    	apvo.setVideoUrl(httpPath + actor.getVideoShow());//换成可以访问的http地址
    	apvo.setPicList(picList);//照片地址列表
    	// 
    	apvo.setIntroduction(actor.getIntroduction());
    	apvo.setPrice(actor.getPrice() == null ? "0币/分钟" : String.valueOf(actor.getPrice()) + "币/分钟");//缺少价格和币的转换，也可以直接录入币的价格
    	apvo.setIsAttention(false);//先赋值false，下面在判断是否已经关注
    	// 返回是否已经关注此主播；
    	try {
    		BeatContext beatContext = RequestUtils.getCurrent();//获取不到会抛出异常，捕获。因为用户可以不登录
        	Integer userId = beatContext.getUserid();
        	if(userId != null) {
        		UserAttention ua = new UserAttention();
        		ua.setUserId(userId);
        		ua.setActorId(id);
        		List<UserAttention> uaList = userAttentionService.getListByPo(ua);
        		if(uaList != null && !uaList.isEmpty()) {
        			apvo.setIsAttention(true);
        		}
        	}
        }
        catch(Exception e) {
	        // 不需要处理
        }
    	return apvo;
    }
    
    /**
     * 增加对某主播的关注
     * @param map
     * @return
     * @throws Exception
     */
    public void addAttention(UserAttention attention) throws Exception {
    	List<UserAttention> uaList = userAttentionService.getListByPo(attention);
		if(uaList == null || uaList.isEmpty()) {
			userAttentionService.insert(attention);
		}
    }
    
    /**
     * 取消对某主播的关注
     * @param map
     * @return
     * @throws Exception
     */
    public void cancelAttention(int userId, int actorId) throws Exception {
    	userAttentionService.cancelAttention(userId, actorId);
    }
    
}
