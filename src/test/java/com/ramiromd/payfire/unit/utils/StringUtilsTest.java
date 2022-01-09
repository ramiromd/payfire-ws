package com.ramiromd.payfire.unit.utils;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramiromd.payfire.utils.StringUtils;

@SpringBootTest
public class StringUtilsTest {
	
	
    @Test
    public void it_can_sluggify_a_string() {

        assertEquals("hello-world", StringUtils.toSlug("Hello World !!"));
        assertEquals("nandu", StringUtils.toSlug("Ñandú"));
    }
}
