package me.codalot.dragonblock.game.players.process;

import lombok.Getter;
import me.codalot.dragonblock.game.players.Fighter;

@Getter
public abstract class FighterProcess {

    private int updateRate;
    private ProcessState state;

    protected Fighter fighter;

    public FighterProcess(int updateRate, Fighter fighter) {
        this.updateRate = updateRate;
        state = ProcessState.PRE_PROGRESS;

        this.fighter = fighter;
    }

    public void start() {
        if (state == ProcessState.PRE_PROGRESS) {
            state = ProcessState.IN_PROGRESS;
            onStart();
        }
    }

    public void progress() {
        if (state == ProcessState.IN_PROGRESS)
            onProgress();
    }

    public void end() {
        if (state == ProcessState.IN_PROGRESS) {
            state = ProcessState.POST_PROGRESS;
            onEnd();
        }
    }

    public boolean isInProgress() {
        return state == ProcessState.IN_PROGRESS;
    }

    public boolean hasEnded() {
        return state == ProcessState.POST_PROGRESS;
    }

    protected abstract void onStart();

    protected abstract void onProgress();

    protected abstract void onEnd();
}
