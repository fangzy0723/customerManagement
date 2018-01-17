package cn.com.example.customermanagement.domain;


import java.io.Serializable;

/**
 * Created by fangzy on 2017/9/2 15:52
 */
public class User implements Serializable {

    private Long id;

    private Integer age;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
