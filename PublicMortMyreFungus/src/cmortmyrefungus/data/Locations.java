package cmortmyrefungus.data;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public class Locations {

 public static Area fungusArea = Area.rectangular(3401, 3394, 3486, 3469);
 public static Position fungusPosition = new Position(3406,3407,0);
 public static Area fungusLogArea = Area.rectangular(3404, 3409, 3408, 3405);

 public static Area clanWarsArea = Area.rectangular(3347, 3176, 3394, 3140);
 public static Position clanWarsPortal = new Position(3353, 3162,0);
 public static Area insideClanWarsPortalArea = Area.rectangular(3326, 4749, 3330, 4753, 0);

}
