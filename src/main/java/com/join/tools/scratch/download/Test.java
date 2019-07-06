package com.join.tools.scratch.download;

import com.join.tools.http.ByteResponse;
import com.join.tools.http.HttpClientTools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Join
 * @date ：Created in 2019/6/24 20:46
 * @modified By：
 */
public class Test {
	public static void main(String[] args) throws IOException {

		String path=Test.class.getResource("/scratch").getPath();
		List<String> list= md5List(path);

		list.forEach(System.out::println);
		System.out.println(list.size());

		//测试10个
		list=list.stream().limit(10).collect(Collectors.toList());

		String urlPath="https://assets.scratch.mit.edu/internalapi/asset/${md5}/get/";

		for (String md5 : list) {

			urlPath=urlPath.replace("${md5}",md5);
			System.out.println(urlPath);
			ByteResponse response= HttpClientTools.doGetToByte(urlPath);

			System.out.println("contentType="+response.getContentType()
					+" , length="+response.getBytes().length
					+" , contentLength="+response.getContentLength());

		}

	}

	private static List<String> md5List(String dirPath) {
		List<String> list=new ArrayList<>();
		File parent =new File(dirPath);
		File[] files=parent.listFiles();
		for (File file : files) {

			try(FileReader fileReader=new FileReader(file)){
				try(BufferedReader reader=new BufferedReader(fileReader)){
					String str=reader.readLine();
					StringBuilder stringBuilder=new StringBuilder();
					while(str!=null){
						stringBuilder.append(str);
						str=reader.readLine();
					}

					int index=-1;

					do {
						String flag="\"md5\":";
						index = stringBuilder.indexOf(flag);
						if(index>=0){
							int beginIndex = index + flag.length();
							int endIndex=stringBuilder.indexOf("\",",beginIndex);
							String md5 = stringBuilder.substring(beginIndex, endIndex);
							list.add(md5.trim().replace("\"",""));
							stringBuilder.replace(index, endIndex, "");
						}

					}while (index>=0);

					//System.out.println(stringBuilder.toString());
					//System.out.println("\n============================================\n");
				}catch (IOException e){
					e.printStackTrace();;
				}

			}catch (IOException e){
				e.printStackTrace();;
			}

		}
		return list;
	}
}
