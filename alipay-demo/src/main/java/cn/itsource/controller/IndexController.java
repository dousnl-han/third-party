package cn.itsource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alipay")
public class IndexController {
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
