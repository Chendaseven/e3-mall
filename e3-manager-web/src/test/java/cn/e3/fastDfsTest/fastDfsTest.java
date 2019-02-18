package cn.e3.fastDfsTest;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import util.FastDFSClient;

/**
 * fastDfs服务器上传文件测试
 * @ClassName fastDfsTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年2月17日 下午4:30:12
 */

public class fastDfsTest {
	@Test
	public void fSDTest() throws IOException, MyException {
		//1、创建配置文件，配置文件的内容为tracker服务器的ip地址
		ClientGlobal.init("G:\\Java\\MyProjects\\e3-manager-web\\src\\main\\resources\\config\\client.conf");
		//2、创建一个TrackerClient对象，直接new一个
		TrackerClient trackerClient = new TrackerClient();
		//3、使用TrackerClient创建连接，获得TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//4、创建StorageServer的引用，值为null
		StorageServer storageServer = null;
		//5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient storageClient = new StorageClient1(trackerServer, storageServer);
		//6、使用StorageClient对象上传图片。
		//使用StorageClient上传图片会返回图片的文件名
		String[] strings = storageClient.upload_appender_file("H:\\temp\\0236.jpg", "jpg", null);
		//7、返回数组。包含组名和图片的路径。
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void fsdUpload() throws Exception {
		FastDFSClient fastDfsClient = new FastDFSClient("G:\\Java\\MyProjects\\e3-manager-web\\src\\main\\resources\\config\\client.conf");
		String fileName = fastDfsClient.uploadFile("H:\\temp\\0236.jpg", "jpg");
		System.out.println(fileName);
	}
	

}
