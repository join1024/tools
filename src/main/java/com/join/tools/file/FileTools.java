package com.join.tools.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Join
 * @date ：Created in 2020/1/15 10:55
 * @modified By：
 */
public class FileTools {

	public static String path="E:\\课程资料-未整理\\视频（1-4）\\第4课（B4）";

	public static void main(String[] args) {
		File file=new File(path);

		File[] list=file.listFiles();
		for (File f : list) {
			System.out.println(f.getName());
		}
	}
}
