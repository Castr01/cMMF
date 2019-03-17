package cmortmyrefungus.node;

import cmortmyrefungus.data.Locations;
import cmortmyrefungus.data.PaintData;
import cmortmyrefungus.data.Settings;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class GrabFungi extends Task {

    Locations loc = new Locations();

    @Override
    public boolean validate() {
        return loc.fungusLogArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) > 0 && !Inventory.isFull() && !Settings.isPicking;
    }

    @Override
    public int execute() {

        Item silverSickle = Inventory.getFirst("Silver sickle (b)");
        SceneObject fungusOnLog = SceneObjects.getNearest("Fungi on log");

        if(Players.getLocal().getPosition().getX() == loc.fungusPosition.getX() && Players.getLocal().getPosition().getY() == loc.fungusPosition.getY()) {

                Log.info("Blooming, for fungi");
                if (fungusOnLog == null && silverSickle != null && silverSickle.interact("Cast Bloom")) {

                    Time.sleepUntil(()-> Players.getLocal().isAnimating(),3000);
                    Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 3000);
                }

            if (loc.fungusLogArea.contains(fungusOnLog) && fungusOnLog.interact("Pick")) {

                PaintData.fungiPicked++;

                Log.info("Picking fungi");

                Log.fine("Mort Myre fungi picked: " + PaintData.fungiPicked);

                   Time.sleep(750,1250);

            }

            }else {

            Log.info("Moving back to Blooming spot");
            Movement.walkTo(loc.fungusPosition);
            Time.sleep(250, 500);

        }

        return 50;
    }
}
