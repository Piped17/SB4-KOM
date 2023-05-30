import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.playersystem.Player;
import dk.sdu.piped17.cbse.playersystem.PlayerPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest {
    private World world;
    private GameData gameData;
    private PlayerPlugin playerPlugin;

    @BeforeEach
    void setup(){
        world = new World();
        gameData = new GameData();
        playerPlugin = new PlayerPlugin();
    }

    @Test
    @DisplayName("Test if Player is created in the world when the plugin starts")
    void startPlayerPlugin(){
        assertEquals(0, world.getEntities().toArray().length);
        playerPlugin.start(gameData, world);
        assertEquals(1, world.getEntities(Player.class).toArray().length);
    }
}

