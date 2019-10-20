package com.flysafe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeepLinkServiceTest {
    @Autowired
    private DeepLinkService deepLinkService;

    @Test
    void getDeepLink() {
        //deepLinkService.getDeepLink("Berlin","Madr");
    }
}