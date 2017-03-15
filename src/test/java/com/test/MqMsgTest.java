package com.test;

import com.alibaba.fastjson.JSON;
import com.wechat.sys.MessageComm;
import com.wechat.sys.MessageQueueName;
import com.wechat.util.message.QueueMessageUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public class MqMsgTest extends BaseTest {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Test
    public void sendAndReceiveMSg(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("namge",109);
        try{

        }catch (Exception ex){
                ex.printStackTrace();
        }
    }

}
