package Game.Entities.StaticEntities;

import java.awt.Dimension;

import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class FlowerBlock extends BaseDynamicEntity {

	public Animation anim;
	public FlowerBlock(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height,handler, Images.flowerBlock[0]);
		anim = new Animation(100,Images.flowerBlock);
	}

	@Override
	public void tick(){
		super.tick();
		anim.tick();

	}

}
