package cn.com.example.customermanagement.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * Created by fangzy on 2018/1/24 9:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/findAll")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void findAllFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/findAlls")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void addUser() throws Exception {
        Date date = new Date(LocalDateTime.now().plusYears(-1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(date.getTime());
        String content = "{\"age\":19,\"name\":\"tom03\",\"password\":\"123456\",\"birthday\":"+date.getTime()+"}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.post("/addUsers").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tom03"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }
    @Test
    public void getUserByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getUser/tom").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tom"));
    }
    @Test
    public void deleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUser/15").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void updateUserById() throws Exception {
        Date date = new Date(LocalDateTime.now().plusYears(-1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        String content = "{\"id\":13,\"age\":50,\"name\":\"lisa1\",\"password\":\"1234561\",\"birthday\":"+date.getTime()+"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/updateUserById")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lisa1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}