package dk.sdu.piped17.cbse.common.services;

import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        /**
         * entity processing after initial processing, such as collision
         *
         * precondition: a cycle has passed since last call, all entities has been processed
         * postcondition: Entity has been processed and updated
         * @param gameData game data
         * @param world world data
         */
        void process(GameData gameData, World world);
}
