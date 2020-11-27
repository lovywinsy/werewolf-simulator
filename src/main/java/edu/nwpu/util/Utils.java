package edu.nwpu.util;

import edu.nwpu.model.Player;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Utils {
    public static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static List<String> getPlayerNameList() {
        return Arrays.asList("二师兄", "段肥猫", "郭孜然", "大鲤鱼", "JZ", "五月", "谈小歪", "沙师兄", "批西", "小熊猫", "施嘉祺", "咖啡鱼");
    }

    public static List<Player> getPlayerList() {
        return getPlayerNameList().stream().map(name -> Player.builder().name(name).build()).collect(Collectors.toList());
    }

    public static boolean getRandomBoolean() {
        return getRandomBooleanWithProbability(1d);
    }

    public static boolean getRandomBooleanWithProbability(double p) {
        return Math.random() <= p;
    }

    public static <T> T getRandomOne(List<T> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    public static <T> T getAndRemoveRandomOne(List<T> items) {
        return items.remove(new Random().nextInt(items.size()));
    }

    public static <T> List<T> shuffleList(List<T> items) {
        Collections.shuffle(items);
        return items;
    }

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.get(0)
        );
    }
}
