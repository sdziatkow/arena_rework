package charData.statMods;

import control.handlers.StatChangeHandler;

public class StatModThread implements Runnable {
    private int gameCharID;
    private StatMod mod;
    private long duration;
    private long increment;

    public StatModThread(Integer gameCharID, StatMod mod, long duration, long increment) {
        this.gameCharID = gameCharID;
        this.mod = mod;
        this.duration = duration * 1000;
        this.increment = increment * 1000;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long elapsedTime;
        do {
            try{Thread.sleep(increment);}
            catch(InterruptedException e) {System.out.println("STAT MOD THREAD INTERRUPTED");}
            elapsedTime = System.currentTimeMillis() - startTime;
            StatChangeHandler.applyStatMod(gameCharID, mod);
        } while (elapsedTime < duration);
    }
}
