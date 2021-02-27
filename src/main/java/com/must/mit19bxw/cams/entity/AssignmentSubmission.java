package com.must.mit19bxw.cams.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description AssignmentSubmission
 * @Author xiong
 * @Date 2020/02/27 13:40
 * @Version 1.0
 */
@Entity
@Table(name = "assignment_submission")
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    private Student student;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "submit_time")
    private Date submitTime;

    @Lob
    @Column(name = "save_path", columnDefinition = "text")
    private String savePath;

    @Column(name = "score")
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
