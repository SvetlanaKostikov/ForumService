package telran.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;
import telran.forum.model.Post;
import telran.forum.service.ForumService;


@RestController
@RequestMapping("/forum")
public class ForumController {
	@Autowired
	ForumService forumService;
	
	@PostMapping("/post/newpost")
	public Post addPost(@RequestBody NewPostDto post) {
		return forumService.addNewPost(post);
	}
	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable String id) {
		return forumService.getPost(id);
	}
	@DeleteMapping("/post/{id}")
	public Post removeStudent(@PathVariable String id,@RequestHeader(value = "Authorization")String auth) {
		return forumService.removePost(id,auth);
	}
	@PutMapping("/updatepost")
	public Post updatePost (@RequestBody PostUpdateDto post,@RequestHeader(value = "Authorization")String auth) {
		return forumService.updatePost(post,auth);
	}
	@PutMapping("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	}
	@PutMapping("/post/{id}/comment")
	public Post addComment(@PathVariable String id,@RequestBody NewCommentDto comment) {
		return forumService.addComment(id, comment);
	}
	@PostMapping("posts/tags")
	public Iterable<Post>getPostByTags(@RequestBody List<String>tags){
		return forumService.getPostByTags(tags);
	}
	@GetMapping("posts/author/{author}")
	public Iterable <Post>getPostByAuthor(@PathVariable String author){
		return forumService.getPostByAuthor(author);
	}
	@PostMapping("posts/dates")
	public Iterable <Post>getPostBetweenDates(@RequestBody DatePeriodDto period){
		return forumService.getPostByDates(period);
	}

}
