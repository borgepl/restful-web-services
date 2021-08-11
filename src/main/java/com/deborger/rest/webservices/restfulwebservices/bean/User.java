package com.deborger.rest.webservices.restfulwebservices.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Table(name = "User")
@ApiModel(description = "User for the social media app")
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(message = "Username must at least have 2 chars",min=2)
    @ApiModelProperty(notes = "Username must at least have 2 chars")
    private String name;

    @Past
    @ApiModelProperty(notes = "BirthDate should be in the past!")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
