package cn.com.example.customermanagement.domain;


import cn.com.example.customermanagement.valid.CheckUserName;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fangzy on 2017/9/2 15:52
 */
public class User implements Serializable {

    private Long id;
    @Min(value = 18,message = "年龄必须大于18岁")
    @Max(value = 100,message = "年龄不能大于100岁")
    private Integer age;
    @NotBlank(message = "用户名不能为空")
    @CheckUserName(message = "用户名已被使用")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Past(message = "出生日期不能使将来时")
    private Date birthday;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
