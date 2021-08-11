package com.deborger.rest.webservices.restfulwebservices.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.deborger.rest.webservices.restfulwebservices.bean.User;
import com.deborger.rest.webservices.restfulwebservices.service.UserDAOService;
import com.deborger.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDAOService userService;

    // GET /users
    //retrieveAllUsers
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    // GET /users/{id}
    //retrieveUser(int id)
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userService.findOne(id);
        if (user==null) {
            throw new UserNotFoundException("User id-"+ id + " is not found");
        }
        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        //HATEOAS
        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping ("/users/{id}")
    public User deleteUser(@PathVariable int id) {
        User user = userService.deleteById(id);
        if (user==null) {
            throw new UserNotFoundException("User id-"+ id + " is not found");
        }
        return user;
    }

}
