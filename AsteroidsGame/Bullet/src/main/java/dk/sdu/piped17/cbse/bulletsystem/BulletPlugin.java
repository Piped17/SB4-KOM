package dk.sdu.piped17.cbse.bulletsystem;

import dk.sdu.piped17.cbse.common.bullet.Bullet;
import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.services.IGamePluginService;
import dk.sdu.piped17.cbse.common.bullet.Bullet;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }

}