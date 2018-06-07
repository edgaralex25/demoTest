package am.test.edo.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApiMockApplication {

	@RequestMapping(value = "/api/image", method = POST)
	public String postImage(@RequestBody String text) {
            return text;
	}
 
	public static void main(String[] args) {
		SpringApplication.run(ApiMockApplication.class, args);
	}
}