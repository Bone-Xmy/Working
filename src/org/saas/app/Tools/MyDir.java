package org.saas.app.Tools;

import org.saas.app.Tools.MyUUID;
import java.io.File;

public class MyDir{
	public String getDir(){
		//在/Users/bone/myWork/source/下创建一个以UUID为名称的文件,此时只有File对象，不会创建文件
		File newFile = new File("/Users/bone/myWork/source/" + new MyUUID().getUUID());
		//创建目录
		newFile.mkdir();
		return newFile.getAbsolutePath();
	}
}
