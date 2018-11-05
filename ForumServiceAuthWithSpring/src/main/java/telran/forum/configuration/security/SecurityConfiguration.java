package telran.forum.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//docs.spring.io = doki po springu - Nad endpointami stavim anotaciyu
//prePostEnable i s pomoshyu nee reguliruem dostup k etim endpointam
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/account/register");//secyurity ignoriruet etot endpoint
	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();//nasha applikaciya zashishena basic - dekodiruet imya i parol iz Headers Authorization
		//, otpravlaet imya v UserDetailervice, sravnil parol iz bazi s parolem iz zagolovka, sravnil,propustil,ne propustil
		http.csrf().disable();//mejsaytovaya poddelka
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//zaprosa i ne podderjka keshirovaniya
		
		http.authorizeRequests().antMatchers("/forum/post/*/like","/forum/post/*comment",
				"/forum/posts/tags","/forum/posts/period",
				"/forum/posts/author/*").hasRole("USER");
		//anyRequest().authenticated();//vse zaprosi doljni bit autontificirovani - parol podhodit pod login
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/forum/post").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/forum/post/*").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/forum/post/*").hasAnyRole("USER","MODERATOR");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/forum/post").hasAnyRole("USER","ADMIN");
	}
	

}
