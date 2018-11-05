package telran.forum;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.dao.UserAccountRepository;
import telran.forum.domain.UserAccount;

@SpringBootApplication
public class ForumServiceAuthWithSpringApplication implements CommandLineRunner{
	@Autowired
	UserAccountRepository userRepository;
	@Autowired
	AccountConfiguration configuration;
	
	public static void main(String[] args) {
		SpringApplication.run(ForumServiceAuthWithSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!userRepository.existsById("root")) {
			String hashPassword = configuration.getPasswordEncoder("root");
			UserAccount userAccount = UserAccount.builder()
					.id("root")
					.password(hashPassword)
					.firstName("Super")
					.lastName("Admin")
					.role("ROLE_ADMIN")
					.role("ROLE_USER")
					.role("ROLE_MODERATOR")
					.expDate(LocalDateTime.now().plusYears(25))
					.build();
			userRepository.save(userAccount);
			
		}
		}
		
	}

