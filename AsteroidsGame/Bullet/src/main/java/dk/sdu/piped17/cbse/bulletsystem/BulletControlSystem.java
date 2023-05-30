package dk.sdu.piped17.cbse.bulletsystem;

import dk.sdu.piped17.cbse.common.bullet.Bullet;
import dk.sdu.piped17.cbse.common.bullet.RunTimeInstantiatorService;
import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.data.entityparts.LifePart;
import dk.sdu.piped17.cbse.common.data.entityparts.MovingPart;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;
import dk.sdu.piped17.cbse.common.data.entityparts.TimerPart;
import dk.sdu.piped17.cbse.common.services.IEntityProcessingService;


import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, RunTimeInstantiatorService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            movingPart.shootingProcess(gameData, bullet);
            positionPart.process(gameData, bullet);

            setShape(bullet);
        }
    }

    @Override
    public Entity spawn(PositionPart shooterPart, GameData gameData) {

        float start_x = shooterPart.getX();
        float start_y = shooterPart.getY();
        float direction = shooterPart.getRadians();
        float dt = gameData.getDelta();
        float speed = 350;

        Entity bullet = new Bullet();
        bullet.setRadius(2);

        float bx = (float) cos(direction) *  8 * bullet.getRadius();
        float by = (float) sin(direction) * 8 * bullet.getRadius();

        bullet.add(new PositionPart(bx + start_x, by + start_y, direction));
        bullet.add(new LifePart(1, 2));
        bullet.add(new MovingPart(0,500, 300, 50, 500));
        bullet.add(new TimerPart(2));

        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
