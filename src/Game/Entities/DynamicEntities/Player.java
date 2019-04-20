package Game.Entities.DynamicEntities;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.BoundBlock;
import Game.Entities.StaticEntities.FlowerBlock;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;

public class Player extends BaseDynamicEntity {

	protected double velX,velY;

	public String facing = "Left";
	public boolean moving = false;
	public Animation playerSmallLeftAnimation,playerSmallRightAnimation,playerBigLeftWalkAnimation,playerBigRightWalkAnimation,playerBigLeftRunAnimation,playerBigRightRunAnimation;
	public boolean falling = true, jumping = false,isBig=false,running = false,changeDirrection=false, activatedFlower = false, marioDies = false;
	public double gravityAcc = 0.38;
	public int marioCoins = 0, luigiCoins = 0;
	int changeDirectionCounter=0;

	public Player(int x, int y, int width, int height, Handler handler, BufferedImage sprite,Animation PSLA,Animation PSRA,Animation PBLWA,Animation PBRWA,Animation PBLRA,Animation PBRRA) {
		super(x, y, width, height, handler, sprite);
		playerBigLeftRunAnimation=PBLRA;
		playerBigLeftWalkAnimation=PBLWA;
		playerBigRightRunAnimation=PBRRA;
		playerBigRightWalkAnimation=PBRWA;
		playerSmallLeftAnimation=PSLA;
		playerSmallRightAnimation=PSRA;
	}

	@Override
	public void tick(){

		if (changeDirrection) {
			changeDirectionCounter++;
		}
		if(changeDirectionCounter>=10){
			changeDirrection=false;
			changeDirectionCounter=0;
		}

		checkBottomCollisions();
		checkMarioHorizontalCollision();
		checkTopCollisions();
		checkItemCollision();

		if(!isBig) {
			if (facing.equals("Left") && moving) {
				playerSmallLeftAnimation.tick();
			} else if (facing.equals("Right") && moving) {
				playerSmallRightAnimation.tick();
			}
		}else{
			if (facing.equals("Left") && moving && !running) {
				playerBigLeftWalkAnimation.tick();
			} else if (facing.equals("Left") && moving && running) {
				playerBigLeftRunAnimation.tick();
			} else if (facing.equals("Right") && moving && !running) {
				playerBigRightWalkAnimation.tick();
			} else if (facing.equals("Right") && moving && running) {
				playerBigRightRunAnimation.tick();
			}
		}
	}

	private void checkItemCollision() {
		
		for (BaseDynamicEntity entity : handler.getMap().getEnemiesOnMap()) {
			if (entity != null && getBounds().intersects(entity.getBounds()) && entity instanceof Item && entity instanceof Coin == false && !isBig) {
				isBig = true;
				this.y -= 8;
				this.height += 8;
				setDimension(new Dimension(width, this.height));
				((Item) entity).used = true;
				entity.y = -100000;
			}
			if(entity != null && getBounds().intersects(entity.getBounds()) && entity instanceof Item && entity instanceof Coin) {
				
				((Item) entity).used = true;
				entity.y = -100000;
				marioCoins ++;
				System.out.println(marioCoins);
			}
		}

	}



	public void checkBottomCollisions() {
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies =  handler.getMap().getEnemiesOnMap();

		Rectangle marioTopBounds =mario.getTopBounds();
		Rectangle marioBottomBounds =getBottomBounds();

		if (!mario.jumping) {
			falling = true;
		}

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickTopBounds = brick.getTopBounds();
			Rectangle brickBottomBounds = brick.getBottomBounds();

			if(brick instanceof BoundBlock) {
				if(marioTopBounds.intersects(brickBottomBounds)) {
					marioDies = true;
				}
			}
			if (marioBottomBounds.intersects(brickTopBounds)) {
				mario.setY(brick.getY() - mario.getDimension().height + 1);
				falling = false;
				velY=0;
			}
		}

		if(marioDies) {
			handler.getGame().getMusicHandler().play("marioDies");
			State.setState(handler.getGame().deathState);
			handler.getMap().reset();
		}

