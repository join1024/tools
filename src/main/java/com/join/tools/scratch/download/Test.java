package com.join.tools;

import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;
import java.net.ResponseCache;
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

		List<String> list=list("C:\\Users\\join0\\Desktop\\素材");

		System.out.println(list.size());
		list.forEach(System.out::println);

		list=list.stream().limit(100).collect(Collectors.toList());

		String urlPath="https://assets.scratch.mit.edu/internalapi/asset/${md5}/get/";

		for (String s : list) {

			urlPath=urlPath.replace("${md5}",s);
			System.out.println(urlPath);
			ByteResponse response=HttpClientTools.doGetToByte(urlPath);

			System.out.println("contentType="+response.getContentType()
					+" , length="+response.getBytes().length
					+" , contentLength="+response.getContentLength());

		}

		HttpClientTools.doGetToByte("https://assets.scratch.mit.edu/internalapi/asset/83a9787d4cb6f3b7632b4ddfebf74367.wav/get/");

	}

	private static List<String> list(String dirPath) {
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
