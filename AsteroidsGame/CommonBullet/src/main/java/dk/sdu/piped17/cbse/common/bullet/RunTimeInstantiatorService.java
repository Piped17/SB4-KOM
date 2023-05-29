package dk.sdu.piped17.cbse.common.bullet;

import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;


public interface RunTimeInstantiatorService {
    Entity spawn(PositionPart e, GameData gamedata);
}
