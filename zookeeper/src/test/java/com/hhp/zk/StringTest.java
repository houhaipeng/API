package com.hhp.zk;

import org.junit.Test;

public class StringTest {

    @Test
    public void test() {
        String s1 = "a" + "b" + "c";
        String s2 = "abc";

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }

    @Test
    public void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4);
        System.out.println(s3 == s5);
        System.out.println(s3 == s6);
        System.out.println(s3 == s7);
        System.out.println(s5 == s6);
        System.out.println(s5 == s7);
        System.out.println(s6 == s7);

        String s8 = s6.intern();
        System.out.println(s3 == s8);
    }

    @Test
    public void test3() {
        String s1 = "abc";
        String s2 = "abc";
        s1 = "hello";
        System.out.println(s1 == s2);
    }

    @Test
    public void test4() {
        int x = 1;
        System.out.println("调用add方法前, x=" + x);
        add(x);
        System.out.println("调用add方法后, x=" + x);
    }

    private void add(int x) {
        x = x++;
    }
}
