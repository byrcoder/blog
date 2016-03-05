package blog.web;

import blog.entity.Comment;
import blog.service.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dengwei on 16/3/5.
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	CacheManager manager;

	@Autowired
	DB db;

	@RequestMapping(value="/add/{id}")
	@ResponseBody
	public String Article(Model model, @PathVariable int id,
						  @RequestParam String comment) {
		Comment ct = new Comment();
		ct.setArticleId(id);
		ct.setComment(comment);
		db.save(ct);
		return "ok";
	}
}
