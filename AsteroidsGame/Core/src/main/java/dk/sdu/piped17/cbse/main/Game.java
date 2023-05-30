package dk.sdu.piped17.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.piped17.cbse.asteroidsystem.AsteroidControlSystem;
import dk.sdu.piped17.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.piped17.cbse.collisionsystem.CollisionPlugin;
import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.services.IEntityProcessingService;
import dk.sdu.piped17.cbse.common.services.IGamePluginService;
import dk.sdu.piped17.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.piped17.cbse.common.util.SPILocator;
import dk.sdu.piped17.cbse.managers.GameInputProcessor;
import dk.sdu.piped17.cbse.playersystem.PlayerControlSystem;
import dk.sdu.piped17.cbse.playersystem.PlayerPlugin;



import java.util.List;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors;
    private List<IGamePluginService> gamePluginServices;
    private List<IPostEntityProcessingService> postEntityProcessors;
    private World world = new World();
    public Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServices, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessors = entityProcessingServices;
        this.postEntityProcessors = postEntityProcessingServices;
    }

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();



        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        //add player
        IGamePluginService playerPlugin = new PlayerPlugin();

        IEntityProcessingService playerProcess = new PlayerControlSystem();
        gamePluginServices.add(playerPlugin);
        entityProcessors.add(playerProcess);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : gamePluginServices) {
            iGamePlugin.start(gameData, world);
        }
        // asteroid
        for (int i = 0; i < MathUtils.random(5, 20); i++) {
            IGamePluginService bigAsteroidPlugin = new AsteroidPlugin(MathUtils.random(2,3));
            gamePluginServices.add(bigAsteroidPlugin);
        }

        //Asteroid controller
        IEntityProcessingService bigAsteroidProcess = new AsteroidControlSystem();
        entityProcessors.add(bigAsteroidProcess);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : gamePluginServices) {
            iGamePlugin.start(gameData, world);
        }

        IPostEntityProcessingService collisionProcess = new CollisionPlugin();
        postEntityProcessors.add(collisionProcess);
    }


    @Override
    public void render(){

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }
        // Collision detection
        for (IPostEntityProcessingService entityPostProcessorService : postEntityProcessors) {
            entityPostProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
