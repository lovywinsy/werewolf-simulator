package edu.nwpu.enums;

import edu.nwpu.util.Utils;

import java.util.Arrays;

public enum ElectStrategy {
    PARTICIPATE,
    ABSTAIN;

    public static ElectStrategy getRandomElectStrategy() {
        return Utils.getRandomOne(Arrays.asList(ElectStrategy.values()));
    }
}
