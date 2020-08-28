package com.join.tools.mp3;

import com.mpatric.mp3agic.*;

import java.io.File;

/**
 * mp3agic 一个修改mp3文件属性信息工具库
 * https://github.com/mpatric/mp3agic
 *
 * @author ：Join
 * @date ：Created in 2019/6/19 14:59
 */
public class Mp3Tools {

	public static void main(String[] args) {

		String dir="C:\\Users\\join0\\Downloads\\";
		String destDir="C:\\Users\\join0\\Downloads\\dest\\";
		File parent=new File(dir);
		File[] files=parent.listFiles();
		System.out.println("===========");
		for (File file : files) {
			if(file.getName().endsWith(".mp3")){
				try {
					Mp3File mp3file = new Mp3File(file);
					ID3v1 id3v1Tag;

					if (mp3file.hasId3v2Tag()) {
						id3v1Tag =  mp3file.getId3v2Tag();
					} else if(mp3file.hasId3v1Tag()){
						id3v1Tag =  mp3file.getId3v1Tag();
					} else {
						// mp3 does not have an ID3v1 tag, let's create one..
						id3v1Tag = new ID3v1Tag();
						mp3file.setId3v1Tag(id3v1Tag);
					}
					//id3v1Tag.setTrack("5");
					id3v1Tag.setArtist("纪涵邦");
					//把mp3标题修改成与文件名一致,在音乐播放器列表中显示的是title,而不是文件名，可以根据自己的需求修改title
					id3v1Tag.setTitle("汉朝那些事_"+file.getName());
					id3v1Tag.setAlbum("汉朝那些事");
					id3v1Tag.setYear("2019");
					id3v1Tag.setGenre(12);
					id3v1Tag.setComment("modify by mp3agic");
					mp3file.save(destDir+file.getName());

					//System.out.println(file.getName()+ " process success: ");
				} catch (Exception e) {
					System.out.println(file.getName()+ " process fail: "+e.getLocalizedMessage());
					//e.printStackTrace();
				}
			}
		}

	}



}
