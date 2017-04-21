package com.fun.likechat.yunxin.im;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.yunxin.YXConstant;
import com.fun.likechat.yunxin.YXHttpClient;
import com.fun.likechat.yunxin.data.GetFriendsListResultInfo;
import com.fun.likechat.yunxin.data.GetFriendsResData;
import com.fun.likechat.yunxin.data.ListBlackAndMuteListResultInfo;
import com.fun.likechat.yunxin.data.ResultInfo;

@Service
public class FriendsAction {

	/**
	 * 
	 * @param accid
	 *            加好友发起者accid
	 * @param faccid
	 *            加好友接收者accid
	 * @param type
	 *            1直接加好友，2请求加好友，3同意加好友，4拒绝加好友
	 * @param msg
	 *            加好友对应的请求消息，第三方组装，最长256字符
	 * @return code 200成功，其他失败
	 * @throws Exception
	 */
	public String addFriend(String accid, String faccid, String type, String msg) throws Exception {
		String result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("faccid", faccid));
		nvps.add(new BasicNameValuePair("type", type + ""));
		nvps.add(new BasicNameValuePair("msg", msg));
		try {
			String json = YXHttpClient.postRequest(YXConstant.ADD_FRIEND_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求加好友错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改好友信息，前提是必须是好友关系
	 * 
	 * @param accid
	 *            发起者accid
	 * @param faccid
	 *            要修改朋友的accid
	 * @param alias
	 *            给好友增加备注名，限制长度128
	 * @param ex
	 *            修改ex字段，限制长度256
	 * @return
	 * @throws Exception
	 */
	public String updateFriend(String accid, String faccid, String alias, String ex) throws Exception {
		String result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("faccid", faccid));
		nvps.add(new BasicNameValuePair("alias", alias));
		nvps.add(new BasicNameValuePair("ex", ex));
		try {
			String json = YXHttpClient.postRequest(YXConstant.UPDATE_FRIEND_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求修改好友信息错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param accid
	 * @param faccid
	 * @return
	 * @throws Exception
	 */
	public String delFriend(String accid, String faccid) throws Exception {
		String result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("faccid", faccid));
		try {
			String json = YXHttpClient.postRequest(YXConstant.DEL_FRIEND_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求删除好友信息错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取好友列表
	 * 
	 * @param accid
	 * @param createtime
	 *            格式：1440037706987
	 * @return List<GetFriendsResData>
	 * @throws Exception
	 */
	public List<GetFriendsResData> getFriends(String accid, String createtime) throws Exception {
		List<GetFriendsResData> result = new ArrayList<>();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("createtime", createtime));
		try {
			String json = YXHttpClient.postRequest(YXConstant.GET_FRIEND_URL, nvps);
			GetFriendsListResultInfo info = JsonHelper.toObject(json, GetFriendsListResultInfo.class);
			if ("200".equals(info.getCode())) {
				if (info.getSize() > 0) {
					result = info.getFriends();
				} else {
					LogFactory.getInstance().getLogger().debug("请求获取好友列表信息,好友为0：" + json);
				}
			} else {
				LogFactory.getInstance().getLogger().debug("请求获取好友列表信息错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param accid
	 *            用户帐号，最大长度32字符，必须保证一个APP内唯一
	 * @param targetAcc
	 *            被加黑或加静音的帐号
	 * @param relationType
	 *            本次操作的关系类型,1:黑名单操作，2:静音列表操作
	 * @param value
	 *            操作值，0:取消黑名单或静音，1:加入黑名单或静音
	 * @return 200成功
	 * @throws Exception
	 */
	public String setSpecialRelation(String accid, String targetAcc, int relationType, int value) throws Exception {
		String result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		nvps.add(new BasicNameValuePair("targetAcc", targetAcc));
		nvps.add(new BasicNameValuePair("relationType", relationType + ""));
		nvps.add(new BasicNameValuePair("value", value + ""));
		try {
			String json = YXHttpClient.postRequest(YXConstant.SET_SPECIAL_URL, nvps);
			ResultInfo info = JsonHelper.toObject(json, ResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = "200";
			} else {
				LogFactory.getInstance().getLogger().debug("请求黑名单/静音接口错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param accid
	 * @return 返回两个列表
	 * @throws Exception
	 */
	public ListBlackAndMuteListResultInfo listBlackAndMuteList(String accid) throws Exception {
		ListBlackAndMuteListResultInfo result = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("accid", accid));
		try {
			String json = YXHttpClient.postRequest(YXConstant.LIST_BLACKANDMUTELIST_URL, nvps);
			ListBlackAndMuteListResultInfo info = JsonHelper.toObject(json, ListBlackAndMuteListResultInfo.class);
			if ("200".equals(info.getCode())) {
				result = info;
			} else {
				LogFactory.getInstance().getLogger().debug("请求查看用户的黑名单和静音列表错误：" + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
