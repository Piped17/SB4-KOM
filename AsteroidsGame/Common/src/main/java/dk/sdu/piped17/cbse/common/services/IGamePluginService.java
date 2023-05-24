package dk.sdu.piped17.cbse.common.services;

import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;

public interface IGamePluginService {
    /**
     * start a plugin
     * precondition: game is starting, plugins not yet started
     * postcondition: plugin has been started, entities added to world if needed
     * @param gameData game data
     * @param world world data
     */
    void start(GameData gameData, World world);

    /**
     * stop a plugin
     * precondition: game is stopping, plugins not yet stopped
     * postcondition: plugin stopped, entities removed from world
     * @param gameData game data
     * @param world world data
     */
    void stop(GameData gameData, World world);
}
