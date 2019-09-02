package org.changyeyi.graphbed.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author :    zj.wu
 * @date :      2019/09/02 11:20
 */
@RestController
public class UserController {
    @GetMapping("/user")
    public Principal user(Principal principal){
        System.out.println(principal);
        return principal;
    }
}
