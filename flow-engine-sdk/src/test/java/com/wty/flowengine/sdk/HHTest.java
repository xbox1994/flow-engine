package com.wty.flowengine.sdk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HHTest.class)
public class HHTest {

    @Value("${testVar}")
    String testVar;

    @Test
    public void testProperty() {
        System.out.println(testVar);
    }
}
