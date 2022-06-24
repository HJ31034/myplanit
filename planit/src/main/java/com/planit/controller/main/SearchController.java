package com.planit.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/planit")
public class SearchController {
	@GetMapping("/search")
	public String search() {
		return "main/search";
	}
}
