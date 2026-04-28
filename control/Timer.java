package control;

public class Timer {
    public enum TimerState{GOING, STOPPED, CLEARED}
    private TimerState state;
    private Long startTime;
    private Long endTime;

    public Timer() {
        state = TimerState.CLEARED;
        startTime = null;
        endTime = null;
    }

    public void startTimer() {
        if (state.equals(TimerState.STOPPED)) {
            state = TimerState.GOING;
            startTime = System.currentTimeMillis();
        }
        else if (state.equals(TimerState.CLEARED)) {
            state = TimerState.GOING;
            startTime = System.currentTimeMillis();
        }
    }
    public void endTimer() {
        if (state.equals(TimerState.GOING)) {
            state = TimerState.STOPPED;
            endTime = System.currentTimeMillis();
        }
    }
    public void clearTimer() {
        if (state.equals(TimerState.CLEARED)) return;
        state = TimerState.CLEARED;
        startTime = null;
        endTime = null;
    }

    public Long currTimeElapsedMS() {
        if (!state.equals(TimerState.GOING)) throw new IllegalStateException("Timer is not GOING.");
        return System.currentTimeMillis() - startTime;
    }
    public Long timeElapsedMS() {
        if (!state.equals(TimerState.STOPPED)) throw new IllegalStateException("Timer must be STOPPED.");
        return endTime - startTime;
    }
    public TimerState state() { return state; }
}

