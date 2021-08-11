package com.deborger.rest.webservices.restfulwebservices.controllers;

import com.deborger.rest.webservices.restfulwebservices.bean.Post;
import com.deborger.rest.webservices.restfulwebservices.bean.User;
import com.deborger.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.deborger.rest.webservices.restfulwebservices.repository.PostRepository;
import com.deborger.rest.webservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // GET /users
    //retrieveAllUsers
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // GET /users/{id}
    //retrieveUser(int id)
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User id-"+ id + " is not found");
        }
        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        EntityModel<User> resource = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        //HATEOAS
        return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping ("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // POSTS

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUserPosts(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User id-"+ id + " is not found");
        }
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createUserPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User id-"+ id + " is not found");
        }
        User retrievedUser = user.get();
        post.setUser(retrievedUser);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
