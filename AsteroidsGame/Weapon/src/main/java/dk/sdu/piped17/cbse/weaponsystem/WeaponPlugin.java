package dk.sdu.piped17.cbse.weaponsystem;

import dk.sdu.piped17.cbse.common.bullet.RunTimeInstantiatorService;
import dk.sdu.piped17.cbse.common.data.Entity;
import dk.sdu.piped17.cbse.common.data.GameData;
import dk.sdu.piped17.cbse.common.data.GameKeys;
import dk.sdu.piped17.cbse.common.data.World;
import dk.sdu.piped17.cbse.common.data.entityparts.PositionPart;
import dk.sdu.piped17.cbse.common.services.IEntityProcessingService;
import dk.sdu.piped17.cbse.playersystem.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WeaponPlugin implements IEntityProcessingService {
    private WeaponPlugin weapon;
    public WeaponPlugin(){

    }
    public void fireWeapon(){
        System.out.println("firing");
    }
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                weapon.fireWeapon();
                for (RunTimeInstantiatorService bullet : getBulletSPIs()) {
                    world.addEntity(bullet.spawn(player.getPart(PositionPart.class), gameData));
                }
                System.out.println("Firing");
            }
        }
    }
    private Collection<? extends RunTimeInstantiatorService> getBulletSPIs() {
        return ServiceLoader.load(RunTimeInstantiatorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
