package com.exam.carapp.user;

import com.exam.carapp.user.models.User;
import com.exam.carapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping(value = "/user")
    public ResponseEntity<?> create(@RequestBody User user) {
        boolean isCreated = userService.create(user);
        return isCreated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAll() {
        List<User> list = userService.readAll();

        return list == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User user = userService.readBy(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
        System.out.println("Request");
        final boolean updated = userService.update(user, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User existingUser = userService.login(user.getUsername(), user.getPassword());
        return existingUser == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity(existingUser, HttpStatus.OK);
    }
}
