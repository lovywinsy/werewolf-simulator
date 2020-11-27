package edu.nwpu.enums;

import com.google.common.collect.Lists;
import edu.nwpu.exception.ModeNotSupportedException;

import java.util.List;

public enum Mode {
    STANDARD;

    public List<Role> getRoleList() {
        switch (this) {
            case STANDARD:
                return roleListForStandard();
            default:
                throw new ModeNotSupportedException();
        }
    }

    private List<Role> roleListForStandard() {
        return Lists.newArrayList(
                Role.VILLAGER,
                Role.VILLAGER,
                Role.VILLAGER,
                Role.VILLAGER,
                Role.WOLF,
                Role.WOLF,
                Role.WOLF,
                Role.WOLF,
                Role.PROPHET,
                Role.WITCH,
                Role.HUNTER,
                Role.IDIOT
        );
    }
}
