package Game.Entities.DynamicEntities;

import Main.Handler;
import Resources.Images;

public class FireFlower extends Item {

    public FireFlower(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.fireflower);
    }

    @Override
    public void tick(){
        if(!used) {
            if (falling) {
                y = (int) (y + velY);
                velY = velY + gravityAcc;
                checkFalling();
            }
            checkHorizontal();
            move();
        }
    }

}
