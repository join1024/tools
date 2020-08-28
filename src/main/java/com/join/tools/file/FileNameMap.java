package com.join.tools.file;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Join
 * @date ：Created in 2020/1/15 11:01
 * @modified By：
 */
public class FileNameMap {
	public static Map<String,String> map=new HashMap<>();

	static{
		for(int i=1;i<9;i++){
			map.put("B"+i,appendStr("T1",i));
		}
		map.put("C1",appendStr("T1",9));
		map.put("C2",appendStr("T1",10));
		for(int i=11;i<9;i++){
			map.put("B"+i,appendStr("T1",i));
		}
	}

	private static String appendStr(String levelSequence,int lessonSequence){
		return levelSequence+"_第"+lessonSequence+"课";
	}
}
