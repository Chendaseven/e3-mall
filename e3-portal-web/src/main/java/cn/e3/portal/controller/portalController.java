package cn.e3.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class portalController {
	
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
}
