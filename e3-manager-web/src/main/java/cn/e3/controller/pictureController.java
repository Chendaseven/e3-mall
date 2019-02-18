package cn.e3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import util.FastDFSClient;
/**
 * 图片上传web层
 * @ClassName pictureController
 * @Description 
 * @Author Chen Peng
 * @Date 2019年2月17日 下午7:11:42
 */

@Controller
public class pictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map uploadFile(MultipartFile uploadFile) {
//		1、接收页面传递的图片信息uploadFile
		String originalFilename = uploadFile.getOriginalFilename();
		//获取文件的扩展名
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

		try {
//			2、把图片上传到图片服务器。使用封装的工具类实现。需要取文件的内容和扩展名。
			FastDFSClient fastDfsClient = new FastDFSClient("classpath:config/client.conf");
//			3、图片服务器返回图片的url,同时上传文件
			String path = fastDfsClient.uploadFile(uploadFile.getBytes(), extName);
//			4、将图片的url补充完整，返回一个完整的url。
			String url = IMAGE_SERVER_URL + path;
//			5、把返回结果封装到一个Map对象中返回。
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			String json = JSON.toJSONString(result);
			
			//返回值为String解决浏览器兼容性问题
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("erro", 1);
			result.put("message", "图片上传失败");
			String json = JSON.toJSONString(result);
			return result;
			
		}
	}
}
