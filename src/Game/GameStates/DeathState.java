package Game.GameStates;

import java.awt.Color;
import java.awt.Graphics;

import Display.UI.UIManager;
import Display.UI.UIStringButton;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Player;
import Main.Handler;
import Resources.Images;

public class DeathState extends State {

	private UIManager uiManager;

	public DeathState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIStringButton(340, (300+(64+16))+(64+16), 128, 64, "Title", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.setIsInMap(false);
			State.setState(handler.getGame().menuState);
		},handler,Color.WHITE));

	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		if(handler.isMultiPlayer() == true) {
			if(Player.marioCoins>=10) {
				g.drawImage(Images.marioWins,0,0,handler.getWidth(),handler.getHeight(),null);
				uiManager.Render(g);

			}
			if (Player.luigiCoins>=10){
				g.drawImage(Images.luigiWins,0,0,handler.getWidth(),handler.getHeight(),null);
				uiManager.Render(g);			}
		}
		else {
			g.drawImage(Images.gameOver,0,0,handler.getWidth(),handler.getHeight(),null);
			uiManager.Render(g);	
		}
	}
}
