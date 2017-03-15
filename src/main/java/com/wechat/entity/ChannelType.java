package com.wechat.entity;

public class ChannelType {

	private ChannelTypes channelTypes;

	public ChannelTypes getChannelTypes() {
		return channelTypes;
	}

	public void setChannelTypes(ChannelTypes channelTypes) {
		this.channelTypes = channelTypes;
	}

	public enum ChannelTypes{
		SUNTYPE ("YJLT");
		private final String value;
		
		public String getValue() {
            return value;
        }

		ChannelTypes(String value) {
            this.value = value;
        }
	}
	
}
