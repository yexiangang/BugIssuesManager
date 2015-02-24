package com.fabrefrederic.webapp.scheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

public class MyBean {
    private static final Logger LOGGER = Logger.getLogger(MyBean.class);

    @Scheduled(fixedRate=1000)
    public void printMessage() {
        System.out.println("I am called by Spring scheduler - system");
        LOGGER.debug("I am called by Spring scheduler");
    }
}
