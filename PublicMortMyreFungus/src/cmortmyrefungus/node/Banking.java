package cmortmyrefungus.node;

import cmortmyrefungus.data.Locations;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Banking extends Task {

    Locations loc = new Locations();

    @Override
    public boolean validate() {
        return loc.clanWarsArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) == Skills.getLevel(Skill.PRAYER) && !Inventory.contains("Salve graveyard teleport") || loc.clanWarsArea.contains(Players.getLocal()) && Skills.getCurrentLevel(Skill.PRAYER) == Skills.getLevel(Skill.PRAYER) && !Inventory.contains(x -> x.getName().contains("Ring of dueling"));
    }

    @Override
    public int execute() {

        Log.info("Banking");

        if(!Bank.isOpen()){

            Log.info("Opening bank");
            Bank.open(BankLocation.getNearest());
            Time.sleepUntil(Bank::isOpen, 3000);

        }else if(Bank.isOpen() && Inventory.contains("Mort myre fungus")){

            Item ringOfDueling = Inventory.getFirst(x -> x.getName().contains("Ring of dueling"));

                Log.info("Depositing Mort myre fungus");
                Bank.depositAllExcept("Silver sickle (b)");

                Time.sleepUntil(()-> Inventory.contains("Mort myre fungus"), 3000);

            }else if(!Inventory.contains("Mort myre fungus") && Health.getPercent() != 100 && !Inventory.contains("Lobster") && Bank.contains("Lobster")){

            Log.info("Withdrawing food");
            Bank.withdraw("Lobster",1);
            Time.sleepUntil(()-> Inventory.contains("Lobster"), 3000);

        }else if (!Inventory.contains(x -> x.getName().contains("Ring of dueling")) && Bank.contains(x -> x.getName().contains("Ring of dueling"))){

            Log.info("Withdrawing Ring of dueling");
            Bank.withdraw(x -> x.getName().contains("Ring of dueling"),1);
            Time.sleepUntil(()-> Inventory.contains(x -> x.getName().contains("Ring of dueling")), 3000);

        }else if(Inventory.contains(x ->x.getName().contains("Ring of dueling")) && Bank.contains("Salve graveyard teleport")){

            Log.info("Withdrawing Salve graveyard teleport");
            Bank.withdraw("Salve graveyard teleport", 1);
            Time.sleepUntil(()-> Inventory.contains("Salve graveyard teleport"), 3000);

        }

        return 50;
    }
}
