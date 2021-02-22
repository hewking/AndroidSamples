package com.hewking.develop.unittest.mock;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date: 2021/2/22 18:26
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box,  and it is prohibited to leak or used for other commercial purposes.
 */
public class MockitoSample2 {

    public MockitoSample2(){
        // 初始化mock ，使用注解创建Mock 对象
//        MockitoAnnotations.initMocks(this);
    }

    /**
     * 通过runner 的方式来使用注解的方式生成Mock 对象
     */
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    /**
     * 注解的方式创建Mock 对象，有两种方式
     */
    @Mock
    List list;

    @Test
    public void testMock(){
        list.add(1);

        verify(list).add(1);

    }

    /**
     * ##### 6.2.12 更多的注解
     *
     * 使用注解都需要预先进行配置，怎么配置见6.2.7说明
     *
     * *   @Captor 替代ArgumentCaptor
     * *   @Spy 替代spy(Object)
     * *   @Mock 替代mock(Class)
     * *   @InjectMocks 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中
     */

}
