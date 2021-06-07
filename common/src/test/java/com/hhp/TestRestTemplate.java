package com.hhp;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.hhp.entity.Project;
import com.hhp.entity.ProjectDto;
import com.hhp.util.Result;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "http://localhost:8080/auto/manager/project/queryProject";
        ProjectDto projectDto = new ProjectDto(null, "太平洋", null, null, null);
        HttpEntity<ProjectDto> projectDtoHttpEntity = new HttpEntity<>(projectDto);
        ResponseEntity<Result> exchange = restTemplate.exchange(url, HttpMethod.POST, projectDtoHttpEntity, Result.class);
        Result body = exchange.getBody();
        List<Project> data = (List<Project>) body.getData();
        System.out.println(data);
    }
}
