package org.example;

import org.springframework.core.codec.StringDecoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {
    private static String hello;
    private static String hi;
    private static String jndi;

    public static void main(String[] args) {
        hello1();
        hello();
        String hi = "hi";
        jndi = "jndi";
    }

    private static void hello1() {
        System.out.println("Hello world!");
    }

    private static void hello() {
        try {
            InputStream i = new FileInputStream("/home/bassure/");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

