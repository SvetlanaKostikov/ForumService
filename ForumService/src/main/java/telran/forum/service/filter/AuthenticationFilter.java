package telran.forum.service.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.UserAccountRepository;
import telran.forum.model.UserAccount;

@Service
@Order(1)
public class AuthenticationFilter implements Filter {
	@Autowired
	AccountConfiguration userConfiguration;
	
	@Autowired
	UserAccountRepository accountRepository;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		if(request.getServletPath().startsWith("/forum")) {
		String auth = request.getHeader("Autorization");
		AccountUserCredential userCredential = userConfiguration.tokens(auth);
		UserAccount userAccount = accountRepository.findById(userCredential.getLogin()).orElse(null);
		if(userAccount==null) {
			response.sendError(401,"Unauthorized");
		}else {
			if (!BCrypt.checkpw(userCredential.getPassword(), userAccount.getPassword())){
				response.sendError(403, "Forbidden");
			}
		}
		}
		arg2.doFilter(request,response);
		
		

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
