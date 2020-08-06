package com.redis.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserEntity implements Serializable {
    private Long id;
    private String guid;
    private String name;

    public UserEntity(Long id, String guid, String name, String age, Date createTime) {
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.age = age;
        this.createTime = createTime;
    }

    private String age;
    private Date createTime;

    public UserEntity() {
    }
}
