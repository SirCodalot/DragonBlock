package me.codalot.dragonblock.game.fighters.process;

import me.codalot.dragonblock.utils.TimeUtils;

import java.util.HashSet;
import java.util.Set;

public class ProcessHandler extends HashSet<FighterProcess> {

    public void update() {
        Set<FighterProcess> remove = new HashSet<>();

        for (FighterProcess process : this) {
            if (!TimeUtils.every(process.getUpdateRate()))
                continue;

            process.progress();

            if (process.hasEnded())
                remove.add(process);
        }

        removeAll(remove);
    }

    public boolean addAndStart(FighterProcess process) {
        if (add(process)) {
            process.start();
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends FighterProcess> void endAll() {
        Set<T> remove = new HashSet<>();

        for (FighterProcess process : this) {
            try {
                process.end();
                remove.add((T) process);
            } catch (Exception ignored) {}
        }

        removeAll(remove);
    }

    public <T extends FighterProcess> int getProcessCount() {
        int count = 0;

        for (FighterProcess process : this) {
            try {
                T cast = (T) process;
            } catch (Exception e) {
                continue;
            }

            count++;
        }

        return count;
    }

    public void interrupt() {
        forEach(FighterProcess::interrupt);
    }
}
