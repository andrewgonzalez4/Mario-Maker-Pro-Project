package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by AlexVR on 7/1/2018.
 */

public class KeyManager implements KeyListener {

	private boolean[] keys,justPressed,cantPress;
	public boolean up=false, down=false, left=false, right=false,pbutt=false,runbutt=false, 
			up2=false, down2=false, left2=false, right2=false,pbutt2=false,runbutt2=false,
			skill=false, skill2=false;


	public KeyManager(){

		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];

	}

	public void tick(){
		for(int i =0; i < keys.length;i++){
			if(cantPress[i] && !keys[i]){
				cantPress[i]=false;

			}else if(justPressed[i]){
				cantPress[i]=true;
				justPressed[i] =false;
			}
			if(!cantPress[i] && keys[i]){
				justPressed[i]=true;
			}
		}

		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D]; 

		pbutt = keys[KeyEvent.VK_ESCAPE];
		runbutt = keys[KeyEvent.VK_SHIFT];
		skill = keys[KeyEvent.VK_E];
		
		up2 =  keys[KeyEvent.VK_UP];
		down2 =  keys[KeyEvent.VK_DOWN];
		left2 =  keys[KeyEvent.VK_LEFT];
		right2 = keys[KeyEvent.VK_RIGHT];

		pbutt2 = keys[KeyEvent.VK_ESCAPE];
		runbutt2 = keys[KeyEvent.VK_SLASH];
		skill2 = keys[KeyEvent.VK_PERIOD];


	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}

}
