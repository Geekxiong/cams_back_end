package com.must.mit19bxw.cams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Description Course
 * @Author xiong
 * @Date 2020/02/27 12:03
 * @Version 1.0
 */

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition="text")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @JsonBackReference
    @ManyToMany(mappedBy="courses")
    private Set<Student> students;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.REFRESH)
    private Set<Courseware> coursewares;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.REFRESH)
    private Set<Assignment> assignments;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.REFRESH)
    private Set<Announcement> announcements;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Courseware> getCoursewares() {
        return coursewares;
    }

    public void setCoursewares(Set<Courseware> coursewares) {
        this.coursewares = coursewares;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
}
