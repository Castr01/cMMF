package cmortmyrefungus.node;

import cmortmyrefungus.data.Locations;
import cmortmyrefungus.data.PaintData;
import cmortmyrefungus.data.Settings;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Traversing extends Task {

    Locations loc = new Locations();

    @Override
    public boolean validate() {
        return loc.clanWarsArea.contains(Players.getLocal()) && SceneObjects.getNearest("Free-for-all portal") == null && Movement.getRunEnergy() <= 35 || loc.fungusArea.contains(Players.getLocal()) && Players.getLocal().getPosition().getX() != loc.fungusPosition.getX() && Players.getLocal().getPosition().getY() != loc.fungusPosition.getY() || loc.clanWarsArea.contains(Players.getLocal()) && Inventory.contains("Salve graveyard teleport") && Inventory.contains(x -> x.getName().contains("Ring of dueling")) || loc.fungusArea.contains(Players.getLocal()) && Inventory.isFull() || loc.fungusArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) == 0;
    }

    @Override
    public int execute() {

        if(loc.clanWarsArea.contains(Players.getLocal()) && !Inventory.contains("Salve graveyard teleport")){

            if(!Movement.isRunEnabled() && Movement.getRunEnergy() >= Settings.activateRunAt){

                Log.fine("Activating run");
                Movement.toggleRun(true);

            }

            Log.info("Resetting at Clan Wars portal");
            Movement.walkTo(loc.clanWarsPortal);
            Time.sleep(250,750);

        }else if(loc.fungusArea.contains(Players.getLocal()) && Players.getLocal().getPosition().getX() != loc.fungusPosition.getX() && Players.getLocal().getPosition().getY() != loc.fungusPosition.getY()){

            if(!Movement.isRunEnabled() && Movement.getRunEnergy() >= Settings.activateRunAt){

                Log.fine("Activating run");
                Movement.toggleRun(true);

            }

            Log.info("Walking to Fungi location");
            Movement.walkTo(loc.fungusPosition);
            Time.sleep(250,750);

        }else if(loc.clanWarsArea.contains(Players.getLocal()) && Inventory.contains("Salve graveyard teleport") && Inventory.contains(x -> x.getName().contains("Ring of dueling"))){

            Item salveGraveyardTeleprot = Inventory.getFirst("Salve graveyard teleport");
            Item foodItem = Inventory.getFirst("Lobster");
            Item ringOfDueling = Inventory.getFirst(x -> x.getName().contains("Ring of dueling"));

            if(foodItem != null && foodItem.interact("Eat")){

                Log.fine("Eating");
                Time.sleepUntil(()-> !Inventory.contains("Lobster"), 3000);

            }

            Log.info("Teleporting via Salve graveyard teleport");
            if(salveGraveyardTeleprot != null && salveGraveyardTeleprot.interact("Break")){

                PaintData.teleportsUsed++;

                Time.sleepUntil(()-> loc.fungusArea.contains(Players.getLocal()), 5000);

            }

        }else if(loc.fungusArea.contains(Players.getLocal()) && Inventory.isFull() || loc.fungusArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) == 0){

            Log.info("Teleporting to Clan Wars");

            Item ringOfDueling = Inventory.getFirst(x -> x.getName().contains("Ring of dueling"));

            if(ringOfDueling != null && ringOfDueling.interact("Rub") && !Dialog.isOpen()){

                Time.sleepUntil(Dialog::isOpen, 3000);

            }else if(Dialog.isOpen()){

                Dialog.process("Clan Wars");

                Time.sleepUntil(()-> loc.clanWarsArea.contains(Players.getLocal()), 7000);
            }

        }

        return 40;
    }
}
