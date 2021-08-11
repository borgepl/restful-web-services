package com.deborger.rest.webservices.restfulwebservices.repository;

import com.deborger.rest.webservices.restfulwebservices.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}