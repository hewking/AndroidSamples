package com.hewking.develop.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JavaGenericTest {

    static class A {
        void hello(){
            System.out.println("hello");
        }
    }

    static class B extends A {
        @Override
        void hello() {
            System.out.println("hi");
        }
    }

    class C<T extends A> {

    }

    @Test
    public void foo(){
        List<B> b = new ArrayList<>();
        b.add(new B());
        baz(b);

        bar(new ArrayList<Object>());

        List<? extends A> a = new ArrayList<B>();

        List<? super A> a2 = new ArrayList<Object>();

        // ? 相当于 ? extends Object
        List<?> a3 = a;

        C c = new C<B>();
    }

    /**
     * 测试泛型协变
     */
    void baz(List<? extends A> a){
        for (A t : a) {
            t.hello();
        }
    }

    /**
     * 测试泛型逆变
     */
    void bar(List<? super A> a2){
        a2.add(new A());
    }

}
