package com.atguigu.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Payment)实体类
 *
 * @author makejava
 * @since 2020-03-06 20:05:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    
    private Long id;
    
    private String serial;


}