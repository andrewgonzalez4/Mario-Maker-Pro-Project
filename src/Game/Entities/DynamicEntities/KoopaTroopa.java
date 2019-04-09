package Game.Entities.DynamicEntities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;

public class KoopaTroopa extends BaseDynamicEntity {

	public Animation left;
	public Animation right;
	
	public KoopaTroopa(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height, handler, Images.KoopaTroopaRight[0]);
		right = new Animation(160,Images.KoopaTroopaRight);
		left = new Animation(160,Images.KoopaTroopaLeft);
	}

	@Override
	public void tick(){
		if(!ded && dedCounter==0) {
			super.tick();
			if(direction.equals("Right")){
				right.tick();
			}
			else {
				left.tick();
			}
			if (falling) {
				y = (int) (y + velY);
				velY = velY + gravityAcc;
				checkFalling();
			}
			checkHorizontal();
			move();
		}else if(ded&&dedCounter==0){
			y++;
			height--;
			setDimension(new Dimension(width,height));
			if (height==0){
				dedCounter=1;
				y=-10000;
			}
		}
	}

	@Override
	public void kill() {
		if(direction.equals("Right")){
			sprite = Images.KoopaTroopaRightDies;
		}
		else {
			sprite = Images.KoopaTroopaLeftDies;       
		}
		ded=true;
	}
}
