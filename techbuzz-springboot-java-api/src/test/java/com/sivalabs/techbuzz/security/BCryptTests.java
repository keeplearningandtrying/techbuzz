package com.sivalabs.techbuzz.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

public class BCryptTests {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<String> strings = Arrays.asList("siva","admin");
        strings.forEach( it -> {
            System.out.println("Plain:"+ it+", encoded:"+encoder.encode(it));
        });
    }
}
