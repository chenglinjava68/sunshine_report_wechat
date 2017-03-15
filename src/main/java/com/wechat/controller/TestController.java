package com.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.sys.MessageQueueName;
import com.wechat.sys.MessageType;
import com.wechat.util.message.QueueMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Message;
import java.util.HashMap;


/**
 * Created by :Guozhihua
 * Date： 2017/3/8.
 */
@Controller
public class TestController {

    @Autowired
    private QueueMessageUtils messageUtils;
    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/testMessage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse detail(String userId, String mobile) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            for(int i=0;i<10;i++){
                HashMap<String,Object> map = new HashMap<>();
                map.put("namge",i);
                messageUtils.sendSerializableMessage(MessageQueueName.textMessageQueue,map, MessageType.CLASS_MODULE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

}
