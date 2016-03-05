package blog.web;

import blog.entity.Article;
import blog.entity.Comment;
import blog.service.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by dengwei on 16/3/5.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

	@Autowired
	CacheManager manager;

	@Autowired
	DB db;

	@RequestMapping(value="/{id}")
	public String Article(Model model, @PathVariable int id) {
		Article article;

		Cache arts = manager.getCache("article");
		if(null != arts.get(id).get()) {
			article = (Article) arts.get(id).get();
		} else {
			article = db.list(Article.class, id);
			arts.put(id, article);
		}

		if(article != null) {

			Cache comment = manager.getCache("comment");
			if(comment.get(id).get() != null) {
				model.addAttribute("comments", comment.get(id).get());
			} else {
				List<Comment> comments = db.list(Comment.class, "articleId", id);
				comment.put(id, comments);
				model.addAttribute("comments", comments);
			}
		}

		model.addAttribute("article", article);

		System.out.println(article + "\t---" + article);

		return "body/main/article";
	}
}
