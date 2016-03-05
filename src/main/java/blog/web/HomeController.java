package blog.web;

import blog.entity.Article;
import blog.service.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by dengwei on 16/3/3.
 */

@Controller
public class HomeController {

	@Autowired
	CacheManager manager;

	@Autowired
	DB db;

	@RequestMapping(value="/")
	public String Home(Model model) {
		List<Article> articles;

		Cache cache = manager.getCache("list");

		String key = "all-article";
		if(null != cache.get(key).get()) {
			articles = (List<Article>) cache.get(key).get();
		} else {
			articles = db.list(Article.class);
			cache.put(key, articles);
		}

		model.addAttribute("articles", articles);

		System.out.println(articles + "\t---" + articles);

		return "body/main/home";
	}





}
