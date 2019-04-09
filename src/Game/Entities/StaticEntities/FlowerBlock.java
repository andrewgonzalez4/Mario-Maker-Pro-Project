package Game.Entities.StaticEntities;

import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class FlowerBlock extends BaseDynamicEntity {

	public Animation anim;
	public FlowerBlock(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height,handler, Images.flowerBlock[2]);
		anim = new Animation(160,Images.flowerBlock);
	}

}
