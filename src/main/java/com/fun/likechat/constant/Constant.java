package com.fun.likechat.constant;

public interface Constant {

//******************************************
public static final int TYPE_ACTOR=1;//主播
public static final int TYPE_VIDEO=2;//视频
public static final int  TYPE_COMMENT=3;//评论

//*******************系统配置***********************
public static final int ACTOR_RANDOM_LIMIT = 20;//首页随机获取主播的列表

//类型定义：
public static final int MSG_TYPE_SYS = 1; //系统
public static final int MSG_TYPE_FEEDBACK = 2; //反馈消息
public static final int MSG_TYPE_NOTIFICATION = 1000; //通知栏显示


    //(数据字典配置项)
    //banner图片轮训时间
    public static final String D_HOST="D_HOST";//server访问地址
    public static final String D_BANNER_TIME="D_BANNER_TIME";
    public static final String D_IMG_HOST="D_IMG_HOST";//图片服务器地址
    public static final String D_IMG_SAVE_PATH="D_IMG_SAVE_PATH";//图片保存根目录
    public static final String D_VIDEO_HOST="D_VIDEO_HOST";//视频服务器地址
    public static final String D_VIDEO_SAVE_PATH="D_VIDEO_SAVE_PATH";//视频保存根目录
    public static final String D_DEFAULT_ACTOR_IMG="D_DEFAULT_ACTOR_IMG";//默认的主播图片
    public static final String SMS_CONTENT="验证码:";
   public static final String SMS_SEND_MAX="SMS_SEND_MAX";
    //人工规则
    public static final String R_WORKER_DO="R_WORKER_DO";//人工
    //1、个性推荐
    public static final String C_SPECIAL_AREA="C_SPECIAL_AREA";//1.*为该频道下面的子频道
    //1.1 banner区域
    public static final String C_SPECIAL_BANNER="C_SPECIAL_BANNER";
    //1.2 分类区域
    public static final String C_SPECIAL_CLASSIFY="C_SPECIAL_CLASSIFY";
    //1.2.1 分类的3个icon，写死配置(杨海需求)
    public static final String C_ACTOR_CLASSIFY_ICON="C_ACTOR_CLASSIFY_ICON";//最优先级的主播分类
    public static final String C_RANKINGLIST_ACTOR_ICON="C_RANKINGLIST_ACTOR_ICON";//最优先级的榜单
    public static final String C_RANKINGLIST_VIDEO_ICON="C_RANKINGLIST_VIDEO_ICON";//最优先级的播单
    //推荐主播(推荐列表6个主播)
    public static final String C_ACTOR_RECOMMEND="C_ACTOR_RECOMMEND";
    
    //推荐主播更多页面(直接跳转到直播频道)
   // public static final String C_ACTOR_RECOMMEND_MORE="C_ACTOR_RECOMMEND_MORE";
    //1.3 精品视频(推荐列表)
    public static final String C_VIDEO_RECOMMEND="C_VIDEO_RECOMMEND";
    //1.4精品视频更多页面
    public static final String C_VIDEO_RECOMMEND_MORE="C_VIDEO_RECOMMEND_MORE";
    //1.5精品播单
    public static final String C_BOUTIQUE_VIDEO="C_BOUTIQUE_VIDEO";//
    //1.6精品播单更多页面
    public static final String C_BOUTIQUE_VIDEO_MORE="C_BOUTIQUE_VIDEO_MORE";
    //1.7最新热评
    public static final String C_HOT_COMMENT="C_HOT_COMMENT";
    //1.8最新视频
    public static final String C_NEW_VIDEO="C_NEW_VIDEO";
    //1.9最新视频更多页面
    public static final String C_NEW_VIDEO_MORE="C_NEW_VIDEO_MORE";
    
    //2、榜单
    public static final String C_RANKING_AREA="C_RANKING_AREA";
    //2.1榜单列表
    public static final String C_RANKING_LIST="C_RANKING_LIST";
    //2.2榜单更多主播列表页面
   // public static final String C_RANKING_LIST_MORE="C_RANKING_LIST_MORE";
    
    //3、直播频道
    public static final String C_LIVE_AREA="C_LIVE_AREA";
    //3.1主播分类标签(8个)
    public static final String C_LIVE_CLASSIFY="C_LIVE_CLASSIFY";
    //主播推荐列表
    public static final String C_LIVE_ACTOR_RECOMMEND="C_LIVE_ACTOR_RECOMMEND";
    //4、播单
    public static final String C_VIDEO_PLAY_AREA="C_VIDEO_PLAY_AREA";
    //4.1播单列表
    public static final String C_VIDEO_PLAY_LIST="C_VIDEO_PLAY_LIST";
    //5、我的
    public static final String C_MY_AREA="C_MY_AREA";
    //6、我要做主播
    public static final String C_DO_ACTOR_AREA="C_DO_ACTOR_AREA";
    
    
  //header头
    public static final String request_header_encrypt = "header-encrypt-code";
    
    //banner跳转配置项
    
    
}
