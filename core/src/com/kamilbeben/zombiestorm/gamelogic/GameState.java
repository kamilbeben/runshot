package com.kamilbeben.zombiestorm.gamelogic;

/**
 * Created by bezik on 19.10.16.
 */
public class GameState {

    private enum State {
        OVER, NOT_READY, RUN, PAUSE
    }

    private State state;
    private State previousState;

    public GameState() {
        state = State.NOT_READY;
        previousState = state;
    }

    public boolean isOver() {
        if (state == State.OVER) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNotReady() {
        if (state == State.NOT_READY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPause() {
        if (state == State.PAUSE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGoing() {
        if (state == State.RUN) {
            return true;
        } else {
            return false;
        }
    }

    public void setOver() {
        previousState = state;
        state = State.OVER;
    }

    public void setGoing() {
        previousState = state;
        state = State.RUN;
    }

    public void setPause() {
        previousState = state;
        state = State.PAUSE;
    }

    public void unpause() {
        state = previousState;
        previousState = State.PAUSE;
    }

}
