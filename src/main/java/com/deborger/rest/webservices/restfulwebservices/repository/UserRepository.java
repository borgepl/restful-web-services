package com.deborger.rest.webservices.restfulwebservices.repository;

import com.deborger.rest.webservices.restfulwebservices.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}