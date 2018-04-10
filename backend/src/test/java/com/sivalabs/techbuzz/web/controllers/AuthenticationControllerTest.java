package com.sivalabs.techbuzz.web.controllers;

import com.sivalabs.techbuzz.common.TimeProvider;
import com.sivalabs.techbuzz.entities.Authority;
import com.sivalabs.techbuzz.entities.User;
import com.sivalabs.techbuzz.security.TokenHelper;
import com.sivalabs.techbuzz.service.CustomUserDetailsService;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TimeProvider timeProviderMock;

    private static final String TEST_USERNAME = "testUser";

    @Autowired
    private TokenHelper tokenHelper;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Before
    public void setup() {


        User user = new User();
        user.setUsername("username");
        Authority authority = new Authority();
        authority.setId(0L);
        authority.setName("ROLE_USER");
        List<Authority> authorities = Arrays.asList(authority);
        user.setAuthorities(authorities);
        user.setLastPasswordResetDate(new Timestamp(DateUtil.yesterday().getTime()));
        when(this.userDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(user);
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 100); // 100 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "queenvictoria");

    }

    @Test
    public void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer 123"))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldRefreshNotExpiredToken() throws Exception {

        given(timeProviderMock.now())
                .willReturn(new Date(30L));

        String token = createToken();
        String refreshedToken = tokenHelper.refreshToken(token);

        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:100}"));
    }

    @Test
    public void shouldNotRefreshExpiredToken() throws Exception {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
        when(timeProviderMock.now())
                .thenReturn(beforeSomeTime);
        String token = createToken();
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    private String createToken() {
        return tokenHelper.generateToken(TEST_USERNAME);
    }
}
