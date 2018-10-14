package telran.forum.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.dao.ForumRepository;
import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;
import telran.forum.model.Comment;
import telran.forum.model.Post;

@Service
public class ForumSeviceImpl implements ForumService {
	@Autowired
	ForumRepository forumRepository;

	@Override
	public Post addNewPost(NewPostDto newPost) {
		Post post = new Post(newPost.getTitle(), newPost.getContent(), 
				newPost.getAuthor(), newPost.getTags());
		
		return forumRepository.save(post);
	}

	@Override
	public Post getPost(String id) {
		return forumRepository.findById(id).orElse(null);
	}

	@Override
	public Post removePost(String id) {
		Post post = forumRepository.findById(id).orElse(null);
		if(post!=null) {
			forumRepository.delete(post);
		}
		return post;
	}

	@Override
	public Post updatePost(PostUpdateDto updatePost) {
		Post post = getPost(updatePost.getId());
		post.setContent(updatePost.getContent());
		forumRepository.save(post);
		return post;
	}

	@Override
	public boolean addLike(String id) {
		Post post = getPost(id);
		if(post==null) {
			return false;
		}
		post.addLike();
		forumRepository.save(post);
		return true;
	}

	@Override
	public Post addComment(String id, NewCommentDto newComment) {
		Post post = getPost(id);
		Comment comment = new Comment(newComment.getUser(),newComment.getMessage());
		if(post!=null) {
			post.addComment(comment);
			forumRepository.save(post);
		}
		return post;
	}

	@Override
	public Iterable<Post> getPostByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags);
	}

	@Override
	public Iterable<Post> getPostByAuthor(String author) {
		return forumRepository.findByAuthor(author);
	}

	@Override
	public Iterable<Post> getPostByDates(DatePeriodDto period) {
		LocalDate dateFrom = LocalDate.parse(period.getFrom());
		LocalDate dateTo = LocalDate.parse(period.getTo());
		return forumRepository.findByDateTimeBetween(dateFrom, dateTo);
	}

}
