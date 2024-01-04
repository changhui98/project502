package org.choongang.admin.config;

import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.admin.config.service.ConfigSaveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
@Transactional
public class ConfigSaveTest {

    @Autowired
    private ConfigSaveService saveService;

    @Autowired
    private ConfigInfoService configInfoService;

    @Test
    @DisplayName("BasicConfig로 생성된 객체가 JSON으로 저장되는지 테스트 ")
    void saveTest(){
        BasicConfig config = new BasicConfig();
        config.setSiteTitle("사이트 제목");
        config.setSiteDescription("사이트 설명");
        config.setSiteKeywords("사이트 키워드");
        config.setCssJsVersion(1);
        config.setJoinTerms("회원가입 약관");

        saveService.save("basic", config);

    }

}

