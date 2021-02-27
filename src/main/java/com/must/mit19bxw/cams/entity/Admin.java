package com.must.mit19bxw.cams.entity;

import javax.persistence.*;

/**
 * @Description Admin
 * @Author xiong
 * @Date 2020/02/27 11:41
 * @Version 1.0
 */
@Entity
@Table(name="admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account", length = 30)
    private String account;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "name", length = 30)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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


}
