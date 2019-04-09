package Game.World;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.FireFlower;
import Game.Entities.DynamicEntities.Goomba;
import Game.Entities.DynamicEntities.KoopaTroopa;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Mushroom;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.BoundBlock;
import Game.Entities.StaticEntities.BreakBlock;
import Game.Entities.StaticEntities.FlowerBlock;
import Game.Entities.StaticEntities.MisteryBlock;
import Game.Entities.StaticEntities.SurfaceBlock;
import Main.Handler;
import Resources.Images;

public class MapBuilder {

	public static int pixelMultiplier = 48;
	public static int boundBlock = new Color(0,0,0).getRGB();
	public static int mario = new Color(255,0,0).getRGB();
	public static int surfaceBlock = new Color(255,106,0).getRGB();
	public static int breakBlock = new Color(0,38,255).getRGB();
	public static int misteryBlock = new Color(255,216,0).getRGB();
	public static int flowerBlock = new Color(70, 219, 179).getRGB();
	public static int mushroom = new Color(178,0,255).getRGB();
	public static int fireFlower = new Color(214, 108, 51).getRGB();
	public static int goomba = new Color(167,15,1).getRGB();
	public static int koopaTroopa = new Color(221, 88, 135).getRGB();
	public static boolean mapDone = false;

	public static Map createMap(BufferedImage mapImage, Handler handler){
		Map mapInCreation = new Map(handler);
		for (int i = 0; i < mapImage.getWidth(); i++) {
			for (int j = 0; j < mapImage.getHeight(); j++) {
				int currentPixel = mapImage.getRGB(i, j);
				int xPos = i*pixelMultiplier;
				int yPos = j*pixelMultiplier;
				if(currentPixel == boundBlock){
					BaseStaticEntity BoundBlock = new BoundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BoundBlock);
				}else if(currentPixel == mario){
					BaseDynamicEntity Mario = new Mario(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mario);
				}else if(currentPixel == surfaceBlock){
					BaseStaticEntity SurfaceBlock = new SurfaceBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(SurfaceBlock);
				}else if(currentPixel == breakBlock){
					BaseStaticEntity BreakBlock = new BreakBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(BreakBlock);
				}else if(currentPixel == misteryBlock){
					BaseStaticEntity MisteryBlock = new MisteryBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(MisteryBlock);
				}else if(currentPixel == flowerBlock){
					BaseDynamicEntity FlowerBlock = new FlowerBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(FlowerBlock);
				}else if(currentPixel == mushroom){
					BaseDynamicEntity Mushroom = new Mushroom(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Mushroom);	
				}else if(currentPixel == fireFlower){
					BaseDynamicEntity FireFlower = new FireFlower(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(FireFlower);
				}else if(currentPixel == goomba){
					BaseDynamicEntity Goomba = new Goomba(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addEnemy(Goomba);
				}else if(currentPixel == koopaTroopa){
			        BaseDynamicEntity KoopaTroopa = new KoopaTroopa(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
				    mapInCreation.addEnemy(KoopaTroopa);
			    }else if(handler.getMario().activatedFlower) {
					//BaseDynamicEntity FireFlower = new FireFlower(flower,yPos,pixelMultiplier,pixelMultiplier,handler);
					//
				}
			}

		}
		if(mapDone) {
			Images.makeMap(50, pixelMultiplier, mapImage.getWidth(), 100, mapInCreation, handler);
			for(int i = 96; i < 101; i++) {
				mapInCreation.addBlock(new BreakBlock(49*pixelMultiplier, i*pixelMultiplier,48,48,handler));
				mapInCreation.addBlock(new BreakBlock(54*pixelMultiplier, i*pixelMultiplier,48,48,handler));
			}
		}
		return mapInCreation;
	}

}
