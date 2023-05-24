package dk.sdu.piped17.cbse.asteroidsystem;

import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.data.entityparts.LifePart;
import dk.sdu.piped17.cbse.common.data.entityparts.MovingPart;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;
import dk.sdu.piped17.cbse.common.services.IGamePluginService;
import com.badlogic.gdx.math.MathUtils;

public class AsteroidPlugin implements IGamePluginService {
    private Entity asteroid;
    private int life;
    private float deacceleration, acceleration, maxSpeed, rotationSpeed;
    private int shapePointCount;

    public AsteroidPlugin(){
        this(3);
    }

    public AsteroidPlugin(int life) {
        this.life = life;
        this.deacceleration = 0;
        this.acceleration = 0;
        this.maxSpeed = 400;
        this.rotationSpeed = 0;
        this.shapePointCount = 8;
    }

    /**
     * calls for creation of first asteroid
     *
     * @param gameData game data
     * @param world world data
     */
    @Override
    public void start(GameData gameData, World world) {
        asteroid = createFirstAsteroid(gameData);
        world.addEntity(asteroid);
    }

    /**
     * creates the first asteroid
     * precondition: creates an asteroid that hasn't been split yet
     * postcondition: an asteroid has been created with a starting set of data
     * @param gameData
     * @return returns asteroid to be added to world
     */
    private Entity createFirstAsteroid(GameData gameData) {
        float x = MathUtils.random(gameData.getDisplayWidth());
        float y = MathUtils.random(gameData.getDisplayHeight());
        float radians = MathUtils.random(0, (float) (2 * Math.PI));

        float startSpeed = MathUtils.random(25f, 75f);

        Entity asteroid = new Asteroid();
        this.setAsteroidRadius(asteroid);

        this.buildAsteroid(gameData, asteroid, x, y, radians, startSpeed);

        return asteroid;
    }

    /**
     * creates a split asteroid, uses some data from previous initial asteroid
     *
     * precondition: an asteroid needs to be split
     * postcondition: asteroid is split and added to world
     * @param gameData gamme data
     * @param world world data
     * @param asteroid asteroid to be split
     */
    public void createSplitAsteroid(GameData gameData, World world, Entity asteroid){
        world.removeEntity(asteroid);
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        MovingPart movingPart = asteroid.getPart(MovingPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);

        this.life = lifePart.getLife() - 1;

        if (lifePart.getLife() <= 1) {
            return;
        }
        float[] splits = new float[] {(float) ((Math.PI * 1/2)), (float) ((Math.PI * 1/2) * (-1))};

        for (float split : splits) {
            Entity splittetAsteroid = new Asteroid();

            this.setAsteroidRadius(splittetAsteroid);

            float radians = positionPart.getRadians() + split;

            float bx = (float) Math.cos(radians) * asteroid.getRadius();
            float x = bx + positionPart.getX();
            float by = (float) Math.sin(radians) * asteroid.getRadius();
            float y = by + positionPart.getY();

            float startSpeed = MathUtils.random(movingPart.getSpeed(), 75f);

            this.buildAsteroid(gameData, splittetAsteroid, x, y, radians, startSpeed);

            world.addEntity(splittetAsteroid);
        }
    }

    /**
     * building part for an asteroid
     * precondition: asteroid that needs to be added but has no parts added
     * postcondition: Asteroid has been built with needed parts and added
     * @param gameData game data
     * @param asteroid asteroid to be added to world
     * @param x starting x position of asteroid
     * @param y starting y position of asteroid
     * @param radians initial radians
     * @param startSpeed initial speed
     */
    private void buildAsteroid(GameData gameData, Entity asteroid, float x, float y, float radians, float startSpeed) {
        asteroid.setShapeX(new float[this.shapePointCount]);
        asteroid.setShapeY(new float[this.shapePointCount]);
        asteroid.add(new MovingPart(this.deacceleration, this.acceleration, this.maxSpeed, this.rotationSpeed, startSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        LifePart lifePart = new LifePart(this.life, 0);
        asteroid.add(lifePart);
        this.setAsteroidRadius(asteroid);
    }

    /**
     * sets radius of the asteroid based on life.
     *
     * precondition: asteroid exists
     * postcondition: asteroid has radius set
     * @param asteroid to have radius set
     */
    private void setAsteroidRadius(Entity asteroid) {
        float radius = 0;
        switch (this.life) {
            case 1:
                radius = 10;
                break;
            case 2:
                radius = 15;
                break;
            case 3:
                radius = 25;
                break;
            default:
                break;
        }
        asteroid.setRadius(radius);
    }


    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(asteroid);
    }
}
