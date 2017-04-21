package com.fun.likechat.yunxin;

public interface YXConstant {

    public static final String CREATE_URL="https://api.netease.im/nimserver/user/create.action";//云信ID创建
    public static final String UPDATE_URL="https://api.netease.im/nimserver/user/update.action";//云信ID更新
    public static final String REFRESHTOKEN_URL="https://api.netease.im/nimserver/user/refreshToken.action";//TOKEN更新
    public static final String UPDATE_UINFO_URL="https://api.netease.im/nimserver/user/updateUinfo.action";//更新用户名片
    public static final String ADD_FRIEND_URL="https://api.netease.im/nimserver/friend/add.action";//加好友
    public static final String UPDATE_FRIEND_URL="https://api.netease.im/nimserver/friend/update.action";//更新好友相关信息
    public static final String DEL_FRIEND_URL="https://api.netease.im/nimserver/friend/delete.action";//删除好友相关信息
    public static final String GET_FRIEND_URL="https://api.netease.im/nimserver/friend/get.action";//获取好友列表相关信息
    public static final String SET_SPECIAL_URL="https://api.netease.im/nimserver/user/setSpecialRelation.action";//设置黑名单/静音
    public static final String LIST_BLACKANDMUTELIST_URL="https://api.netease.im/nimserver/user/listBlackAndMuteList.action";//查看用户的黑名单和静音列表
    public static final String SEND_MSG_URL="https://api.netease.im/nimserver/msg/sendMsg.action";//发送普通消息

}
