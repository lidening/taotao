package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FTPTest {
	@Test
	public void testFtpClient() throws SocketException, IOException{
		FTPClient ftpClient=new FTPClient();
		ftpClient.connect("192.168.0.157",21);
		ftpClient.login("ftpuser", "user");
		FileInputStream fileInputStream=new FileInputStream(new File("C:\\Users\\lidening\\Pictures\\Saved Pictures\\timg.jpg"));
		ftpClient.changeWorkingDirectory("/home/ftpuser/images");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.storeFile("hello.jpg", fileInputStream);
		ftpClient.logout();
		
	}
}
