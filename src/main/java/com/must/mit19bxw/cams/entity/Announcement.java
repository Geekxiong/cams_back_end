package com.must.mit19bxw.cams.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description Announcement
 * @Author xiong
 * @Date 2020/03/02 16:54
 * @Version 1.0
 */
@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "title", length = 30)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition="text")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date editTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}
