package control;

import java.util.HashSet;
import java.util.Random;

public class IDGen {

    public final static HashSet<Integer> IDS = new HashSet<>(20);

    public static int genID() {
        Random gen = new Random();
        int newID;
        do {
            newID = Math.abs(gen.nextInt(89000)) + 10000;
        } while(!IDS.add(newID));
        return newID;
    }

    public static void genAmntIDS(int amnt) {
        for (int i = 0; i < amnt; ++i) genID();
    }
}
