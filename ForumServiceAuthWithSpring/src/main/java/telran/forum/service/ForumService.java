package telran.forum.service;

import java.util.List;

import telran.forum.domain.Post;
import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;

public interface ForumService {


	Post getPost(String id);

	Post removePost(String id, String login);

	Post updatePost(PostUpdateDto updatePost, String login);

	boolean addLike(String id);

	Post addComment(String id, NewCommentDto newComment);
	
	Iterable<Post> findByTags(List<String> tags);
	
	Iterable<Post> findByAuthor(String author);
	
	Iterable<Post> findByDate(DatePeriodDto period);

	Post addNewPost(NewPostDto newPost, String login);

}