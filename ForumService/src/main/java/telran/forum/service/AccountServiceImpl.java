package telran.forum.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.UserAccountRepository;
import telran.forum.model.UserAccount;
import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.exception.ForbiddenException;
import telran.forum.exception.UserExistExeption;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountConfiguration accountConfiguration;
	@Autowired
	UserAccountRepository userRepository;

	@Override
	public UserProfileDto addUser(UserRegisterDto userRegDto, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		if(userRepository.existsById(credentials.getLogin())) {
			throw new UserExistExeption();
		}
		String hashPassword = BCrypt.hashpw(credentials.getPassword(),BCrypt.gensalt());//nado dobavit biblioteku jbcrypt
		UserAccount userAccount = UserAccount.builder()
				.id(credentials.getLogin())
				.password(hashPassword)
				.firstName(userRegDto.getFirstName())
				.lastName(userRegDto.getLastName())
				.role("User")
				.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
				.build();
		userRepository.save(userAccount);
		return new UserProfileDto(credentials.getLogin(),userRegDto.getFirstName(),userRegDto.getLastName());
	}
	@Override
	public UserProfileDto editUser(UserRegisterDto userRegDto, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		UserAccount userAccount = userRepository.findById(credentials.getLogin()).get();
		userAccount.setFirstName(userRegDto.getFirstName());
		userAccount.setLastName(userRegDto.getLastName());
		userRepository.save(userAccount);
		return new UserProfileDto(credentials.getLogin(),
				userRegDto.getFirstName(), userRegDto.getLastName());
	}

	@Override
	public UserProfileDto removeUser(String id, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		UserAccount user = userRepository.findById(credentials.getLogin()).orElse(null);
		Set<String>roles = user.getRoles();
		boolean hasRight = roles.stream()
				.anyMatch(s->"Admin".equals(s)||"Moderator".equals(s));
		hasRight = hasRight || credentials.getLogin().equals(id);
		if(!hasRight) {
			throw new ForbiddenException();
		}
		
		UserAccount userAccount = userRepository.findById(id).get();
		userRepository.delete(userAccount);
		return new UserProfileDto(userAccount.getId(), userAccount.getFirstName(), userAccount.getLastName());
	}
	@Override
	public Set<String> addRole(String id, String role, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		UserAccount user = userRepository.findById(credentials.getLogin()).get();
		if(!user.getRoles().contains("Admin")) {
			throw new ForbiddenException();
		}
		UserAccount userAccount = userRepository.findById(id).orElse(null);
		if (userAccount == null) {
			return null;
		}
		Set<String> roles = userAccount.getRoles();
		roles.add(role);
		userRepository.save(userAccount);
		return roles;
		
	}

	@Override
	public Set<String> removeRole(String id, String role, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		UserAccount user = userRepository.findById(credentials.getLogin()).get();
		if(!user.getRoles().contains("Admin")) {
			throw new ForbiddenException();
		}
		UserAccount userAccount = userRepository.findById(id).orElse(null);
		if (userAccount == null) {
			return null;
		}
		Set<String> roles = userAccount.getRoles();
		roles.remove(role);
		userRepository.save(userAccount);
		return roles;
	}

	@Override
	public void changePassword(String password, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokens(auth);
		UserAccount userAccount = userRepository.findById(credentials.getLogin()).get();
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
		userRepository.save(userAccount);
	}
}