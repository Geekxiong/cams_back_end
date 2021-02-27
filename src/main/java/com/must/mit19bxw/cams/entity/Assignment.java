package com.must.mit19bxw.cams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Description Assignment
 * @Author xiong
 * @Date 2020/02/27 12:04
 * @Version 1.0
 */

@Entity
@Table(name = "assignment")
public class Assignment {
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
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "index_")
    private Integer index;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;


    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REFRESH)
    private Set<AssignmentAnnex> assignmentAnnexes;

    @JsonBackReference
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REFRESH)
    private Set<AssignmentSubmission> assignmentSubmissions;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public Set<AssignmentAnnex> getAssignmentAnnexes() {
        return assignmentAnnexes;
    }

    public void setAssignmentAnnexes(Set<AssignmentAnnex> assignmentAnnexes) {
        this.assignmentAnnexes = assignmentAnnexes;
    }
}
