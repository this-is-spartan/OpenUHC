package in.twizmwaz.openuhc.event.game;

import in.twizmwaz.openuhc.game.Game;
import lombok.Getter;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends GameEvent {

  @Getter private static final HandlerList handlerList = new HandlerList();

  public GameEndEvent(Game game) {
    super(game);
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }

}
