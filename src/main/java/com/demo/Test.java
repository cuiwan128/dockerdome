package com.demo;

import com.googlecode.aviator.AviatorEvaluator;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;

import java.math.BigDecimal;

/**
 * git 测 试
 */
public class Test {


    //分支合并 测试
    public static void main(String[] args) {
        Object result = (Long)AviatorEvaluator.execute("(1+2)*2+3/2");
        System.out.println(result);

        FelEngine fel = new FelEngineImpl();
        String result1 = String.valueOf(fel.eval("(1+2)*2+2/3*10"));
        String result2 = String.valueOf(fel.eval("1.0021+1.1021"));

        System.out.println(result1);
        System.out.println(result2);
    }


}
