package com.fun.likechat.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class AudioVideoUtil {
	/**
	 * 返回文件时长--秒
	 * 		采用jave开源项目，仅支持win和linux；  mac下测试报错
	 * @param path
	 * @return
	 */
	public static int getDuration(String path) {
		if(StringUtils.isNotEmpty(path)) {
			File source = new File(path);
			Encoder encoder = new Encoder();
			try {
				MultimediaInfo m = encoder.getInfo(source);
				long ls = m.getDuration();
				return (int) (ls / 1000);
				// System.out.println("此视频时长为:"+ls/60000+"分"+(ls/000)/1000+"秒！");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
