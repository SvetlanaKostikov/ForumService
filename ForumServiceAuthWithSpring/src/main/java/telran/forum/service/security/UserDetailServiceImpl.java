package telran.forum.service.security;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.forum.dao.UserAccountRepository;
import telran.forum.domain.UserAccount;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	UserAccountRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		boolean enabled = false;//vklyuchen ili otklyuchen polzovatel
        boolean accountNonExpired = false;//ukazivaet istek li srok deystviya u4etnoy zapisi
        boolean credentialsNonExpired = false;//istek li u polzovatelya parol
        boolean accountNonLocked = false;//zablokirovan ili razblokirovan polzovatel
		UserAccount userAccount= repository.findById(arg0).orElse(null);
		if(userAccount== null) {
			throw new UsernameNotFoundException(arg0);
		}
		if(userAccount.getExpDate().isBefore(LocalDateTime.now())) {
		credentialsNonExpired = true;
		}
		String password = userAccount.getPassword();
		Set <String> roles = userAccount.getRoles();
//		if (userAccount.getExpDate().isBefore(LocalDateTime.now())) {
//			roles.clear();
//			roles.add("ROLE_EXPDATE");
//		}
		String[] ArrayRoles = setToArray(roles);


		return new User(arg0, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				AuthorityUtils.createAuthorityList(ArrayRoles));
	}
	

	private String[] setToArray(Set<String> roles) {
		//return roles.stream().toArray(String[]::new);
		return roles.toArray(new String[0]);
	}

}