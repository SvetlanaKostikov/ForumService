package telran.forum.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of= {"id"})
@Document(collection="forum_users")
@Builder
public class UserAccount {
	@Id
	String id;
	String password;
	String firstName;
	String lastName;
	LocalDateTime expDate;
	@Singular//mojno dobavlyat po odnomu elementu a ne peredavat srazu Set
	Set<String>roles;
	

}
