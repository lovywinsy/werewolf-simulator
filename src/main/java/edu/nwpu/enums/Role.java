package edu.nwpu.enums;

import com.google.common.collect.Lists;

import java.util.List;

public enum Role {
    VILLAGER,
    WOLF,
    PROPHET,
    WITCH,
    HUNTER,
    IDIOT;

    private static final List<Role> godRoleList;

    static {
        godRoleList = Lists.newArrayList(PROPHET, WITCH, HUNTER, IDIOT);
    }

    public static boolean isGod(Role role) {
        return godRoleList.contains(role);
    }
}
