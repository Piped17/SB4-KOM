package dk.sdu.piped17.cbse.playersystem;

import dk.sdu.piped17.cbse.common.bullet.RunTimeInstantiatorService;
import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import static dk.sdu.piped17.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.piped17.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.piped17.cbse.common.data.GameKeys.UP;
import static java.util.stream.Collectors.toList;

import dk.sdu.piped17.cbse.common.data.GameKeys;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.data.entityparts.MovingPart;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;
import dk.sdu.piped17.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                for (RunTimeInstantiatorService bullet : getBulletSPIs()) {
                    world.addEntity(bullet.spawn(player.getPart(PositionPart.class), gameData));
                }
            }
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }
    private Collection<? extends RunTimeInstantiatorService> getBulletSPIs() {
        return ServiceLoader.load(RunTimeInstantiatorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
