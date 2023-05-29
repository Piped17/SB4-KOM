module Core {
    requires Common;
    requires Enemy;
    requires Player;
    requires gdx;
    requires gdx.backend.lwjgl;

    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}