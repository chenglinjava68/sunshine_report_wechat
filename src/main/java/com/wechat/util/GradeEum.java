
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:GradeEum.java 
	 * 包名:com.wechat.util 
	 * 创建日期:2017年3月13日上午10:22:19 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.util;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：GradeEum    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月13日 上午10:22:19    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月13日 上午10:22:19    
 * 修改备注：       
 * @version </pre>    
 */

public enum GradeEum {
	ONE_GRADE("一年级",10l),
	TWO_GRADE("二年级",11l),
	THREE_GRADE("三年级",12l),
	FOUR_GRADE("四年级",13l),
	FIVE_GRADE("五年级",14l),
	SIX_GRADE("六年级",15l),
	SEVEN_GRADE("七年级",7l),
	EIGHT_GRADE("八年级",8l),
	NINE_GRADE("九年级",9l),
	TEN_GRADE("高一",4l),
	ELEVEN_GRADE("高二",5l),
	TWELVE_GRADE("高三",6l);
	private GradeEum(String gradeName, Long gradeCode) {
		this.gradeName = gradeName;
		this.gradeCode = gradeCode;
	}

	private String gradeName;
	
	private Long gradeCode;

	public String getGradeName() {
	
		return gradeName;
	}

	public void setGradeName(String gradeName) {
	
		this.gradeName = gradeName;
	}

	public Long getGradeCode() {
	
		return gradeCode;
	}

	public void setGradeCode(Long gradeCode) {
	
		this.gradeCode = gradeCode;
	}
	public static Long getGradeCode(String gradeName) {
        for (GradeEum g : GradeEum.values()) {
            if (g.getGradeName().equals(gradeName)) {
                return g.gradeCode;
            }
        }
        return null;
    }
}

	