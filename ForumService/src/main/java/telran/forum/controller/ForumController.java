package telran.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.json.dto.DatePeriodDto;
import telran.json.dto.NewCommentDto;
import telran.json.dto.NewPostDto;
import telran.json.dto.PostUpdateDto;
import telran.json.model.Post;
import telran.json.service.ForumService;


@RestController
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
	public Post removeStudent(@PathVariable String id) {
		return forumService.removePost(id);
	}
	@PutMapping("/updatepost")
	public Post updatePost (@RequestBody PostUpdateDto post) {
		return forumService.updatePost(post);
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
