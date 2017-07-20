package in.twizmwaz.openuhc;

import in.twizmwaz.openuhc.game.Game;
import in.twizmwaz.openuhc.module.ModuleData;
import in.twizmwaz.openuhc.module.ModuleFactory;
import in.twizmwaz.openuhc.module.ModuleHandler;
import in.twizmwaz.openuhc.module.ModuleRegistry;

import java.util.UUID;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class OpenUHC extends JavaPlugin {

  @Getter private static OpenUHC instance;

  private ModuleHandler moduleHandler;
  private Game game;

  /**
   * Enables OpenUHC.
   */
  @Override
  public void onEnable() {
    instance = this;
    getLogger().info("OpenUHC has started.");

    // Load modules
    final ModuleFactory loader = new ModuleFactory();
    loader.findEntries(getFile());
    ModuleRegistry.registerModules(loader);
    moduleHandler = new ModuleHandler();
    ModuleRegistry.getServerModules().forEach(data -> {
      moduleHandler.enableModule(data.getClazz());
    });
    // Load default modules
    for (ModuleData data : ModuleRegistry.getServerModules()) {
      if (data.isEnabledOnStart()) {
        moduleHandler.enableModule(data.getClazz());
      }
    }

    // Create first game.
    World world = Bukkit.createWorld(new WorldCreator(UUID.randomUUID().toString()));
    game = new Game(world);
  }

  @Override
  public void onDisable() {
    moduleHandler.disableAllModules();
  }

  public static void registerEvents(Listener events) {
    Bukkit.getPluginManager().registerEvents(events, instance);
  }

  public static Game getCurrentGame() {
    return getInstance().getGame();
  }

}
