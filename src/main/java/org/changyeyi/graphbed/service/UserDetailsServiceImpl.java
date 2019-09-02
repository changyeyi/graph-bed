package org.changyeyi.graphbed.service;

import org.changyeyi.graphbed.model.SysPermission;
import org.changyeyi.graphbed.model.SysRole;
import org.changyeyi.graphbed.model.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author :    zj.wu
 * @date :      2019/08/29 15:41
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!StringUtils.isEmpty(username)&&"changyeyi".equals(username)){
            SysUser sysUser = new SysUser();
            sysUser.setUsername("admin");
            sysUser.setPassword("123456");
            SysRole sysRole = new SysRole();
            sysRole.setId(1L);
            sysRole.setName("admin");
            SysPermission sysPermission = new SysPermission();
            sysPermission.setId(1L);
            sysPermission.setName("any");
            sysPermission.setUrl("/");
            sysPermission.setDescription("can do anything");
            sysRole.setPermissions(List.of(sysPermission));
            sysUser.setRoles(List.of(sysRole));
            return sysUser;
        }
        return null;
    }
}
