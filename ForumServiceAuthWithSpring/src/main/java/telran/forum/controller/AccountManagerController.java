package telran.forum.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountManagerController {
	@Autowired
	AccountService accountService;
	
	@PostMapping("/register")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto
			){
		return accountService.addUser(userRegisterDto);
	}
	@PutMapping
	@PreAuthorize("#UserProfileDto.getId==authentication.name")
	public UserProfileDto edit(@RequestBody UserProfileDto userRegisterDto 
			) {
		return accountService.editUser(userRegisterDto);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id==authentication.name or hasAnyRole('ADMIN')")//reshetka ozna4aet 4to vozmi "id" iz "id" v PathVariable
	public UserProfileDto removeUser(@PathVariable String id) {
		return accountService.removeUser(id);
	}
	@PutMapping("/{id}/{role}")
	@PreAuthorize("hasRole('ADMIN')")
	public Set<String> addRole(@PathVariable String id,@PathVariable String role) {
		return accountService.addRole(id, role);
	}
	@DeleteMapping("/{id}/{role}")
	@PreAuthorize("hasRole('ADMIN')")
	public Set<String> removeRole(@PathVariable String id, @PathVariable String role) {
		return accountService.removeRole(id, role);
	}
	@PutMapping("/password")
	//@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'EXPDATE')")
	public void changePassword(@RequestHeader(value = "X-Password") String password, Principal principal) {
		accountService.changePassword(password, principal.getName());
	}



}
