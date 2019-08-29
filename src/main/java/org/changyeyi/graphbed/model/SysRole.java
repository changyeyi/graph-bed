package org.changyeyi.graphbed.model;

import lombok.Data;

import java.util.List;

/**
 * @author :    zj.wu
 * @date :      2019/08/29 14:53
 */
@Data
public class SysRole {
    private Long id;
    private String name;
    private List<SysPermission> permissions;
}
