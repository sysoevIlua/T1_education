package org.sysoev;

import org.sysoev.service.TestRunner;
import org.sysoev.service.TestService;

public class Main {
    public static void main(String[] args) {
        TestRunner.runTests(TestService.class);
    }
}