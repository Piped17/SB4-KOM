package dk.sdu.piped17.cbse.common.services;

import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;

public interface IEntityProcessingService {
    /**
     * process the entity
     * precondition: cycle has passed since last call
     * postcondition: entiti has been processed and updated
     * @param gameData data for the game
     * @param world world of the game
     */

    void process(GameData gameData, World world);
}
