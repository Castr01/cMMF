package cmortmyrefungus.node;

import cmortmyrefungus.data.Locations;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import javax.xml.stream.Location;

public class RestoreStats extends Task {

    Locations loc = new Locations();

    @Override
    public boolean validate() {
        return loc.clanWarsArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) != Skills.getLevel(Skill.PRAYER) && Movement.getRunEnergy() >= 36 || loc.clanWarsArea.contains(Players.getLocal()) && Movement.getRunEnergy() <= 35 || loc.insideClanWarsPortalArea.contains(Players.getLocal());
    }

    @Override
    public int execute() {

        if(Movement.getRunEnergy() >= 36 && !loc.insideClanWarsPortalArea.contains(Players.getLocal())){

            Log.info("Restoring prayer at altar");

            SceneObject altar = SceneObjects.getNearest("Altar");

            if(altar != null && altar.interact("Pray-at")){

                Time.sleepUntil(()-> Skills.getCurrentLevel(Skill.PRAYER) == Skills.getLevel(Skill.PRAYER), 3000);
            }

        }else{

            SceneObject clanWarsPortal = SceneObjects.getNearest("Free-for-all portal");

            if(clanWarsPortal != null && clanWarsPortal.interact("Enter")){

                Time.sleepUntil(()-> loc.insideClanWarsPortalArea.contains(Players.getLocal()),3000);

            }else if(loc.insideClanWarsPortalArea.contains(Players.getLocal())){

                Log.info("Exiting Clan Wars portal");

                  SceneObject exitPortal = SceneObjects.getNearest(x -> x.getName().contains("Portal") && x.containsAction("Exit"));

                  if(exitPortal != null && exitPortal.interact("Exit")){

                      Time.sleepUntil(()-> loc.clanWarsArea.contains(Players.getLocal()),3000);

                  }

            }

        }

        return 50;
    }
}