		for (BaseDynamicEntity enemy : enemies) {
			Rectangle enemyTopBounds = enemy.getTopBounds();

			if(marioBottomBounds.intersects(enemyTopBounds) && enemy instanceof FlowerBlock) {
				mario.setY(enemy.getY() - mario.getDimension().height + 1);
				falling = false;
				velY=0;
			}

			if(enemy instanceof Coin) {

			}
			else if (marioBottomBounds.intersects(enemyTopBounds) && !(enemy instanceof Item) && !(enemy instanceof Mario) && !(enemy instanceof Luigi)) {
				if(!enemy.ded) {
					handler.getGame().getMusicHandler().playStomp();
				}
				enemy.kill();
				falling=false;
				velY=0;

			}
		}
	}


	public void checkTopCollisions() {
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies = handler.getMap().getEnemiesOnMap();

		Rectangle marioTopBounds = mario.getTopBounds();
		Rectangle marioBottomBounds = mario.getBottomBounds();
		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBottomBounds = brick.getBottomBounds();
			Rectangle brickTopBounds = brick.getTopBounds();

			if(brick instanceof BoundBlock) {
				if(marioBottomBounds.intersects(brickTopBounds)) {
					marioDies = true;
				}
			}
			if (marioTopBounds.intersects(brickBottomBounds)) {
				velY=0;
				mario.setY(brick.getY() + brick.height);
			}
		}

		for(BaseDynamicEntity enemy : enemies){
			if (marioTopBounds.intersects(enemy.getBottomBounds()) && !(enemy instanceof Mario) && !(enemy instanceof Luigi)) {
				if(enemy instanceof FlowerBlock) {
					velY=0;
					mario.setY(enemy.getY() + enemy.height);
					activatedFlower = true;
				}

				if(enemy instanceof Coin) {

				}
				else {
					marioDies = true;
					break;
				}
			}
		}

		if(activatedFlower) {
			System.out.println("Hola");
			activatedFlower = false;
		}

		if(marioDies) {
			handler.getGame().getMusicHandler().play("marioDies");
			State.setState(handler.getGame().deathState);
			handler.getMap().reset();
		}
	}

	public void checkMarioHorizontalCollision(){
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies = handler.getMap().getEnemiesOnMap();

		boolean toRight = moving && facing.equals("Right");

		Rectangle marioBounds = toRight ? mario.getRightBounds() : mario.getLeftBounds();

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
			if (marioBounds.intersects(brickBounds)) {
				velX=0;
				if(toRight)
					mario.setX(brick.getX() - mario.getDimension().width);
				else
					mario.setX(brick.getX() + brick.getDimension().width);

				if(brick instanceof BoundBlock) {
					if(marioBounds.intersects(getRightBounds()) || marioBounds.intersects(getLeftBounds())) {
						marioDies = true;
					}
				}
			}
		}

		for(BaseDynamicEntity enemy : enemies){
			Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
			if (marioBounds.intersects(enemyBounds) && !(enemy instanceof Mario) && !(enemy instanceof Luigi)) {
				velX=0;

				if(enemy instanceof FireFlower) {

				}

				if(enemy instanceof Coin) {

				}

				else if(enemy instanceof FlowerBlock) {
					if(toRight)
						mario.setX(enemy.getX() - mario.getDimension().width);
					else
						mario.setX(enemy.getX() + enemy.getDimension().width);
				}

				else if(enemy instanceof Mushroom) {

				}

				else if(isBig == true){
					if (facing.equals("Left") && moving) {
						playerSmallLeftAnimation.tick();

					} else if (facing.equals("Right") && moving) {
						playerSmallRightAnimation.tick();
					}
				}

				else {
					marioDies = true;
					break;
				}
			}

		}


		if(marioDies) {
			handler.getGame().getMusicHandler().play("marioDies");
			State.setState(handler.getGame().deathState);
			handler.getMap().reset();
		}
	}

	public void jump() {
		if(!jumping && !falling){
			jumping=true;
			velY=10;
			handler.getGame().getMusicHandler().playJump();

			if(handler.isMultiPlayer() == true) {
				handler.getMario().doubleJump=false;
				handler.getLuigi().floating=false;
			}

			else {
				handler.getMario().doubleJump=false;
			}
		}
	}

	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}


}
