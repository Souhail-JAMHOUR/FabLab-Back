package ma.odc.fablabback.security;

import lombok.AllArgsConstructor;
import ma.odc.fablabback.entities.Users.AppUser;
import ma.odc.fablabback.services.UserServiceImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private UserServiceImpl userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userService.loadUserbyUsername(username);
        if(appUser==null) throw  new RuntimeException("User does not exists");
        UserDetails userDetails = User.withUsername(appUser.getAppUsersname())
                .password(appUser.getPassword())
                .build();
        return userDetails;
    }
}
