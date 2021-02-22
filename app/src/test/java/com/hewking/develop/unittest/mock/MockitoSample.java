package com.hewking.develop.unittest.mock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @Description: Mockito 例子
 * @Author: jianhao
 * @Date: 2021/2/22 17:40
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box,  and it is prohibited to leak or used for other commercial purposes.
 */
public class MockitoSample {

    @Test
    public void testMock(){
        List mock = mock(List.class);

        mock.add("one");
        mock.clear();

        verify(mock).add("one");
        verify(mock).clear();;

    }

    @Test
    public void testMock2(){
        List list = mock(LinkedList.class);

        when(list.get(0)).thenReturn("first");
        when(list.get(1)).thenThrow(new IllegalArgumentException("不能做这个"));

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(99));

        verify(list).get(0);
    }

    @Test
    public void testMock3(){
        // 参数匹配
        List list = mock(List.class);
        when(list.get(anyInt())).thenReturn("item");

        System.out.println(list.get(100));

        verify(list).get(anyInt());
    }

    @Test
    public void testMock4(){
        // 验证方法调用次数
        // times(n)：方法被调用n次
        //never()：没有被调用
        //atLeast(n)：至少被调用n次
        //atLeastOnce()：至少被调用1次，相当于atLeast(1)
        //atMost()：最多被调用n次
        List mock = mock(List.class);

        mock.add(1);
        mock.add(2);
        mock.add(2);
        mock.add(3);
        mock.add(3);

        verify(mock,times(2)).add(2);
        verify(mock,never()).add(4);
        verify(mock,atLeast(1)).add(1);
        verify(mock,atMost(2)).add(3);
        verify(mock,atLeastOnce()).add(1);

//        verify(mock,atLeast(3)).add(1);

    }

    @Test
    public void testMock5(){
        List list1 = mock(List.class);
        List list2 = mock(List.class);

        list1.add(1);
        list1.add(2);

        list2.add(1);
        list2.add(1);

        InOrder order = inOrder(list1, list2);
        order.verify(list1).add(1);
        order.verify(list2,times(2)).add(1);


    }

    @Test
    public void testMock6(){
        // verifyZeroInteractions && verifyNoMoreInteractions
        List list = mock(List.class);
        verifyZeroInteractions(list);

        List mock = mock(List.class);

        mock.add(1);
        mock.add(2);

        verify(mock).add(1);

        // 验证是否所有的方法调用都被验证了, 这里不会通过一位内add(2)没有被验证
        verifyNoMoreInteractions(mock);


    }

    @Test
    public void testMock7(){
        // doThrow(Throwable...)：进行异常测试
        List list = mock(List.class);
        doThrow(new RuntimeException()).when(list).clear();

        list.clear();
    }

    @Test
    public void testMock8(){
        // doReturn() 指定返回值
        List list = mock(List.class);
        doReturn("123").when(list).get(anyInt());

        System.out.println(list.get(2));

        // doNothing() 指定void 方法什么都不做
    }

    static class Foo {

        void mock(){
            System.out.println("mock method");
        }

        public int getCount(){
            return 1;
        }

    }

    @Test
    public void testMock9(){
        Foo foo = mock(Foo.class);

        foo.mock();
        doNothing().when(foo).mock();

        foo.mock();

        doCallRealMethod().when(foo).mock();

        foo.mock();

        System.out.println(foo.getCount());
        doCallRealMethod().when(foo).getCount();
        System.out.println(foo.getCount());


    }

    @Test
    public void testMock10(){
        // 使用spy 方法监控真实对象，有些对象需要调用真实业务，有些方法需要Mock，可以使用spy() 做到。
        List list = new ArrayList();
        List spy = Mockito.spy(list);

        // subbing 方法，当调用size() 使用该方法的返回值
        when(spy.size()).thenReturn(10);

        spy.add(1);
        spy.add(2);

        System.out.println(spy.get(1));
        System.out.println(spy.size());

        verify(spy).add(1);

    }

    @Test
    public void testMock11(){
        // 参数捕捉
        List list = mock(List.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        list.add("1");
        verify(list).add(captor.capture());
        Assert.assertEquals("1",captor.getValue());
    }

    @Test
    public void testMock12(){
        // 重置Mock
        List list = mock(List.class);
        when(list.size()).thenReturn(10);

        Assert.assertEquals(10, list.size());

        // 重置Mock，之前的交互和stub 全部都失效
        reset(list);

        Assert.assertEquals(0, list.size());

    }

}
