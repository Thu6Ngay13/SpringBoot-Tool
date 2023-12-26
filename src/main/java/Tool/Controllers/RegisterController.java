package Tool.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

	@GetMapping("/tool")
	public String showFormRegister() {
		return "register";
	}
}
