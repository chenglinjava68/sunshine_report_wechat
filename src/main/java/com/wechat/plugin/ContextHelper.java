package com.wechat.plugin;


import com.wechat.plugin.StrategyManager;
import com.wechat.util.SpringBeanUtils;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */
public class ContextHelper {

    private static final StrategyManager strategyManager =(StrategyManager) SpringBeanUtils.getBean("strategyManager");
    public static  StrategyManager getStrategyManager(){
        return strategyManager;
    }
}
