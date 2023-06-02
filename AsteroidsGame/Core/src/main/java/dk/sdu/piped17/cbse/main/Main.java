package dk.sdu.piped17.cbse.main;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

		cfg.title = "Asteroids";
		cfg.width = 500;
		cfg.height = 400;
		cfg.useGL30 = false;
		cfg.resizable = false;
		
		new LwjglApplication(ctx.getBean(Game.class), cfg);
		
	}
	
}
