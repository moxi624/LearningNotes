package com.moxi.interview.study.thread;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: 陌溪
 * @create: 2020-03-14-18:26
 */
@NoArgsConstructor // 空参构造方法
@Getter
@Setter
public class Person {
    private Integer id;
    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }
}
