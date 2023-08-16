package ma.odc.fablabback.services;

import ma.odc.fablabback.Exceptions.UserNotFoundException;
import ma.odc.fablabback.entities.Users.User;
import ma.odc.fablabback.repositories.Users.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceV1 {
    private UserRepository userRepository;

    public UserServiceV1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticateUser(long id){
        User user = new User();
        try{
            user = userRepository.findById(id).orElse(null);
            System.out.println(user);
            if (user == null){
                throw new UserNotFoundException();
            }
        }
        catch (UserNotFoundException e){
            return e.toString();
        }
        return user.toString();
    }
}
