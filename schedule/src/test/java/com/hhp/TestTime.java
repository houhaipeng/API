package com.hhp;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import lombok.Data;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTime {

    @Test
    public void test() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        Date date1 = calendar1.getTime();
        System.out.println(format.format(date1));
    }

    @Test
    public void test2() {
        LocalDateTime now = LocalDateTime.now();
        String nowFormat = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(nowFormat);
        LocalDateTime plus = now.plus(1, ChronoUnit.MONTHS);
        String plusFormat = plus.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(plusFormat);
    }

    @Test
    public void test3() {
        DateDto dateDto = new DateDto();
        dateDto.setNum(1);
        dateDto.setUnit(ChronoUnit.SECONDS);
        String s = JSON.toJSONString(dateDto);
        System.out.println(s);
        DateDto dateDto1 = JSON.parseObject(s, DateDto.class);
        System.out.println(dateDto1);
    }

    @Test
    public void test4() {
        Duration between = Duration.between(LocalTime.now(), LocalTime.now().plus(5, ChronoUnit.MINUTES));
//        long seconds = between.getSeconds();
//        String format = LocalTime.ofSecondOfDay(seconds).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//        System.out.println(format);
//        LocalTime parse = LocalTime.parse(format, DateTimeFormatter.ofPattern("HH:mm:ss"));
//        System.out.println(parse);
//        LocalDateTime parse1 = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("HH:mm:ss"));
//        System.out.println(seconds);
        String s = between.toString();
        System.out.println(s);
    }

    @Test
    public void test5() {
        LocalDateTime parse = LocalDateTime.parse("00:01:00");
        System.out.println(parse);
//        Duration duration = Duration.parse();
//        long seconds = duration.getSeconds();
//        System.out.println(seconds);
        String timeStr = "01:03:22";
        String[] time = timeStr.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        long seconds = hour*60*60 + minute*60 + second;


    }
}

@Data
class DateDto {
    private Integer num;
    private TemporalUnit unit;
}
