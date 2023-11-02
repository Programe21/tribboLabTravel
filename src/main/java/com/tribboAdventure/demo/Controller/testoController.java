package com.tribboAdventure.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class testoController {

	@RequestMapping("")
	public String test() {
		return "bienvenidaTribbo";
	}
}


// despues borrar esta clase