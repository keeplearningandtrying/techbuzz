package com.sivalabs.techbuzz.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.techbuzz.entities.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LinkControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void getLinksWithAnonymousUser() throws Exception {
        this.mvc.perform(get("/api/links"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void createLinkSuccessfullyWithUserRole() throws Exception {
        Link link = new Link();
        link.setTitle("sampel title");
        link.setUrl("http://dummy.url");
        ObjectMapper mapper = new ObjectMapper();
        String linkJson = mapper.writeValueAsString(link);
        this.mvc.perform(post("/api/links")
                    .content(linkJson)
                    .contentType(APPLICATION_JSON)
                    )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    public void createLinkFailWithAnonymousUser() throws Exception {
        Link link = new Link();
        link.setTitle("sampel title");
        link.setUrl("http://dummy.url");
        ObjectMapper mapper = new ObjectMapper();
        String linkJson = mapper.writeValueAsString(link);
        this.mvc.perform(post("/api/links")
                .content(linkJson)
                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }
}
