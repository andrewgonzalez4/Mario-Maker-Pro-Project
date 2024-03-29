package Game.GameStates;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Display.DisplayMultiScreen;
import Display.DisplayScreen;
import Display.UI.UIAnimationButton;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Display.UI.UIStringButton;
import Game.World.MapBuilder;
import Input.KeyManager;
import Input.MouseManager;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuState extends State {

	public UIManager uiManager;
	private int background;
	private String mode= "Menu";

	private DisplayScreen display;
	private DisplayMultiScreen display2;
	private int[] str={83,117,98,32,116,111,32,80,101,119,100,115};
	private String str2="";

	private BufferStrategy bs;
	private BufferStrategy bs2;
	private Graphics g;
	private Graphics g3;
	private UIAnimationButton but;
	private boolean creatingMap=false;
	public int GridWidthPixelCount,GridHeightPixelCount,DiplayHeight,DisplayWidth;
	public int GridPixelsize;
	int colorSelected = MapBuilder.boundBlock;
	Color[][] blocks;
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private boolean clicked = true;

	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);
		background = new Random().nextInt(9);

		DisplayWidth=(handler.getWidth())+(handler.getWidth()/2);
		DiplayHeight = handler.getHeight();
		GridPixelsize = 20;
		GridHeightPixelCount = DiplayHeight/GridPixelsize;
		GridWidthPixelCount = DisplayWidth/GridPixelsize;
		blocks = new Color[GridWidthPixelCount][GridHeightPixelCount];
		keyManager = handler.getGame().keyManager;
		mouseManager = new MouseManager();
		for (int i:str) { str2+=(char)i;}
		this.but = new UIAnimationButton(handler.getWidth() - (handler.getWidth()/ 8),(handler.getHeight()/0b1100),32, 32 , Images.item, () -> {
			if(but.getdraw() && !handler.isInMap()) {handler.setMap(handler.getGame().getMap());
			handler.getGame().getMusicHandler().pauseBackground();
			handler.getGame().getMusicHandler().play("Megalovania");
			State.setState(handler.getGame().gameState);}}, this.handler);
		uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/2+(handler.getHeight()/8), 128, 64, Images.butstart, () -> {
			if(!handler.isInMap()) {
				mode = "Select";
			}
		}));
	}

	@Override
	public void tick() {
		if(!creatingMap) {
			handler.getMouseManager().setUimanager(uiManager);
			uiManager.tick();
			if (mode.equals("Select")) {
				mode = "Selecting";
				uiManager = new UIManager(handler);
				handler.getMouseManager().setUimanager(uiManager);


				//Single Player
				uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 250, (handler.getHeight() / 2) + (handler.getHeight() / 10), 128, 64, "Single Player", () -> {
					if(!handler.isInMap()) {
						handler.setSinglePlayer(true);
						mode = "FirstMenu";
						uiManager = new UIManager(handler);
						handler.getMouseManager().setUimanager(uiManager);

						if(handler.isSinglePlayer() == true) {



							//New Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 250, (handler.getHeight() / 2) + (handler.getHeight() / 10) - (85), 128, 64, "New Map", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									initNew("New Map Creator", handler);
								}
							}, handler,Color.BLACK));


							//testMap1
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 + 150, (handler.getHeight() / 2) + (handler.getHeight() / 10) - (85), 128, 64, "Map 1", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.testMap, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));

							//testmap2
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 250, (handler.getHeight() / 2) + (handler.getHeight() / 10), 128, 64, "Map 2", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.testMaptwo, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));

							//other
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 + 150, (handler.getHeight() / 2) + (handler.getHeight() / 10), 128, 64, "Other", () -> {
								if(!handler.isInMap()){
									mode = "Menu";
									JFileChooser chooser = new JFileChooser("/maps");
									FileNameExtensionFilter filter = new FileNameExtensionFilter(
											"JPG, & PNG Images", "jpg", "png");
									chooser.setFileFilter(filter);
									int returnVal = chooser.showOpenDialog(null);
									if (returnVal == JFileChooser.APPROVE_OPTION) {
										System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
										try {
											handler.setMap(MapBuilder.createMap(ImageIO.read(chooser.getSelectedFile()), handler));
											State.setState(handler.getGame().gameState);
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
							}, handler,Color.BLACK));
							uiManager.addObjects(this.but);


							//Hello World Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 80, (handler.getHeight() / 2) + (handler.getHeight() / 10) - (85), 128, 64, "<Hello World>", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.challengeMap, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));
							
							//SkyFall Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 70, (handler.getHeight() / 2) + (handler.getHeight() / 5) - (70), 128, 64, "SkyFallMap", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.SkyFall, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));

						}

					}
				}, handler,Color.black));
				uiManager.addObjects(this.but);


				uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 + 110, (handler.getHeight() / 2) + (handler.getHeight() / 10), 128, 64, "Multi Player", () -> {
					if(!handler.isInMap()) {
						handler.setMultiPlayer(true);
						mode = "SecondMenu";
						uiManager = new UIManager(handler);
						handler.getMouseManager().setUimanager(uiManager);

						if(handler.isMultiPlayer() == true) {

							handler.getGame().display2.getFrame().setVisible(true);
							handler.getGame().display.getFrame().setLocation(50 + 80, 125);

							

							//New Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 50, (handler.getHeight() / 2) + (handler.getHeight() / 10) - (85), 128, 64, "New Map", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									initNew("New Map Creator", handler);

								}
							}, handler,Color.BLACK));

							//other
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 40, (handler.getHeight() / 2) + (handler.getHeight() / 3) - (110), 128, 64, "Other", () -> {
								if(!handler.isInMap()){
									mode = "Menu";
									JFileChooser chooser = new JFileChooser("/maps");
									FileNameExtensionFilter filter = new FileNameExtensionFilter(
											"JPG, & PNG Images", "jpg", "png");
									chooser.setFileFilter(filter);
									int returnVal = chooser.showOpenDialog(null);
									if (returnVal == JFileChooser.APPROVE_OPTION) {
										System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
										try {
											handler.setMap(MapBuilder.createMap(ImageIO.read(chooser.getSelectedFile()), handler));
											State.setState(handler.getGame().gameState);
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
							}, handler,Color.BLACK));
							uiManager.addObjects(this.but);


							//Hello World Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 70, (handler.getHeight() / 2) + (handler.getHeight() / 5) - (110), 128, 64, "<Hello World>", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.challengeMap, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));
							
							//SkyFall Map
							uiManager.addObjects(new UIStringButton(handler.getWidth() / 2 - 70, (handler.getHeight() / 2) + (handler.getHeight() / 3) - (150), 128, 64, "SkyFallMap", () -> {
								if(!handler.isInMap()) {
									mode = "Menu";
									handler.setMap(MapBuilder.createMap(Images.SkyFall, handler));
									State.setState(handler.getGame().gameState);
								}
							}, handler,Color.BLACK));

						}

					}
				}, handler,Color.black));
				uiManager.addObjects(this.but);


			}
			if (mode.equals("Selecting") && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && (!handler.isInMap())) {
				mode = "Menu";
				uiManager = new UIManager(handler);
				handler.getMouseManager().setUimanager(uiManager);
				uiManager.addObjects(new UIImageButton(handler.getWidth() / 2 - 64, handler.getHeight() / 2 + (handler.getHeight() / 8), 128, 64, Images.butstart, () -> {
					mode = "Select";
				}));
			}
		}else{
			handler.getGame().mouseManager=null;
			tickNewScreen();
			if(clicked){
				clicked = mouseManager.isLeftPressed();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(!creatingMap) {
			g.setColor(Color.GREEN);
			g.drawImage(Images.backgrounds[background], 0, 0, handler.getWidth(), handler.getHeight(), null);
			g.drawImage(Images.title, 0, 0, handler.getWidth(), handler.getHeight(), null);
			uiManager.Render(g);
		}else{
			renderNewScreen();
		}
	}

	private void initNew(String title,Handler handler){
		display = new DisplayScreen(title + "              (H for Mapping)", DisplayWidth, DiplayHeight);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		creatingMap = true;
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0,0,0), new Point(0, 0), "cursor1");
		display.getCanvas().setCursor(c);
	}

	private void tickNewScreen(){
		//for the tin take each value and divide by 255.
		//Ex for a red tint you wan the RGB : 255,0,0 so the tint is 1,0,0
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_0)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,1,1,1), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = Color.WHITE.getRGB();
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,1,0,0), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.mario;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0,0,1), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.breakBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,1,1,0), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.misteryBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,1,0.5f,0), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.surfaceBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0,0,0), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.boundBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_6)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,069f,0,1), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.mushroom;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_7)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.6549f,0.05882f,0.003921f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.goomba;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_8)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.8392f,0.4235f,0.2f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.fireFlower;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_9)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.27450f,0.858827f,0.70196f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.flowerBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.84313f,0.858827f,0.81568f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.cloudBlock;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.86666f,0.34509f,0.52941f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.koopaTroopa;
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)){
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,1,0.98823f,0.72941f), new Point(0, 0), "cursor1");
			display.getCanvas().setCursor(c);
			colorSelected = MapBuilder.coin;
		}

		if(handler.isMultiPlayer() == true) {

			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)){
				Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(Images.tint(Images.Cursor,0.22352f,0.60784f,0.28627f), new Point(0, 0), "cursor1");
				display.getCanvas().setCursor(c);
				colorSelected = MapBuilder.luigi;
			}
		}


		if(mouseManager.isLeftPressed() && !clicked){
			int posX =mouseManager.getMouseX()/GridPixelsize;
			int posY =mouseManager.getMouseY()/GridPixelsize;
			System.out.println(posX + " , " +posY);
			blocks[posX][posY]=new Color(colorSelected);
			clicked=true;
		}

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			for (int i = 0; i < GridWidthPixelCount; i++) {
				for (int j = 0; j < GridHeightPixelCount; j++) {
					if(blocks[i][j]!=null && blocks[i][j].equals(new Color(MapBuilder.mario)) && blocks[i][j+1]!=null&& !blocks[i][j+1].equals(new Color(MapBuilder.mario))){
						handler.setMap(MapBuilder.createMap(createImage(GridWidthPixelCount,GridHeightPixelCount,blocks,JOptionPane.showInputDialog("Enter file name: ","Mario Heaven")), handler));
						State.setState(handler.getGame().gameState);
						creatingMap=false;
						display.getFrame().setVisible(false);
						display.getFrame().dispose();
						handler.getGame().mouseManager=handler.getGame().initialmouseManager;
						return;
					}
				}
			}
			JOptionPane.showMessageDialog(display.getFrame(), "You cant have a map without at least a Mario and a floor right under him. (1 for Mario)");
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)){

			if(handler.isMultiPlayer() == true) {


				JOptionPane.showMessageDialog(display.getFrame(), "Number key <-> Color Mapping: \n" +
						"0 -> Erase \n" +
						"1 -> Mario (Red)\n" +
						"2 -> Break Block (Blue)\n" +
						"3 -> Mystery Block (Yellow)\n" +
						"4 -> Surface Block (Orange)\n" +
						"5 -> Bounds Block (Black)\n" +
						"6 -> Mushroom (Purple)\n" +
						"7 -> Goomba (Brown)\n" + 
						"8 -> Fire Flower (Darker Orange)\n" + 
						"9 -> Fire Flower Block (Mint Green)\n" +
						"- -> Cloud Block (Light Grey)\n" +
						"K -> Koopa Troopa (Pink)\n"+
						"C -> Coin (Pale Yellow)\n"+
						"L -> Luigi (Green)\n");
			}

			else {

				JOptionPane.showMessageDialog(display.getFrame(), "Number key <-> Color Mapping: \n" +
						"0 -> Erase \n" +
						"1 -> Mario (Red)\n" +
						"2 -> Break Block (Blue)\n" +
						"3 -> Mystery Block (Yellow)\n" +
						"4 -> Surface Block (Orange)\n" +
						"5 -> Bounds Block (Black)\n" +
						"6 -> Mushroom (Purple)\n" +
						"7 -> Goomba (Brown)\n" + 
						"8 -> Fire Flower (Darker Orange)\n" + 
						"9 -> Fire Flower Block (Mint Green)\n" +
						"- -> Cloud Block (Light Grey)\n" +
						"K -> Koopa Troopa (Pink)\n" + 
						"C -> Coin (Pale Yellow)\n");
			}
		}
	}
	public UIAnimationButton getBut() {
		return this.but;
	}

	private void renderNewScreen(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0,  handler.getWidth()+handler.getWidth()/2, handler.getHeight());

		//Draw Here!
		Graphics2D g2 = (Graphics2D) g.create();

		g.setColor(Color.white);
		g.fillRect(0,0,handler.getWidth()+(handler.getWidth()/2),handler.getHeight());

		for (int i = 0; i <= DisplayWidth; i = i + GridPixelsize) {
			g.setColor(Color.BLACK);
			g.drawLine(i,0,i,DiplayHeight);
		}
		for (int i = 0; i <= DiplayHeight; i = i + GridPixelsize) {
			g.setColor(Color.BLACK);
			g.drawLine(0, i, DisplayWidth , i);
		}
		for (int i = 0; i < GridWidthPixelCount; i++) {
			for (int j = 0; j < GridHeightPixelCount; j++) {
				if(blocks[i][j]!=null && !blocks[i][j].equals(Color.WHITE)){
					g.setColor((blocks[i][j]));
					g.fillRect((i*GridPixelsize),(j*GridPixelsize),GridPixelsize,GridPixelsize);
				}
			}
		}

		//End Drawing!
		bs.show();
		g.dispose();
	}

	public BufferedImage createImage(int width,int height,Color[][] blocks,String name){

		// Create buffered image object
		BufferedImage img = null;
		MapBuilder.mapDone=false;
		if(name.equals(str2)) MapBuilder.mapDone = true;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// file object
		File f = null;

		// create random values pixel by pixel
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if(blocks[x][y]!=null) {
					img.setRGB(x, y, blocks[x][y].getRGB());
				}else{
					img.setRGB(x, y, Color.WHITE.getRGB());

				}
			}
		}

		// write image
		try
		{
			String path = getClass().getClassLoader().getResource(".").getPath();
			String path2 = path.substring(0,path.indexOf("/out/"))+"/res/maps/"+name+".png";
			path2 = path2.replaceAll("%20", " ");

			f = new File(path2);
			System.out.println("File saved in: "+path2);
			ImageIO.write(img, "png", f);
		}
		catch(IOException e)
		{
			System.out.println("Error: " + e);
		}
		return img;
	}

}
