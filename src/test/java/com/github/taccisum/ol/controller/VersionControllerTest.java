package com.github.taccisum.ol.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/24
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class VersionControllerTest {
    @Autowired
    private MockMvc mvc;
    @Value("${app.version}")
    private String version;

    @Test
    public void versions() throws Exception {
        mvc.perform(get("/versions")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(version));
    }
}