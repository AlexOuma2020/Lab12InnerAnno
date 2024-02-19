package ru.spmi.temnov.lab12;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface tvChar {//аннотация для телевизора
    String display();
    String resolution();
}
