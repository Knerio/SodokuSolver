package de.derioo.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConsoleClearer {

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
