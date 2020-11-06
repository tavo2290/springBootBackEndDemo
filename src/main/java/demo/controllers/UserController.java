package demo.controllers;

import demo.persistence.models.User;
import demo.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/searchUser")
    public ResponseEntity<Object> searchUser(@RequestParam MultiValueMap<String,String> params){
        String userName = params.getFirst("userName");
        String profile = params.getFirst("profile");
        List<User> users = userRepository.findAllByUserNameAndProfile(userName,profile);
        System.out.println(users);
        return ResponseEntity.ok(users);
    }
}
