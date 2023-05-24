package dk.sdu.piped17.cbse.asteroidsystem;

import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.data.entityparts.LifePart;
import dk.sdu.piped17.cbse.common.data.entityparts.MovingPart;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;
import dk.sdu.piped17.cbse.common.services.IEntityProcessingService;
import com.badlogic.gdx.math.MathUtils;

/**
 * asteroid control system, handles movement of asteroids
 */
public class AsteroidControlSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            this.splitAsteroid(gameData, world, asteroid);
            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);
            if (lifePart.isDead()) {
                world.removeEntity(asteroid);
            }

            this.updateShape(asteroid);
        }

    }

    /**
     * splits asteroid if needed
     *
     * precondition: Asteroid is in the game and has life
     * postcondition: splits an asteroid that needs to be split
     * @param gameData game data
     * @param world world data
     * @param asteroid the asteroid that needs splitting
     */
    private void splitAsteroid(GameData gameData, World world, Entity asteroid) {
        LifePart lifePart = asteroid.getPart(LifePart.class);

        if (!lifePart.isIsHit() || lifePart.isDead()) {
            return;
        }
        AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
        asteroidPlugin.createSplitAsteroid(gameData, world, asteroid);

        return;
    }

    /**
     * logic for updating the shape of asteroid
     *
     * precondition: game cycle has passed since last entity call
     * postcondition: updated shape for entity
     * @param entity entity which shape needs updating
     */
    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        LifePart lifePart = entity.getPart(LifePart.class);

        float[] distances = new float[8];
        switch (lifePart.getLife()) {
            default:
            case 1:
                distances = new float[] {10, 8, 10, 6, 2, 10, 9, 10};
                break;
            case 2:
                distances = new float[] {18, 5, 15, 10, 18, 18, 15, 18};
                break;
            case 3:
                distances = new float[] {25, 20, 23, 21, 26, 18, 25, 25};
                break;
        }

        for (int i = 0; i < 8; i++) {
            shapeX[i] = (float) (x + MathUtils.cos(radians + MathUtils.PI * (i / 4f)) * distances[i]);
            shapeY[i] = (float) (y + MathUtils.sin(radians + MathUtils.PI * (i / 4f)) * distances[i]);
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }

}
