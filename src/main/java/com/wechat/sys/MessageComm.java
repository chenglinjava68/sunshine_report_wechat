package com.wechat.sys;

import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/8.
 */
public class MessageComm implements MessageCreator {
    //消息数据
    protected HashMap<String,Object> messageData;
    //消息类型
    private MessageType messageType;
    public MessageComm(HashMap message,MessageType messageType){
        this.messageData = message;
        this.messageType=messageType;
    }
    @Override
    public Message createMessage(Session session) throws JMSException {
        // TODO Auto-generated method stub
        ObjectMessage messages = session.createObjectMessage();
        messages.setObject(messageData);
        messages.setJMSType(messageType.getTypeCode());
        if(session.getTransacted()){
            session.commit();
        }
        return messages;
    }
}
