package com.warehouse.test;


import com.warehouse.commons.UrlDispatcher;
import com.warehouse.service.IDeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MainTest {
    @Autowired
    IDeptService ids;
    @Test
    public void Test1(){
        System.out.println(ids.getDeptLevel(18));
    }
    @Test public void Test2(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("000000","$2a$10$qPIzbwO94GmxLoywnKH7CucUN.1p/J82wo61/5gJCkNo2LEfGKL22"));
    }
}
