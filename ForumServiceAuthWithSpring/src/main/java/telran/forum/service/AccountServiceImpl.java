package telran.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.dao.UserAccountRepository;
import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.domain.UserAccount;
import telran.forum.dto.UserExistExeption;
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	UserAccountRepository userRepository;
	@Autowired
	AccountConfiguration accountConfiguration;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public UserProfileDto addUser(UserRegisterDto userRegDto) {
		if(userRepository.existsById(userRegDto.getId())) {
			throw new UserExistExeption();
		}
		String hashPassword = encoder.encode(userRegDto.getPassword());
		UserAccount userAccount = UserAccount.builder()
				.id(userRegDto.getId())
				.password(hashPassword)
				.firstName(userRegDto.getFirstName())
				.lastName(userRegDto.getLastName())
				.role("ROLE_USER")
				.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
				.build();
		userRepository.save(userAccount);
		return new UserProfileDto(userRegDto.getId(),userRegDto.getFirstName(),userRegDto.getLastName());
	}


	@Override
	public UserProfileDto editUser(UserProfileDto userRegDto) {
		UserAccount userAccount = userRepository.findById(userRegDto.getId()).get();
		userAccount.setFirstName(userRegDto.getFirstName());
		userAccount.setLastName(userRegDto.getLastName());
		userRepository.save(userAccount);
		return new UserProfileDto(userRegDto.getId(), userRegDto.getFirstName(), userRegDto.getLastName());
	}

	@Override
	public UserProfileDto removeUser(String id) {
		UserAccount userAccount = userRepository.findById(id).get();
		userRepository.delete(userAccount);
		return new UserProfileDto(userAccount.getId(), userAccount.getFirstName(), userAccount.getLastName());
	}

	@Override
	public Set<String> addRole(String id, String role) {
		UserAccount userAccount = userRepository.findById(id).orElse(null);
		if (userAccount == null) {
			return null;
		}
		Set<String> roles = userAccount.getRoles();
		roles.add("ROLE_"+role.toUpperCase());
		userRepository.save(userAccount);
		return roles;
		
	}

	@Override
	public Set<String> removeRole(String id, String role) {
		UserAccount userAccount = userRepository.findById(id).orElse(null);
		if (userAccount == null) {
			return null;
		}
		Set<String> roles = userAccount.getRoles();
		roles.remove("ROLE_"+role.toUpperCase());
		userRepository.save(userAccount);
		return roles;
	}
	@Override
	public void changePassword(String password, String id) {
		UserAccount userAccount = userRepository.findById(id).get();
		String hashPassword = encoder.encode(password);
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now()
				.plusDays(accountConfiguration.getExpPeriod()));
		userRepository.save(userAccount);


	}


	

}
