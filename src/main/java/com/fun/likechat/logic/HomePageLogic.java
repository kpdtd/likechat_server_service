package com.fun.likechat.logic;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
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
import com.fun.likechat.persistence.po.YunxinAccid;
import com.fun.likechat.service.ActorDynamicPvService;
import com.fun.likechat.service.ActorService;
import com.fun.likechat.service.ColumnService;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.service.TagService;
import com.fun.likechat.service.UserAttentionService;
import com.fun.likechat.service.YunxinAccidService;
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

	// @Autowired
	// DictionaryService dictionaryService;

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
	
	@Autowired
	YunxinAccidService yunxinAccidService;

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
		tag.setProperty(1);// 1-system tag, 2-user self definition
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
			vo.setToken(po.getOpenId());
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
			vo.setToken(po.getOpenId());
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
	public ActorPageVo getActorPage(int id, String accid) throws Exception {
    	// 获取主播的基本信息,通过id或云信accid 两者之一获取
		Actor actor = null;
		if(id > 0) {
			actor = actorService.getById(id);
		}else if(StringUtils.isNotEmpty(accid)){
			YunxinAccid yunxinaccid = yunxinAccidService.getByAccid(accid);
			if(yunxinaccid != null) {
				Actor tempActor = new Actor();
				tempActor.setOpenId(yunxinaccid.getOpenid());
				List<Actor> listactor = actorService.getListByPo(tempActor);
				if(listactor != null && !listactor.isEmpty()) {
					actor = listactor.get(0);
				}
			}
		}
    	
    	if(actor == null || actor.getState() != 1) {
    		return null;//主播不存在或状态为不可用；
    	}
    	// 获取主播最新的8张照片
    	Map<String, Object> condition = new HashMap<String, Object>();
    	condition.put("actorId", id);
    	condition.put("type", 2);
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
    	String iconHttpUrl = actor.getIcon() == null ? null : httpPath + actor.getIcon(); 
    	apvo.setId(actor.getId());
    	apvo.setIcon(iconHttpUrl);//这里转成可以访问的地址；
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
    	apvo.setVoiceSec(getDuration(actor.getVideoShow()));
    	apvo.setPicList(picList);//照片地址列表
    	// 
    	apvo.setSignature(actor.getSignature());
    	apvo.setIntroduction(actor.getIntroduction());
    	apvo.setPrice(actor.getPrice() == null ? "0币/分" : String.valueOf(actor.getPrice()) + "币/分");//db直接录入币的价格
    	//获取数据字典里的平台价格：
    	dictionary = dictionaryService.getDicByKey("PLATFORM_PRICE");
		String platformPrice = dictionary.getValue();
    	apvo.setPlatformPrice(platformPrice+"币/分");
    	apvo.setTotalPrice((actor.getPrice() + Integer.parseInt(platformPrice)) + "币/分");
    	// 获取通话时长：应该动态计算，这里按填入假信息（真正推广修改）
    	apvo.setCallTime((new Random().nextInt(30)+8) + "小时" + (new Random().nextInt(60)) + "分钟");
    	// 新增：手机号   qq  微信  身高  weight  concept;//恋爱观		objective;//目的
    	apvo.setPhone(actor.getPhone());
    	apvo.setQq(actor.getQq());
    	apvo.setWechat(actor.getWechat());
    	apvo.setHight(actor.getHight());
    	apvo.setWeight(actor.getWeight());
    	apvo.setConcept(actor.getConcept());
    	apvo.setObjective(actor.getObjective());
    	
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
	        System.out.println("不需要处理的异常，用户不登录也可以看到主播vo");
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

	/**
	 * 获取音视频播放长度
	 * 参考：http://blog.csdn.net/luohai859/article/details/52496054
	 * JAVE requires a J2SE environment 1.4 or later and a Windows or Linux OS on a i386 / 32 bit hardware architecture
	 * @param path
	 * @return
	 */
	private int getDuration(String path) {
		String localPath = null;
		try {
			if(StringUtils.isEmpty(path)) {
				return 0;
			}
			DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH);
			localPath = dictionary.getValue();
			File source = new File(localPath + path);
			Encoder encoder = new Encoder();
			MultimediaInfo m = encoder.getInfo(source);
			long ls = m.getDuration();
			System.out.println("此视频时长为:" + ls / 60000 + "分" + ls / 1000 + "秒！");
			return (int) (ls / 1000);
		}
		catch(Exception e) {
			System.out.println("获取音频播放长度异常" + localPath + path);
			e.printStackTrace();
		}
		return 0;
	}

}
