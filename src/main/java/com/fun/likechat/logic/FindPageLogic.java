package com.fun.likechat.logic;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.fun.likechat.interceptor.ActionResult;
import com.fun.likechat.interceptor.BeatContext;
import com.fun.likechat.interceptor.RequestUtils;
import com.fun.likechat.util.LogFactory;

@Service
public class FindPageLogic {
    private static final Logger logger = LogFactory.getInstance().getLogger();

    public ActionResult getFindList(int tag, String stamp) throws Exception {
        switch (tag) {
            case 1:
                return getNewestDynamic(stamp);
            case 2:
                return getHotDynamic(stamp);
            case 3:
                return getAttentionDynamic(stamp);

        }
        return ActionResult.fail();
    }

    /*
     * 最新动态
     */
    private ActionResult getNewestDynamic(String stamp) {
        
        return ActionResult.fail();
    }

    /*
     * 热门
     */
    private ActionResult getHotDynamic(String stamp) {
        return ActionResult.fail();
    }

    /*
     * 关注
     */
    private ActionResult getAttentionDynamic(String stamp) {
        //获取用户信息
        BeatContext beatContext= RequestUtils.getCurrent();
        if(beatContext!=null && beatContext.getUserid()>0){
            
        }else {
            logger.debug("没有用户信息");
        }
        return ActionResult.fail();
    }
}
