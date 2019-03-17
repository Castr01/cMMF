package cmortmyrefungus.node;

import cmortmyrefungus.data.Settings;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class StopScript extends Task {

    @Override
    public boolean validate() {
        return System.currentTimeMillis() >= Settings.startTime + 9000000 ;
    }

    @Override
    public int execute() {

        Log.severe("Script has reached 2 hour 30 minute limited");
        return -1;

    }
}
