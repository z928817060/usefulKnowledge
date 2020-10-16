package com.study.usefulknowledge.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONConfigUtil {

	/**
	 * 将json写成文件
	 * @param filepath
	 * @param object
	 * @return
	 */
	public static String writeToFile(String filepath, Object object){
		if(object == null || filepath == null)
			return null;
		String jsonStr = JSONObject.toJSONString(object);
		FileOutputStream out = null;
		File file = new File(filepath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			out = new FileOutputStream(file);
			out.write(jsonStr.getBytes("utf-8"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filepath;
	}
	
	public static  <T> T parseObject(String filepath, Class<T> clazz) {
		if (filepath == null)
			return null;
		if (!new File(filepath).exists())
			return null;
		FileInputStream in = null;
		T object = null;
		try {
			in = new FileInputStream(filepath);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			String jsonStr = new String(bytes, "utf-8");
			object = JSON.parseObject(jsonStr, clazz);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}
}
