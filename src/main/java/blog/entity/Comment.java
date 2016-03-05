package blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by dengwei on 16/3/5.
 */
@Entity(name = "comment")
public class Comment {
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "reply_id")
	private int replyId;

	@Column(name = "article_id")
	private int articleId;

	@Column(name = "comment")
	private String comment;

	@Column(name = "score")
	private int score;

	@Column(name = "author")
	private String author;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
