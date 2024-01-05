package org.choongang.file;

import org.choongang.file.service.FileInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThumbnailTest {

    @Autowired
    private FileInfoService infoService;

    @Test
    void getThumbTest(){
//        String[] data = infoService.getThumb(502L, 150, 150);
//        System.out.println(Arrays.toStrig(data));
    }

}
