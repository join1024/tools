## 一个很好的mp3处理命令行工具 

### 命令参考  
http://blog.sina.com.cn/s/blog_5d0f322c0102x6pz.html  
```shell
# 一条命令音频编辑-截取MP3音乐文件片段（MP3文件裁切）
$ ffmpeg -i source_mp3.mp3 -ss 00:00:25 -t 00:25:00 -acodec copy output_mp3.mp3
# 源文件：source_mp3
# 转码开始时间位置：-ss
# 转码结束时间位置：-t
# 原始编码（表示不转码）：-acodec
# 拷贝输出到指定目录：copy output_mp3.mp3
```

### 遍历指定目录对mp3文件进行裁剪    
听有声读物时，广告太烦人，比如前20秒的广告可以通过此命令批量处理 

1. 下载ffmpeg   
windows下载地址如下：https://ffmpeg.zeranoe.com/builds/  

2. 安装ffmpeg  
windows下直接双击安装即可  

3. window批处理命令进行裁剪     
```bat
@echo off
D:
rem 进入ffmpeg安装目录
cd D:\ProgramDev\ffmpeg-20190705-a514244-win64-static\bin

rem 遍历指定目录下的mp3
for %%s in (C:\Users\join0\Downloads\dest\*.mp3) do (
	rem 得到mp3文件名，包含路径
	echo %%s
	rem 拼接得到将要输出的mp3文件名，包含路径
	echo %%s.dest.mp3
	rem 从25秒开始剪切去掉前面25秒的广告
	ffmpeg -i %%s -ss 00:00:25 -acodec copy %%s.dest.mp3
)
pause
```     
