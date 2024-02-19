package ru.spmi.temnov.lab12;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface fridgeChar {//аннотация для холодильника
    String color();
    int volume();
}