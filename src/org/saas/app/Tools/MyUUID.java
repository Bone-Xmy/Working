package org.saas.app.Tools;

import java.util.UUID;

public class MyUUID{
	public String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();;
		return uuid;
	}
}
