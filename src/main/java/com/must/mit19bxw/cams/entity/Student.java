package com.must.mit19bxw.cams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * @Description Student
 * @Author xiong
 * @Date 2020/02/27 11:41
 * @Version 1.0
 */
@Entity
@Table(name = "student")
public class Student {

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

    @Column(name = "class", length = 30)
    private String class_;

    @ManyToMany()
    @JoinTable(name="student_course",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")}
    )
    private Set<Course> courses;

    @JsonBackReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH)
    private Set<AssignmentSubmission> assignmentSubmissions;

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

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<AssignmentSubmission> getAssignmentSubmissions() {
        return assignmentSubmissions;
    }

    public void setAssignmentSubmissions(Set<AssignmentSubmission> assignmentSubmissions) {
        this.assignmentSubmissions = assignmentSubmissions;
    }
}
