package com.fuqqqq.common.area.cn;

import com.fuqqqq.common.area.cn.data.mapper.base.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(markerInterface = BaseMapper.class)
@SpringBootApplication
public class AreaCnApplication {
    public static void main(String[] args) {
        SpringApplication.run(AreaCnApplication.class, args);
    }
}
