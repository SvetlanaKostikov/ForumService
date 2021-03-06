package telran.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.forum.model.Post;

public interface ForumRepository extends MongoRepository<Post, String> {
	Iterable<Post>findByTagsIn(List<String>tags);
	Iterable<Post>findByAuthor(String author);
	Iterable<Post>findByDateTimeBetween(LocalDate from,LocalDate to);
	Stream<Post>findAllBy();

}
