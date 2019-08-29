package org.changyeyi.graphbed.service;

import org.changyeyi.graphbed.model.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author :    zj.wu
 * @date :      2019/08/29 15:41
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!StringUtils.isEmpty(username)&&"changyeyi".equals(username)){
            SysUser sysUser = new SysUser();
            sysUser.setUsername("changyeyi");
            sysUser.setPassword("weiaiyiW1");
        }
        return null;
    }
}
