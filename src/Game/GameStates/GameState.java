package Game.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Display.UI.UIPointer;
import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.Player;
import Game.World.MapBuilder;
import Main.Handler;
import apple.laf.JRSUIUtils.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {

	public GameState(Handler handler){
		super(handler);
		handler.getGame().pointer = new UIPointer(28 * MapBuilder.pixelMultiplier,197 * MapBuilder.pixelMultiplier,128,128,handler);

	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			State.setState(handler.getGame().pauseState);
		}

		if(handler.isMultiPlayer() == true) {
			handler.getMario().tick();
			handler.getLuigi().tick();
			if(handler.getKeyManager().skill && !handler.getMario().doubleJump==true){
				handler.getMario().doubleJump();	
			}
			if (handler.getKeyManager().skill2 && !handler.getLuigi().floating==true) { 
				handler.getLuigi().floating();
			}
			
			if(Player.marioCoins >= 10) {
				handler.getGame().getMusicHandler().play("marioDies");

				State.setState(handler.getGame().deathState);
			}
			
			else if(Player.luigiCoins >= 10) {
				handler.getGame().getMusicHandler().play("marioDies");

				State.setState(handler.getGame().deathState);
			}

		}

		else if(handler.isSinglePlayer() == true) {
			handler.getMario().tick();
		}
		if(handler.getMap().getListener() != null && MapBuilder.mapDone) {
			handler.getMap().getListener().tick();
			handler.getMap().getHand().tick();
			handler.getMap().getWalls().tick();
		}
		for (BaseDynamicEntity entity:handler.getMap().getEnemiesOnMap()) {
			entity.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		handler.getMap().drawMap(g2);
		if(handler.isMultiPlayer() == true) {
			Font font = new Font ("SansSerif", Font.PLAIN, 24);
			g2.setFont(font);
			g2.setColor(Color.RED);
			g2.drawString("Mario's Coins = " + Player.marioCoins, 10, 30);
			g2.setColor(Color.GREEN);
			g2.drawString("Luigi's Coins = " + Player.luigiCoins, handler.getWidth()-210, 30);
		}
	}

}
