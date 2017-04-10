package com.fun.likechat.constant;

public interface Constant {

    public static final int PAGEZISE = 20;// 分页常量

    // ******************************************
    public static final int TYPE_ACTOR = 1;// 主播
    public static final int TYPE_VIDEO = 2;// 视频
    public static final int TYPE_COMMENT = 3;// 评论

    // *******************系统配置***********************
    public static final int ACTOR_RANDOM_LIMIT = 20;// 首页随机获取主播的列表

    // 类型定义：
    public static final int MSG_TYPE_SYS = 1; // 系统
    public static final int MSG_TYPE_FEEDBACK = 2; // 反馈消息
    public static final int MSG_TYPE_NOTIFICATION = 1000; // 通知栏显示

    // header头
    public static final String request_header_encrypt = "header-encrypt-code";

}
