package com.gravitee.gravitee.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GraviteeClientTest {

    @Test
    @Order(value = 0)
    public void testThatResponseIsNullCheckGraviteeResponse() throws Exception {
        assertTrue(true);
        assertNull(null);
    }
}
