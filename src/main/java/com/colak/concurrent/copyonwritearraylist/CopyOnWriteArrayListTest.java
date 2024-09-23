package com.colak.concurrent.copyonwritearraylist;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
class CopyOnWriteArrayListTest {

    public static void main() {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        for (String item : list) {
            log.info(item);
        }
        list.remove("A");
        log.info("Updated list: {}" , list);
    }
}
