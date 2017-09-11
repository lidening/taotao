package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {
	
	@Value("${FTP_ADDRESS}")
	public String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	public Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	public String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	public String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	public String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	public String IMAGE_BASE_URL;
	
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap=new HashMap<>();
		try {
			//生成新的文件名
			//取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			//生成新的文件名
	//		UUID randomUUID = UUID.randomUUID();
			String newName = IDUtils.genImageName();
			newName=newName+oldName.substring(oldName.lastIndexOf("."));
			//图片上传
			String imagePath =new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,imagePath , newName, uploadFile.getInputStream());
			if(!result){
				resultMap.put("error", 1);
				resultMap.put("message", "上传失败");
				return resultMap;
			}
			 
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
