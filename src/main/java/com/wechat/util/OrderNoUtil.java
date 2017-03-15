package com.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoUtil {
	
	/**
	 * 生成订单编号
	 * @param preFixString
	 * @return
	 */
	public synchronized static  String getOrderNumber(String preFixString) {

		 Date today = new Date();
		 int orderIndex = 0;
		Date n = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(n);

		if (orderIndex > 0) {
			if (n.getYear() == today.getYear() && n.getMonth() == today.getMonth() && n.getDay() == today.getDay()) {
				orderIndex += 1;
			} else {
				today = n;
				orderIndex = 1;
			}
		} else {
			today = n;
			orderIndex = 1;
		}
		if (orderIndex > 999999) {
			orderIndex = 1;
		}
		String indexString = String.format("%s%06d", currTime, orderIndex);
		String orderNumberString = preFixString + indexString;
		return orderNumberString;
	}
	
	/**
	 * 生成流水号
	 * @return
	 */
	public synchronized static String getSerialNumber() {
		String a = (int)((Math.random()*9+1)*10000000)+"";
		String b = System.currentTimeMillis()+"";
		return a+b;
	}
}
