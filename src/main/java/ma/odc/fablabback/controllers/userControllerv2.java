package ma.odc.fablabback.controllers;

import ma.odc.fablabback.services.UserServiceV1;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/V2")
public class userControllerv2 {
    private UserServiceV1 userServiceV1;

    public userControllerv2(UserServiceV1 userServiceV1) {
        this.userServiceV1 = userServiceV1;
    }

    @PostMapping("/log/{id}")
    public String hello(@PathVariable long id){
        return userServiceV1.authenticateUser(id);
    }
}
