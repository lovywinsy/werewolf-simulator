package edu.nwpu.common;

import edu.nwpu.enums.ElectStrategy;
import edu.nwpu.enums.Mode;
import edu.nwpu.enums.Role;
import edu.nwpu.enums.Survival;
import edu.nwpu.model.Player;
import edu.nwpu.util.Utils;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@Accessors(chain = true)
public class Execution {
    private List<Player> playerList;

    private int round;

    private boolean dayTime;

    private boolean elected;

    private Player chief;

    private Player silver;

    private Player dying;

    private Player poisoned;

    private Deque<Player> killedDeque;

    private int antidoteNo;

    private int poisonNo;

    private boolean fireStatus;

    private boolean wolfVictory;

    public static Execution initial() {
        List<Role> roleList = Utils.shuffleList(Mode.STANDARD.getRoleList());
        List<Player> playerList = Utils.shuffleList(Utils.getPlayerList());
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            player.setSeq(i);
            player.setSurvival(Survival.ALIVE);
            player.setRole(roleList.get(i));
        }
        return Execution.builder().playerList(playerList)
                .antidoteNo(1)
                .round(1)
                .poisonNo(1)
                .fireStatus(true)
                .dayTime(false)
                .killedDeque(new ArrayDeque<>()).build();
    }

    public void proceed() {
        while (gameNotOver()) {
            if (dayTime) {
                if (!elected) {
                    electChief();
                }
                driveOut();
                round++;
            } else {
                werewolfAction();
                witchAtAction();
                settle();
            }
            dayTime = !dayTime;
        }
        wolfVictory = CollectionUtils.isNotEmpty(getEvilNotDead());
    }

    private boolean gameNotOver() {
        return godStillAlive() && villagerStillAlive() && wolfStillAlive();
    }

    private boolean godStillAlive() {
        return getPlayerList().stream().filter(player -> Role.isGod(player.getRole())).anyMatch(player -> player.getSurvival() != Survival.DEAD);
    }

    private boolean wolfStillAlive() {
        return getEvil().stream().anyMatch(player -> player.getSurvival() != Survival.DEAD);
    }

    private boolean villagerStillAlive() {
        return getPlayerList().stream().filter(player -> player.getRole() == Role.VILLAGER).anyMatch(player -> player.getSurvival() != Survival.DEAD);
    }

    private void werewolfAction() {
        dying = Utils.getRandomOne(Utils.getRandomBooleanWithProbability(antidoteAvailable() ? cheatForAntidote() : 0d) ? getEvilNotDead() : getHumanNotDead()).setSurvival(Survival.UNSETTLED_DEAD);
    }

    private double cheatForAntidote() {
        return 0.05;
    }

    private List<Player> getEvilNotDead() {
        return getEvil().stream().filter(player -> player.getSurvival() != Survival.DEAD).collect(Collectors.toList());
    }

    private List<Player> getEvil() {
        return getPlayerList().stream().filter(player -> player.getRole() == Role.WOLF).collect(Collectors.toList());
    }

    private List<Player> getHumanNotDead() {
        return getHuman().stream().filter(player -> player.getSurvival() != Survival.DEAD).collect(Collectors.toList());
    }

    private List<Player> getHuman() {
        return getPlayerList().stream().filter(player -> player.getRole() != Role.WOLF).collect(Collectors.toList());
    }

    private void witchAtAction() {
        if (Survival.DEAD != getWitch().getSurvival()) {
            if (getWitch() == dying) {
                if (poisonAvailable()) {
                    poisonSomeone();
                }
            } else {
                if (antidoteAvailable()) {
                    saveTheDying();
                } else if (poisonAvailable()) {
                    poisonSomeone();
                } else {
                }
            }
        }
    }

    private boolean antidoteAvailable() {
        return antidoteNo > 0;
    }

    private void saveTheDying() {
        dying.setSurvival(Survival.ALIVE);
        antidoteNo--;
    }

    private boolean poisonAvailable() {
        return poisonNo > 0;
    }

    private void poisonSomeone() {
        poisoned = Utils.getRandomOne(getNotDead().stream().filter(p -> p.getRole() != Role.WITCH).collect(Collectors.toList())).setSurvival(Survival.UNSETTLED_DEAD);
        poisonNo--;
    }

    private List<Player> getNotDead() {
        return Utils.joinLists(getHumanNotDead(), getEvilNotDead());
    }

    private void settle() {
        getNotDead().stream().filter(player -> Survival.UNSETTLED_DEAD == player.getSurvival()).forEach(player -> player.setSurvival(Survival.DEAD));
    }

    private void electChief() {
        chief = getNotDead()
                .stream()
                .map(player -> player.setElectStrategy(ElectStrategy.getRandomElectStrategy()))
                .filter(player -> ElectStrategy.PARTICIPATE == player.getElectStrategy())
                .findAny()
                .orElse(null);
        elected = true;
    }

    private void driveOut() {
        Player outcast;
        if (Utils.getRandomBooleanWithProbability(0.6d)) {
            outcast = Utils.getRandomOne(getEvilNotDead()).setSurvival(Survival.DEAD);
        } else {
            outcast = Utils.getRandomOne(getHuman()).setSurvival(Survival.DEAD);
            if (outcast == getHunter() && fireStatus) {
//                Utils.getRandomOne(getNotDead().stream().filter(p -> p.getRole() != Role.HUNTER).collect(Collectors.toList())).setSurvival(Survival.DEAD);
            }
        }
    }

    private Player getRole(Role role) {
        return getPlayerList().stream().filter(player -> player.getRole() == role).collect(Utils.toSingleton());
    }

    private Player getWitch() {
        return getRole(Role.WITCH);
    }

    private Player getHunter() {
        return getRole(Role.WITCH);
    }
}
