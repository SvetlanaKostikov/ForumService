package telran.json.service;

import java.util.List;

import telran.json.dto.DatePeriodDto;
import telran.json.dto.NewCommentDto;
import telran.json.dto.NewPostDto;
import telran.json.dto.PostUpdateDto;
import telran.json.model.Post;

public interface ForumService {
	Post addNewPost(NewPostDto newPost);
	Post getPost(String id);
	Post removePost(String id);
	Post updatePost(PostUpdateDto updatePost);
	boolean addLike(String id);
	Post addComment(String id, NewCommentDto newComment);
	Iterable<Post>getPostByTags(List<String>tags);
	Iterable <Post>getPostByAuthor(String author);
	Iterable <Post>getPostByDates(DatePeriodDto period);
	
	

}
