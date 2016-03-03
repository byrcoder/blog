package blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by dengwei on 16/2/14.
 */
@Entity(name = "article")
public class Article {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "type")
	private int type;

	@Column(name = "author")
	private String author;

	@Column(name = "content")
	private String content;

	@Column(name = "publishTime")
	private long publishTime;


	//private long updateTime;

	@Column(name = "refer")
	private String refer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}


	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}
}
