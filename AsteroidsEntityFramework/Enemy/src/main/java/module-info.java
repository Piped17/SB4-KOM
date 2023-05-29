import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

module Enemy {
    exports dk.sdu.mmmi.cbse.enemysystem;
    requires Common;
    provides IGamePluginService with EnemyPlugin;


}