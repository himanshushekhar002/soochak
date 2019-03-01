package com.notificationlab.soochak.constants;

import lombok.Getter;

@Getter
public enum FileType {

	TXT(".txt"),HTML(".html");
	
	private String value;
	
	FileType(String type){
		this.value = type;
	}
}
