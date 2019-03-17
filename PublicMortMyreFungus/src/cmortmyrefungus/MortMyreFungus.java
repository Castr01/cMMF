package cmortmyrefungus;

import cmortmyrefungus.data.PaintData;
import cmortmyrefungus.data.Settings;
import cmortmyrefungus.node.*;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;

@ScriptMeta(developer = "C",name = "cMortMyreFungi",desc = "Picks Mort Myre Fungus",category = ScriptCategory.MONEY_MAKING,version = 0.1)
public class MortMyreFungus extends TaskScript implements RenderListener {

    private static final Task[] TASKS = {new Traversing(),new RestoreStats(),new GrabFungi(),new Banking()};

    @Override
    public void onStart() {

        Settings.startTime = System.currentTimeMillis();
        Log.fine("Starting cMortMyreFungus");
        Log.severe("This script will only run for 2 hours 30 minutes before stopping.");
        Log.fine("Food set to: Lobster");
        Log.fine("Traveling to Mort myre via: Salve graveyard teleport");
        Log.fine("Banking via: Ring of dueling (Clan Wars)");

        submit(TASKS);

    }

    @Override
    public void notify(RenderEvent renderEvent) {

        final long runTime = System.currentTimeMillis() - Settings.startTime;

        Graphics g = renderEvent.getSource();
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();

        g.setColor(Color.green);
        g2.setStroke(new BasicStroke(3));
        g.drawRect(22, 50, 190, 128);
        g2.setStroke(oldStroke);
        g.setColor(new Color(50, 50, 50, 200));
        g.fillRect(22, 50, 190, 128);

        Font titleFont = new Font("Consolas", 1, 15);
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("cMortMyreFungi", 53, 70);

        Font font1 = new Font("Consolas",0, 12);
        g.setFont(font1);
        g.drawString("Runtime: "+Settings.formatTime(runTime), 27,90);
        g.drawString("Profit: " + Settings.formatValue(PaintData.fungiPicked * 630),27,110);
        g.drawString("Fungi picked: "+PaintData.fungiPicked,27,130);
        g.drawString("Trips done: "+PaintData.teleportsUsed,27,150);
    }
}
