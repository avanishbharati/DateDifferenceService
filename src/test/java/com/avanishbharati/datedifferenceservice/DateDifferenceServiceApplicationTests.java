package com.avanishbharati.datedifferenceservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateDifferenceServiceApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateDifferenceServiceApplicationTests.class);

	@Test
	public void contextLoads() {
	    LOGGER.info("DateDifferenceServiceApplicationTests::contextLoads()");
	}

}
