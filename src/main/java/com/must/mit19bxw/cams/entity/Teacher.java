package com.must.mit19bxw.cams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * @Description Teacher
 * @Author xiong
 * @Date 2020/02/27 11:42
 * @Version 1.0
 */
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", length = 30)
    private String number;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "name", length = 30)
    private String name;

    @Lob
    @Column(name = "email", columnDefinition = "text")
    private String email;

    @JsonBackReference
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH)
    private Set<Course> courses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
