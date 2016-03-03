package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by dengwei on 16/1/27.
 */
@RestController
public class Hello {

	public static class DemoEntity {
		public String name;

		public String user;
	}

	@RequestMapping("/hello")
	public DemoEntity print() {
		DemoEntity entity = new DemoEntity();
		entity.name = ("dw");
		entity.user = ("userId");
		DispatcherServlet dispatcherServlet;
		return entity;
	}
}
