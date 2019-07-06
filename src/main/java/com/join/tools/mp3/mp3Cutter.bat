@echo off
D:
cd D:\ProgramDev\ffmpeg-20190705-a514244-win64-static\bin

rem 遍历指定目录
for %%s in (C:\Users\join0\Downloads\dest\*.mp3) do (
	rem 得到mp3文件名，包含路径
	echo %%s
	rem 拼接得到将要输出的mp3文件名，包含路径
	echo %%s.dest.mp3
	rem 从25秒开始剪切去掉前面25秒的广告
	ffmpeg -i %%s -ss 00:00:25 -acodec copy %%s.dest.mp3

)
pause