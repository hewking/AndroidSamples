package com.hewking.develop.annotation;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date: 2021/2/20 10:24
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box,  and it is prohibited to leak or used for other commercial purposes.
 */
public class RoutePageTest {

    @Test
    public void test(){
        Route annotation = MainPage.class.getAnnotation(Route.class);
        String[] routes = annotation.value();
        System.out.println(Arrays.asList(routes));
    }

    @Route("main")
    static class MainPage{

    }

}
