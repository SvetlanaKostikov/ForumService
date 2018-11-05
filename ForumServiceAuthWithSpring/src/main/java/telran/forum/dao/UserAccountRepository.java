package telran.forum.dao;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.forum.domain.UserAccount;

public interface UserAccountRepository extends MongoRepository <UserAccount,String>{

}
