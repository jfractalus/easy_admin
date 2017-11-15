package com.volia.eadmin;

import com.volia.eadmin.config.WebConfig;
import com.volia.eadmin.config.security.InMemoryAuthentication;
import com.volia.eadmin.controller.user.MessageController;
import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.core.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  WebConfig.class)
@WebAppConfiguration
public class AuthenticationTest {

    @Autowired
    private MessageController controller;
    private AbstractViewController absMock;

    // Init mocks
    private MockHttpServletRequest requestMock;

    @Autowired
    private SecurityService securityService;

    @Before
    public void init(){
        requestMock = new MockHttpServletRequest();
        requestMock.setServletPath("/client");
        absMock = mock(AbstractViewController.class);
        when(absMock.readAll(requestMock)).thenReturn(new ModelAndView("/content/database-table"));
        authenticateWithRoles(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
    }

    private void authenticateWithRoles(SimpleGrantedAuthority... roles){
        authenticate("in_memory_admin", "in_memory_admin", roles);
    }

    private void authenticate(String login, String password, SimpleGrantedAuthority... roles){
        UserDetails admin = new User(login, password, Arrays.asList(roles));
        InMemoryAuthentication auth = new InMemoryAuthentication(admin);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void test(){
        controller.readAll(requestMock);
    }

    @Test
    public void testMock(){
        ModelAndView modelAndView = absMock.readAll(requestMock);
        assertNotNull(modelAndView);
    }

    @Test
    public void decryptPassword(){
        String encryptPassword = securityService.encryptPassword("volia_pwd");
        Assert.assertEquals("volia_pwd", securityService.decryptPassword(encryptPassword));
    }
}
