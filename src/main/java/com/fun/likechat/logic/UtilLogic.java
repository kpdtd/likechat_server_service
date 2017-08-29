package com.fun.likechat.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.likechat.constant.Constant;
import com.fun.likechat.persistence.po.DataDictionary;
import com.fun.likechat.service.DictionaryService;
import com.fun.likechat.util.FileUtil;
import com.fun.likechat.util.LogFactory;
@Service
public class UtilLogic {

	@Autowired
	DictionaryService dictionaryService;

	private static final Logger logger = LogFactory.getInstance().getLogger();

	/*
	 * 给一个网络图片的url，抓取并存到服务器指定位置
	 */
	public String savePic(String httpUrl) throws Exception {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			byte[] data = readInputStream(inStream);
			String path = savePic(data, httpUrl, true);// 存到服务器
			// 创建输出流
			inStream.close();
			conn.disconnect();
			return path;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 给一个图片字节数组， 和一个带后缀的图片名称，存到服务器指定位置
	 * 参数：图片byte    图片名：主要取格式     是否返回相对地址（false反回绝对地址）
	 */
	public String savePic(byte[] picByte, String picName, boolean isRelative) {
		FileOutputStream out = null;
		try {
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			String fileName = System.currentTimeMillis() + "." + picName.substring(picName.lastIndexOf(".") + 1);
			DataDictionary dictionary = dictionaryService.getDicByKey(Constant.D_IMG_SAVE_PATH);
			String rootPath = FileUtil.createFilePath(dictionary.getValue());// 返回一个日期文件夹，不含数据字典前缀
			String imgPath = dictionary.getValue() + File.separator + rootPath + File.separator + fileName;
			out = new FileOutputStream(imgPath);
			out.write(picByte);
			if(isRelative) {
				return rootPath + File.separator + fileName;
			} else {
				return imgPath;//返回服务器全路径
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(out != null) {
				try {
					out.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public  byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 把outStream里的数据写入内存
		byte[] result = outStream.toByteArray();
		outStream.close();
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		//new一个URL对象  
        URL url = new URL("http://www.192tt.com/d/file/p/2016-11-30/jhej1jeavjh2768.jpg");  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性 
        UtilLogic ug =  new UtilLogic();
        byte[] data = ug.readInputStream(inStream);  
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        File imageFile = new File("/Users/suntao/work/apache-tomcat-7.0.67/webapps/img/a.jpg");  
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        //写入数据  
        outStream.write(data);  
        //关闭输出流  
        outStream.close();  
    }
}
