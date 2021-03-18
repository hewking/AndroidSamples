package com.hewking.develop.stream;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date: 1/31/21 1:58 PM
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box,  and it is prohibited to leak or used for other commercial purposes.
 */
public class StreamTest {

    /**
     * stream 转换为iterable
     * @param stream
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> iterableOf(Stream<T> stream){
        return stream::iterator;
    }

    /**
     * 将迭代器转换为Stream
     * @param it
     * @param <T>
     * @return
     */
    public static <T> Stream<T> streamOf(Iterable<T> it){
        return StreamSupport.stream(it.spliterator(),false);
    }

}
