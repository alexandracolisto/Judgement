/****************************************************************************************************
 * @author Travis R. Dewitt; edited by Team A2
 * @version 0.53
 * Date: June 14, 2015 (Travis)
 * Date: December 2, 2015 (Team A2)
 * 
 * Title: Judgement(The Game)
 * Description: This class extends 'Game.java' in order to run a 2D game with specificly defined
 *  sprites, animatons, and actions.
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 ****************************************************************************************************/

package axohEngine2;

//Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.ImageEntity;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;
import axohEngine2.project.InGameMenu;
import axohEngine2.project.MapDatabase;
import axohEngine2.project.OPTION;
import axohEngine2.project.STATE;
import axohEngine2.project.TYPE;
import axohEngine2.project.TitleMenu;
import axohEngine2.sound.PlaySound;

//Start class by also extending the 'Game.java' engine interface
public class Judgement extends Game {
	//For serializing (The saving system)
	private static final long serialVersionUID = 1L;
	/****************** Variables **********************/
	//--------- Screen ---------
	//SCREENWIDTH - Game window width
	//SCREENHEIGHT - Game window height
	//CENTERX/CENTERY - Center of the game window's x/y
	//gets the current screen's resolution
	static Toolkit toolkit =  Toolkit.getDefaultToolkit ();//needed to get screen's current resolution
	static Dimension currentDimensions = toolkit.getScreenSize();
	static private int SCREENHEIGHT = currentDimensions.height;
	private static int SCREENWIDTH = currentDimensions.width;
	static int CENTERX = SCREENWIDTH / 2;
	static int CENTERY = SCREENHEIGHT / 2;

	//--------- Miscelaneous ---------
	//booleans - A way of detecting a pushed key in game
	//random - Use this to generate a random number
	//state - Game states used to show specific info ie. pause/running
	//option - In game common choices at given times
	//Fonts - Variouse font sizes in the Arial style for different in game text
	boolean keyLeft, keyRight, keyUp, keyDown, keyInventory, keyAction, keyBack, keyEnter, keySpace, keyEscape;
	STATE state; 
	OPTION option;
	private Font simple = new Font("Arial", Font.PLAIN, 72);
	private Font bold = new Font("Arial", Font.BOLD, 72);
	private Font bigBold = new Font("Arial", Font.BOLD, 96);
	private Font small = new Font("Arial", Font.BOLD, 12);

	//--------- Player and scale ---------
	//scale - All in game art is 16 x 16 pixels, the scale is the multiplier to enlarge the art and give it the pixelated look
	//mapX/mapY - Location of the camera on the map
	//playerX/playerY - Location of the player on the map
	//startPosX/startPosY - Starting position of the player in the map
	//playerSpeed - How many pixels the player moves in a direction each update when told to
	//shiaTile - Tile number where Shia is going to be put in level 3
	private int scale;
	private int mapX;
	private int mapY;
	private int playerX;
	private int playerY;
	private int playerSpeed;
	private int shiaTile = 180;

	//----------- Map and input --------
	//currentMap - The currently displayed map the player can explore
	//currentOverlay - The current overlay which usually contains houses, trees, pots, etc.
	//mapBase - The database which contains all variables which pertain to specific maps(NPCs, monsters, chests...)
	//inputWait - How long the system waits for after an action is done on the keyboard
	//confirmUse - After some decisions are made, a second question pops up, true equals continue action from before.
	private Map currentMap;
	private Map currentOverlay;
	private MapDatabase mapBase;
	private int inputWait = 5;
	private boolean confirmUse = false;

	//----------- Menus ----------------
	//inX/inY - In Game Menu starting location for default choice highlight
	//inLocation - Current choice in the in game menu represented by a number, 0 is the top
	//sectionLoc - Current position the player could choose after the first choice has been made in the in game menu(Items -> potion), 0 is the top.
	//titleX, titleY, titleX2, titleY2 - Positions for specific moveable sprites at the title screen (arrow/highlight).
	//titleLocation - Current position the player is choosing in the title screen(File 1, 2, 3) 0 is top
	//currentFile - Name of the currently loaded file
	//wasSaving/wait/waitOn - Various waiting variables to give the player time to react to whats happening on screen
	private int inX = (int)(SCREENWIDTH/11.1), inY = (int)(SCREENHEIGHT/3.2); //square position for inventory
	private int inLocation;
	private int sectionLoc;
	private int titleX = (int)(SCREENWIDTH/1.55), titleY = (int)(SCREENHEIGHT/2.15);  //530, 610 //Main Menu Arrows Positions
	private int titleX2 = (int)(SCREENWIDTH/4.3), titleY2 = (int)(SCREENHEIGHT/1.95); //340, titleY2 = 310; //340 310
	private int titleLocation;
	private String currentFile;
	private boolean wasSaving = false;
	private int wait;
	private boolean waitOn = false;
	private boolean npcsNotDead = false; //to know if all the npcs are dead. If not, "Kill NPCs" message will be displayed
	private int currentLevel;//current Level that you're on
	private boolean gotKey = false;//to know if you actually got the key from level 2 (yes or no)
	private boolean displayFindKey = false;//if you dont have the key, display String in the string saying to find it

	//----------- Game  -----------------
	//SpriteSheets (To be split in to multiple smaller sprites)
	SpriteSheet extras1;
	SpriteSheet mainCharacter,shiaCharacter;

	//ImageEntitys (Basic pictures)
	ImageEntity inGameMenu;
	ImageEntity titleMenu;
	ImageEntity titleMenu1;
	ImageEntity titleMenu2;
	ImageEntity tutorial;
	ImageEntity credits;
	ImageEntity storyLine, intoLevel2, intoLevel3;
	ImageEntity shia_clapping;
	ImageEntity foundKey;
	ImageEntity youLose;

	//Menu classes
	TitleMenu title;
	InGameMenu inMenu;

	//Animated sprites
	AnimatedSprite titleArrow;

	//Player and NPCs
	Mob playerMob,shia;
	ArrayList<Mob> randomNPCs;

	//runs parallel with randomNPCs array list. holds the tile number for that NPC
	//needs to add more when putting more NPCs in.
	ArrayList<Integer> npcTileNumber;
	//runs parallel with randomNPCs array list. holds total damage taken by each NPC (parallel)
	//to compute the health pie
	//needs to add more when putting more NPCs in.
	ArrayList<Integer> damageInflicted;

	private PlaySound swordClip;//hit sound effect
	private boolean toGame = false;//to know if tutorial is hit from title menu or going to the game
	private int shiaHealth = 150;//shia's health
	String shiaDirection = ""; //shia's direction
	private boolean displayFoundKeyImage = false;//displays the image that you found the key

	//JFrame passed in from main class to display the end of the game GIF.
	private JFrame frame;

	/*********************************************************************** 
	 * Constructor
	 * 
	 * Set up the super class Game and set the window to appear
	 **********************************************************************/
	public Judgement() {
		//40 is the frame rate
		super(40, SCREENWIDTH, SCREENHEIGHT);
	}

	/****************************************************************************
	 * Inherited Method
	 * This method is called only once by the 'Game.java' class, for startup
	 * Initialize all non-int variables here
	 *****************************************************************************/
	void gameStartUp() {
		/****************************************************************
		 * The "camera" is the mapX and mapY variables. These variables 
		 * can be changed in order to move the map around, simulating the
		 * camera. The player is moved around ONLY when at an edge of a map,
		 * otherwise it simply stays at the center of the screen as the "camera"
		 * is moved around.
		 ****************************************************************/
		//****Initialize Misc Variables
		state = STATE.TITLE;
		option = OPTION.NONE;
		mapX = -400;//camera
		mapY = -400;//camera
		scale = 4;
		playerSpeed = 20;

		//****Initialize spriteSheets*********************************************************************
		extras1 = new SpriteSheet("/textures/extras/extras1.png", 8, 2, 32, scale);
		mainCharacter = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);
		shiaCharacter = new SpriteSheet("/textures/characters/shiaGrid.png", 8, 8, 32, scale);

		//****Initialize and setup AnimatedSprites*********************************************************
		titleArrow = new AnimatedSprite(this, graphics(), extras1, 0, "arrow");
		titleArrow.loadAnim(4, 10);
		sprites().add(titleArrow);

		//****Initialize and setup image entities**********************************************************
		inGameMenu = new ImageEntity(this);
		titleMenu = new ImageEntity(this);
		titleMenu1 = new ImageEntity(this);
		titleMenu2 = new ImageEntity(this);
		tutorial = new ImageEntity(this);
		storyLine = new ImageEntity(this);
		intoLevel2 = new ImageEntity(this);
		intoLevel3 = new ImageEntity(this);
		shia_clapping = new ImageEntity(this);
		credits = new ImageEntity(this);
		foundKey = new ImageEntity(this);
		youLose = new ImageEntity(this);
		inGameMenu.load("/menus/ingamemenu.png");
		titleMenu.load("/menus/titlemenu1.png");
		titleMenu1.load("/menus/titlemenu.png");
		titleMenu2.load("/menus/titlemenu2.png");
		tutorial.load("/menus/tutorial.png");
		storyLine.load("/menus/storyline.png");
		intoLevel2.load("/menus/level2.png");
		intoLevel3.load("/menus/level3.png");
		shia_clapping.load("/textures/extras/shia_end.gif");
		credits.load("/menus/credits.png");
		foundKey.load("/textures/extras/foundKey.png");
		youLose.load("/textures/extras/youLose.png");

		//*****Initialize Menus***************************************************************************
		title = new TitleMenu(titleMenu, titleMenu2, titleMenu1,titleArrow, SCREENWIDTH, SCREENHEIGHT, simple, bold, bigBold);
		inMenu = new InGameMenu(inGameMenu, SCREENWIDTH, SCREENHEIGHT);

		//****Initialize and setup Mobs*********************************************************************
		playerMob = new Mob(this, graphics(), mainCharacter, 40, TYPE.PLAYER, "mainC", true);
		playerMob.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
		playerMob.setMoveAnim(32, 48, 40, 56, 3, 8);
		playerMob.addAttack("sword", 0, 5);
		playerMob.getAttack("sword").addMovingAnim(17, 25, 9, 1, 3, 8);
		playerMob.getAttack("sword").addAttackAnim(20, 28, 12, 4, 3, 6);
		playerMob.getAttack("sword").addInOutAnim(16, 24, 8, 0, 1, 10);
		playerMob.setCurrentAttack("sword"); //Starting attack
		playerMob.setDirection("up");//set up direction
		
		//if changed, also remember to change this number '60' in the drawPlayersPie method here in this class
		playerMob.setHealth(60); //If you change the starting max health, dont forget to change it in inGameMenu.java max health also

		//ArrayList initializations
		npcTileNumber = new ArrayList<Integer>();
		damageInflicted = new ArrayList<Integer>();

		//parallel with randomNPC, give each NPC its tile number
		//npcTilenumber should be equal in size as randomNPCs and damageInflicted
		//these are commented out because before I had 5 NPCs, but for testing they were too much
		//so i left 2. If you want more NPCs you need to increase the for-loop in MapDatabase at the
		//end of constructor and then come here to add those NPCs to their tile numbers.

		//npcTileNumber.add(98);
		//npcTileNumber.add(190);
		//npcTileNumber.add(498);
		npcTileNumber.add(710);
		npcTileNumber.add(978);

		sprites().add(playerMob); //adds playerMob to the sprites to animate

		//*****Initialize and setup first Map******************************************************************
		mapBase = new MapDatabase(this, graphics(), scale, damageInflicted);
		//get the randomNPCs from mapBase
		randomNPCs = mapBase.getRandomNPCs();

		//add NPCs to the sprites to animate or else they wont move
		for(int i = 0; i < randomNPCs.size(); i++) sprites().add(randomNPCs.get(i));

		//Get Map from the database
		for(int i = 0; i < mapBase.maps.length; i++){
			if(mapBase.getMap(i) == null) continue;
			if(mapBase.getMap(i).mapName() == "city") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "cityO") currentOverlay = mapBase.getMap(i);
		}
		//Add the tiles from the map to be updated each system cycle
		for(int i = 0; i < currentMap.getHeight() * currentMap.getHeight(); i++){
			addTile(currentMap.accessTile(i));
			addTile(currentOverlay.accessTile(i));
			if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
			if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			currentMap.accessTile(i).getEntity().setX(-300);
			currentOverlay.accessTile(i).getEntity().setX(-300);
		}		

		//Add the mobs to their tile home
		//this is why the array list run parallel
		if(npcTileNumber.size() == randomNPCs.size()){
			for(int i = 0; i < npcTileNumber.size(); i++){
				//add all the NPCs with their different tile locations
				mapBase.getCityO().accessTile(npcTileNumber.get(i)).addMob(randomNPCs.get(i));
			}
		}

		currentLevel = 1; //when starting, current level = 1
		requestFocus(); //Make sure the game is focused on
		start(); //Start the game loop
	}

	/**************************************************************************** 
	 * Inherited Method
	 * Method that updates with the default 'Game.java' loop method
	 * Add game specific elements that need updating here
	 *****************************************************************************/
	void gameTimedUpdate() {
		checkInput(); //Check for user input
		//Update certain specifics based on certain game states
		if(state == STATE.TITLE) title.update(option, titleLocation); //Title Menu update
		if(state == STATE.INGAMEMENU) inMenu.update(option, sectionLoc, playerMob.health()); //In Game Menu update
		//updateData(currentMap, currentOverlay, playerX, playerY); //Update the current file data for saving later
		//System.out.println(frameRate()); //Print the current framerate to the console

		if(waitOn) wait--;
	}

	/**
	 * Inherited Method
	 * Obtain the 'graphics' passed down by the super class 'Game.java' and render objects on the screen
	 */
	void gameRefreshScreen() {		
		/*********************************************************************
		 * Rendering images uses the java class Graphics2D
		 * Each frame the screen needs to be cleared and an image is setup as a back buffer which is brought 
		 * to the front as a full image at the time it is needed. This way the screen is NOT rendered pixel by 
		 * pixel in front of the user, which would have made a strange lag effect.
		 * 
		 * 'graphics' objects have parameters that can be changed which effect what it renders, two are font and color
		 **********************************************************************/
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT); 
		g2d.setFont(simple);

		//GUI rendering for when a specific state is set, only specific groups of data is drawn at specific times
		if(state == STATE.GAME) {
			//Render the map, the player, any NPCs or Monsters and the player health or status
			currentMap.render(this, g2d, mapX, mapY);
			currentOverlay.render(this, g2d, mapX, mapY);
			playerMob.renderMob(CENTERX - playerX, CENTERY - playerY);

			//if ALL NPCs are NOT dead, draw the "Kill NPCs" string in the screen
			//TODO: make it relative to the screen's resolution using toolkit
			if(npcsNotDead){
				g2d.setColor(Color.RED);
				g2d.drawString("Kill NPCs!",950, 60);
				g2d.setColor(Color.GREEN);
				requestFocus();
			}

			//*****************************draw the health pie for npcs, player, shia******************************
			this.drawPlayersPie(g2d);//draws the player's health pie

			if(!randomNPCs.isEmpty()){	//if not empty
				for(int i = 0; i < randomNPCs.size(); i++){
					if(randomNPCs.get(i).health() <= 30){//if you hit them
						double ratio = damageInflicted.get(i)/30.0;//ratio of health inflicted
						double pieRatio = 365*ratio;//get the ratio of the pie based on above ratio
						double ceilNum = Math.ceil(pieRatio);//ceil that number
						int num = 365 - (int)ceilNum;//convert to int and subtract by 365
						this.drawNPCHealth(npcTileNumber.get(i), g2d, num);//draws the health for NPCs
					}
				}
			}

			//draw health bar for shia (level 3)
			if(currentLevel == 3 && shia.health() <= shiaHealth && shia.health() > 0){
				double ratio = (shiaHealth-shia.health())/(double)shiaHealth;//ratio of health inflicted
				double pieRatio = 365*ratio;//get the ratio of the pie based on above ratio
				double ceilNum = Math.ceil(pieRatio);//ceil that number
				int num = 365 - (int)ceilNum;//convert to int and subtract by 365
				this.drawNPCHealth(shiaTile, g2d, num);//draws the health for NPCs
			}	
		}

		//******************************************************************************************************

		if(state == STATE.INGAMEMENU){
			//Render the in game menu and specific text
			inMenu.render(this, g2d, inX, inY);
			g2d.setColor(Color.red);
			if(confirmUse) g2d.drawString("Use this?", CENTERX, CENTERY);
		}

		if(state == STATE.TITLE) {
			//Render the title screen
			title.render(this, g2d, titleX, titleY, titleX2, titleY2);

		}

		//if you're in the title screen and just created a new game,
		//you will be presented with this story line before you can play
		if(state == STATE.TITLE && option == OPTION.STORYLINE){
			//draw the image
			g2d.drawImage(storyLine.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			//if enter is pressed, from story line, show the tutorial to teach them how to play
			if(keyEnter){
				option = OPTION.TUTORIAL;
				toGame = true;//to differentiate from the tutorial on title menu and the tutorial from new game
				keyEnter = false;
			}
		}

		//if you killed all NPCs and entered the castle, do this
		if(state == STATE.GAME && option == OPTION.LEVEL2){
			//draw the narration of what is going to happen
			g2d.drawImage(intoLevel2.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			if(keyEnter){
				option = OPTION.NONE;//get out of this option to none
				keyEnter = false;
				currentLevel = 2;//update current level
				this.levelUp("level2_0","level2_1");//level up to level 2
				//TODO: might want to change this relative to screen or something
				mapY -= 1000; //position the character in a new place
				mapX -= 200; //same
			}
		}
		
		//draw image that you got the key if you opened the chest
		if(displayFoundKeyImage){
			//draw the image
			g2d.drawImage(foundKey.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			if(keyEnter){
				displayFoundKeyImage = false;//false the variable so this code never gets run again
				keyEnter = false;
			}
		}
		//if player dies, end game + also delete that file 
		if(playerMob.health() <= 0){
			//draw image saying you lose
			g2d.drawImage(youLose.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			if(keyEnter){
				//delete the file
				Path path  = Paths.get("C:/gamedata/saves/" + currentFile);
				this.deleteSavedFile(path);
				keyEnter = false;
				System.exit(0);//exit
			}
		}
		
		//from level 2 to level 3
		if(state == STATE.GAME && option == OPTION.LEVEL3){
			//draw image narrating what is going to happen
			g2d.drawImage(intoLevel3.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			if(keyEnter){
				option = OPTION.NONE;
				keyEnter = false;
				currentLevel = 3;
				this.levelUp("level2_0","level3");//level up to level 3
			}
		}

		//if you killed shia in level 3
		if(state == STATE.ENDGAME){
			JLabel imageLabel = new JLabel();//new JLabel to put the GIF in
			//get the GIF
			ImageIcon ii = new ImageIcon(getClass().getResource("/textures/extras/shia_end.gif"));
			imageLabel.setIcon(ii);//add the GIF to the JLabel

			this.add(imageLabel);//add the label to the panel
			this.repaint();//repaint panel
			frame.getContentPane().add(this);//add panel to the frame
			frame.pack();//pack
			frame.setVisible(true);
			keyEnter = false;
			long startTime = System.currentTimeMillis();//start a timer 

			Path path  = Paths.get("C:/gamedata/saves/" + currentFile);
			this.deleteSavedFile(path);//delete the file data

			while(true){//runs for ever, but will end game in 7 seconds
				long finishTime = Math.abs(startTime-System.currentTimeMillis());
				if(finishTime >= 7000){
					System.exit(0);//exit after 7 seconds of the GIF playing
				}
			}
		}

		//credits page in the title menu
		if(state == STATE.TITLE && option == OPTION.CREDITS){
			g2d.drawImage(credits.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);
		}
		//tutorial page in the title menu (not from new game)
		if(state == STATE.TITLE && option == OPTION.TUTORIAL){
			g2d.drawImage(tutorial.getImage(), 0, 0, SCREENWIDTH, SCREENHEIGHT+1, this);

			if(keyEnter && toGame){
				option = OPTION.NONE;
				state = STATE.GAME;
				keyEnter = false;
			}
		}

		//Render save time specific writing
		//TODO: written by creator. Needs to be fixed relatively to the screen
		if(option == OPTION.SAVE){
			drawString(g2d, "Are you sure you\n      would like to save?", 660, 400);
		}
		if(wasSaving && wait > 0) {
			save.saveState(currentFile, playerMob.health(), currentMap.mapName(), currentOverlay.mapName(), "up", currentLevel);
			g2d.drawString("Game Saved!", 700, 500);
		}
		//displays "find the key first" for level 2 if you try to open the door without the key
		if(displayFindKey){
			g2d.setColor(Color.RED);
			g2d.drawString("Find the key!",850, 60);
			g2d.setColor(Color.BLACK);
		}
	}

	/*******************************************************************
	 * The next four methods are inherited
	 * Currently these methods are not being used, but they have
	 * been set up to go off at specific times in a game as events.
	 * Actions that need to be done during these times can be added here.
	 ******************************************************************/
	void gameShutDown() {		
	}

	void spriteUpdate(AnimatedSprite sprite) {		
	}

	void spriteDraw(AnimatedSprite sprite) {		
	}

	void spriteDying(AnimatedSprite sprite) {		
	}

	/*************************************************************************
	 * @param AnimatedSprite
	 * @param AnimatedSprite
	 * @param int
	 * @param int
	 * 
	 * Inherited Method
	 * Handling for when a SPRITE contacts a SPRITE
	 * 
	 * hitDir is the hit found when colliding on a specific bounding box on spr1 and hitDir2
	 * is the same thing applied to spr2
	 * hitDir is short for hit direction which can give the data needed to move the colliding sprites
	 * hitDir is a number between and including 0 and 3, these assignments are taken care of in 'Game.java'.
	 * What hitDir is actually referring to is the specific hit box that is on a multi-box sprite.
	 *****************************************************************************/
	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2, int hitDir, int hitDir2) {
		//Get the smallest possible overlap between the two problem sprites
		double leftOverlap = (spr1.getBoundX(hitDir) + spr1.getBoundSize() - spr2.getBoundX(hitDir2));
		double rightOverlap = (spr2.getBoundX(hitDir2) + spr2.getBoundSize() - spr1.getBoundX(hitDir));
		double topOverlap = (spr1.getBoundY(hitDir) + spr1.getBoundSize() - spr2.getBoundY(hitDir2));
		double botOverlap = (spr2.getBoundY(hitDir2) + spr2.getBoundSize() - spr1.getBoundY(hitDir));
		double smallestOverlap = Double.MAX_VALUE; 
		double shiftX = 0;
		double shiftY = 0;

		if(leftOverlap < smallestOverlap) { //Left
			smallestOverlap = leftOverlap;
			shiftX -= leftOverlap; 
			shiftY = 0;
		}
		if(rightOverlap < smallestOverlap){ //right
			smallestOverlap = rightOverlap;
			shiftX = rightOverlap;
			shiftY = 0;
		}
		if(topOverlap < smallestOverlap){ //up
			smallestOverlap = topOverlap;
			shiftX = 0;
			shiftY -= topOverlap;
		}
		if(botOverlap < smallestOverlap){ //down
			smallestOverlap = botOverlap;
			shiftX = 0;
			shiftY = botOverlap;
		}

		//Handling very specific collisions
		if(spr1.spriteType() == TYPE.PLAYER && state == STATE.GAME){
			if(spr2 instanceof Mob) ((Mob) spr2).stop();

			//This piece of code is commented out because I still need the capability of getting a tile from an xand y position
			/*if(((Mob) spr1).attacking() && currentOverlay.getFrontTile((Mob) spr1, playerX, playerY, CENTERX, CENTERY).getBounds().intersects(spr2.getBounds())){
				((Mob) spr2).takeDamage(25);
				//TODO: inside of take damage should be a number dependant on the current weapon equipped, change later
			}*/

			//Handle simple push back collision
			if(playerX != 0) playerX -= (shiftX);
			if(playerY != 0) playerY -= (shiftY);
			if(playerX == 0) mapX -= (shiftX);
			if(playerY == 0) mapY -= (shiftY);
		}
	}

	/***********************************************************************
	 * @param AnimatedSprite
	 * @param Tile
	 * @param int
	 * @param int
	 * 
	 * Inherited Method
	 * Set handling for when a SPRITE contacts a TILE, this is handy for
	 * dealing with Tiles which contain Events. When specifying a new
	 * collision method, check for the type of sprite and whether a tile is
	 * solid or breakable, both, or even if it contains an event. This is
	 * mandatory because the AxohEngine finds details on collision and then 
	 * returns it for specific handling by the user.
	 * 
	 * For more details on this method, refer to the spriteCollision method above
	 *************************************************************************/
	void tileCollision(AnimatedSprite spr, Tile tile, int hitDir, int hitDir2) {
		double leftOverlap = (spr.getBoundX(hitDir) + spr.getBoundSize() - tile.getBoundX(hitDir2));
		double rightOverlap = (tile.getBoundX(hitDir2) + tile.getBoundSize() - spr.getBoundX(hitDir));
		double topOverlap = (spr.getBoundY(hitDir) + spr.getBoundSize() - tile.getBoundY(hitDir2));
		double botOverlap = (tile.getBoundY(hitDir2) + tile.getBoundSize() - spr.getBoundY(hitDir));
		double smallestOverlap = Double.MAX_VALUE; 
		double shiftX = 0;
		double shiftY = 0;

		if(leftOverlap < smallestOverlap) { //Left
			smallestOverlap = leftOverlap;
			shiftX -= leftOverlap; 
			shiftY = 0;
		}
		if(rightOverlap < smallestOverlap){ //right
			smallestOverlap = rightOverlap;
			shiftX = rightOverlap;
			shiftY = 0;
		}
		if(topOverlap < smallestOverlap){ //up
			smallestOverlap = topOverlap;
			shiftX = 0;
			shiftY -= topOverlap;
		}
		if(botOverlap < smallestOverlap){ //down
			smallestOverlap = botOverlap;
			shiftX = 0;
			shiftY = botOverlap;
		}

		//Deal with a tiles possible event property
		if(tile.hasEvent()){
			if(spr.spriteType() == TYPE.PLAYER) {
				//Warp Events(Doors)
				if(tile.event().getEventType() == TYPE.WARP) {
					tiles().clear();
					sprites().clear();
					sprites().add(playerMob);
					//Get the new map
					for(int i = 0; i < mapBase.maps.length; i++){
						if(mapBase.getMap(i) == null) continue;
						if(tile.event().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
						if(tile.event().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
					}
					//Load in the new maps Tiles and Mobs
					for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
						addTile(currentMap.accessTile(i));
						addTile(currentOverlay.accessTile(i));
						if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
						if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
					}
					//Move the player to the new position
					playerX = tile.event().getNewX();
					playerY = tile.event().getNewY();
				}	
			} //end warp
			//Item exchange event
			if(spr.spriteType() == TYPE.PLAYER && (tile.event().getEventType() == TYPE.ITEM
			   || tile.event().getEventType() == TYPE.KEY) && keyAction){
				//Chests should have opened and closed version next to each other
				if((tile._name).equals("chest")) tile.setFrame(tile.getSpriteNumber()+1);
				this.gotKey = true;//boolean to know if you got the key to open door
				this.displayFoundKeyImage = true;//display image to congratulate
			}

			//collision between player and the castle if you pressed action to go inside
			if(spr.spriteType() == TYPE.PLAYER && tile.event().getEventType() == TYPE.CASTLE 
			   && keyAction && state == STATE.GAME){
				if((tile._name).equals("castle") && randomNPCs.isEmpty()){//only if you killed all NPCs
					this.option = OPTION.LEVEL2;//go to level 2
				}

				if(!randomNPCs.isEmpty()){//if you haven't killed all NPCs
					npcsNotDead = true;//activates piece of code to print "Kill NPCs first" on screen
				}
			}

			//collision between the player and the door in level 2 while pressing action key
			if(spr.spriteType() == TYPE.PLAYER && tile.event().getEventType() == TYPE.DOOR 
			   && keyAction && state == STATE.GAME){
				if((tile._name).equals("door") && gotKey){//only if you have the key
					this.option = OPTION.LEVEL3;//go to level 3
					tile.endEvent(); //end the events
				}
				if(!gotKey){//if you do not have the key
					displayFindKey = true;//display on screen
				}
			}
		}//end check events

		//If the tile is solid, move the player off of it and exit method immediately
		if(spr.spriteType() == TYPE.PLAYER && tile.solid() && state == STATE.GAME) {
			if(playerX != 0) playerX -= (shiftX);
			if(playerY != 0) playerY -= (shiftY);
			if(playerX == 0) mapX -= (shiftX);
			if(playerY == 0) mapY -= (shiftY);
			return;
		}
		//If an npc is intersecting a solid tile, move it off
		if(spr.spriteType() != TYPE.PLAYER && tile.solid() && state == STATE.GAME){
			if(spr instanceof Mob) {
				((Mob) spr).setLoc((int)shiftX, (int)shiftY);
				((Mob) spr).resetMovement();
			}
		}
	}//end tileCollision method

	/*****************************************************************
	 * @param int
	 * @param int
	 * 
	 *Method to call which moves the player. The player never moves apart from the map
	 *unless the player is at an edge of the generated map. Also, to simulate the movement
	 *of the space around the player like that, the X movement is flipped. 
	 *Which means to move right, you subtract from the X position.
	 ******************************************************************/
	void movePlayer(int xa, int ya) {
		if(xa > 0) {
			if(mapX + xa < currentMap.getMinX() && playerX < playerSpeed && playerX > -playerSpeed) mapX += xa;
			else playerX += xa; //left +#
			npcsNotDead = false;//get rid of the "Kill NPCs" text 
			displayFindKey = false;//get rid of the "Find key first" text
		}
		if(xa < 0) {
			if(mapX + xa > currentMap.getMaxX(SCREENWIDTH) && playerX < playerSpeed && playerX > -playerSpeed) mapX += xa;
			else playerX += xa; //right -#
			npcsNotDead = false;//get rid of the "Kill NPCs" text 
			displayFindKey = false;//get rid of the "Find key first" text
		}
		if(ya > 0) {
			if(mapY + ya < currentMap.getMinY() && playerY < playerSpeed && playerY > -playerSpeed) mapY += ya;
			else playerY += ya; //up +#
			npcsNotDead = false;//get rid of the "Kill NPCs" text 
			displayFindKey = false;//get rid of the "Find key first" text
		}
		if(ya < 0) {
			if(mapY + ya > currentMap.getMaxY(SCREENHEIGHT) && playerY < playerSpeed && playerY > -playerSpeed) mapY += ya;
			else playerY += ya; //down -#
			npcsNotDead = false;//get rid of the "Kill NPCs" text 
			displayFindKey = false;//get rid of the "Find key first" text
		}
	}

	/**********************************************************
	 * The Depths of Judgement Lies Below
	 * 
	 *             Key events - Mouse events
	 *                            
	 ***********************************************************/

	/****************************************************************
	 * Check specifically defined key presses which do various things
	 ****************************************************************/
	public void checkInput() {
		int xa = 0;
		int ya = 0;

		/********************************************
		 * Special actions for In Game
		 *******************************************/
		if(state == STATE.GAME && inputWait < 0) { 
			//exit with escape
			if(keyEscape){
				save.saveState(currentFile, playerMob.health(), currentMap.mapName(), 
				currentOverlay.mapName(), "up", currentLevel);
				System.exit(0);
			}
			
			//A or left arrow(move left)
			if(keyLeft) {
				xa = xa + 1 + playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//D or right arrow(move right)
			if(keyRight) {
				xa = xa - 1 - playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//W or up arrow(move up)
			if(keyUp) {
				ya = ya + 1 + playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//S or down arrow(move down)
			if(keyDown) {
				ya = ya - 1 - playerSpeed;
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}

			//No keys are pressed
			if(!keyLeft && !keyRight && !keyUp && !keyDown) {
				playerMob.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			movePlayer(xa, ya);

			//I(Inventory)
			if(keyInventory) {
				state = STATE.INGAMEMENU;
				inputWait = 10;
			}

			//SpaceBar(action button)
			if(keySpace) {
				playerMob.inOutItem();
				inputWait = 10;
			}
		}//end in game choices

		/*****************************************
		 * Special actions for the Title Menu
		 *****************************************/
		if(state == STATE.TITLE && inputWait < 0){
			//For when no initial choice has been made
			if(option == OPTION.NONE){
				//exit with escape
				if(keyEscape){
					System.exit(0);
				}
				//S or down arrow(Change selection)
				if(keyDown && titleLocation < 3) {
					//titleX -= 105;
					titleY += 125;
					titleLocation++;
					inputWait = 5;
				}
				//W or up arrow(Chnage selection
				if(keyUp && titleLocation > 0){
					//titleX += 105;
					titleY -= 125;
					titleLocation--;
					inputWait = 5;
				}
				//Enter key(Make a choice)
				if(keyEnter) {
					if(titleLocation == 0){
						option = OPTION.NEWGAME;
						titleLocation = 0;
						inputWait = 10;
						keyEnter = false;
					}
					if(titleLocation == 1){
						option = OPTION.LOADGAME;
						titleLocation = 0;
						inputWait = 5;
						keyEnter = false;
					}
					if(titleLocation == 2){
						option = OPTION.TUTORIAL;
						inputWait = 5;
						keyEnter = false;
					}
					if(titleLocation == 3){
						option = OPTION.CREDITS;
						inputWait = 5;
						keyEnter = false;
					}
				}
			}//end option none

			//*****************************************************************************************Tutorial
			if(option == OPTION.TUTORIAL){
				if(keyBack && !title.isGetName()){
					inputWait = 5;
					option = OPTION.NONE;
				}

				//exit with escape
				if(keyEscape){
					System.exit(0);
				}
			}

			//*****************************************************************************************Credits
			if(option == OPTION.CREDITS){
				if(keyBack && !title.isGetName()){
					inputWait = 5;
					option = OPTION.NONE;
				}

				//exit with escape
				if(keyEscape){
					System.exit(0);
				}
			}

			//*****************************************************************************************New Game
			if(option == OPTION.NEWGAME){
				if(keyBack && !title.isGetName()){
					if(option == OPTION.NEWGAME) titleLocation = 0;
					inputWait = 5;
					titleX2 = (int)(SCREENWIDTH/4.3); //relative to screen
					titleY2 = (int)(SCREENHEIGHT/1.95); //relative to screen
					option = OPTION.NONE;
				}
				//10-27-2015
				if(keyRight){//D key in new game to delete
					//first called to get rid of any hidden files
					title.update(option, titleLocation);//get the other files
					title.deleteFile(titleLocation);//deletes the selected file
					//called second time to update after deletion
					title.update(option, titleLocation);//get the other files
					inputWait = 5;
				}

				//exit with escape
				if(keyEscape){
					System.exit(0);
				}
				//S or down arrow(Change selection)
				if(keyDown && titleLocation < 2 && !title.isGetName()) {
					titleLocation++;
					titleY2 += 80;
					inputWait = 7;
				}
				//W or up arrow(Change selection)
				if(keyUp && titleLocation > 0 && !title.isGetName()) {
					titleLocation--;
					titleY2 -= 80;
					inputWait = 7;
				}
				//Enter key(Make a choice)
				if(keyEnter && !title.isGetName()) {
					if(option == OPTION.NEWGAME) {
						if(title.files() != null){ //Make sure the location of a new game is greater than previous ones(Not overwriting)
							if(title.files().length - 1 < titleLocation) {
								title.enter();
								titleX2 += 40;
								inputWait = 5;
							}

						}
						if(title.files() == null) { //Final check if there are no files made yet, to make the file somewhere
							title.enter();
							titleX2 += 40;
							inputWait = 5;
						}
					}

				}//end enter key

				//The following is for when a new file needs to be created - Typesetting
				if(title.isGetName() == true) {
					title.setFileName(currentChar);
					currentChar = '\0'; //null
					//Back space(Delete last character)
					if(keyBack) {
						title.deleteChar();
						inputWait = 5;
					}
					//Back space(exit name entry if name has no characters)
					if(keyBack && title.getFileName().length() == 0) {
						title.setGetName(false);
						titleX2 -= 40;
						inputWait = 5;
					}
					//Enter key(Write the file using the currently typed name and save it)
					if(keyEnter && title.getFileName().length() > 0) {
						save.newFile(title.getFileName());
						title.setGetName(false);
						currentFile = title.getFileName();
						option = OPTION.STORYLINE;
						setGameState(STATE.GAME);
						keyEnter = false;
					}

				}//end get name
			}
			//************************************************************************************************Load Game
			//After choosing an option
			if(option == OPTION.LOADGAME){
				//Backspace(Exit choice)
				if(keyBack && !title.isGetName()){
					if(option == OPTION.LOADGAME) titleLocation = 1;
					inputWait = 5;
					titleX2 = (int)(SCREENWIDTH/4.3); //relative to screen
					titleY2 = (int)(SCREENHEIGHT/1.95); //relative to screen
					option = OPTION.NONE;
				}
				//exit with escape
				if(keyEscape){
					System.exit(0);
				}
				//S or down arrow(Change selection)
				if(keyDown && titleLocation < 2 && !title.isGetName()) {
					titleLocation++;
					titleY2 += 80;
					inputWait = 7;
				}
				//W or up arrow(Change selection)
				if(keyUp && titleLocation > 0 && !title.isGetName()) {
					titleLocation--;
					titleY2 -= 80;
					inputWait = 7;
				}
				//Enter key(Make a choice)
				if(keyEnter && !title.isGetName()) {

					//Load the currently selected file
					if(option == OPTION.LOADGAME) {
						currentFile = title.enter();
						if(currentFile != "") { //File is empty
							loadGame();
							inputWait = 5;
							option = OPTION.NONE;
							state = STATE.GAME;
							setGameState(STATE.GAME);
						}
					}
				}//end enter key

				//The following is for when a new file needs to be created - Typesetting
				if(title.isGetName() == true) {
					title.setFileName(currentChar);
					currentChar = '\0'; //null
					//Back space(Delete last character)
					if(keyBack) {
						title.deleteChar();
						inputWait = 5;
					}
					//Back space(exit name entry if name has no characters)
					if(keyBack && title.getFileName().length() == 0) {
						title.setGetName(false);
						titleX2 -= 40;
						inputWait = 5;
					}
					//Enter key(Write the file using the currently typed name and save it)
					if(keyEnter && title.getFileName().length() > 0) {
						save.newFile(title.getFileName());
						title.setGetName(false);
						currentFile = title.getFileName();
						state = STATE.GAME;
						option = OPTION.NONE;
						setGameState(STATE.GAME);
					}
				}//end get name
			}//end new/load option
		}//end title state


		/******************************************
		 * Special actions for In Game Menu
		 ******************************************/
		if(state == STATE.INGAMEMENU && inputWait < 0) {
			//exit with escape
			if(keyEscape){
				//save then quit
				save.saveState(currentFile, playerMob.health(), currentMap.mapName(), currentOverlay.mapName(), "up", currentLevel);
				System.exit(0);
			}
			//I(Close inventory)
			if(keyInventory) {
				state = STATE.GAME;
				option = OPTION.NONE;
				inLocation = 0;
				inY = (int)(SCREENHEIGHT/3.2);//relative to screen
				inputWait = 8;
			}
			//No option is chosen yet
			if(option == OPTION.NONE){ 
				if(wait == 0) wasSaving = false;
				//W or up arrow(Move selection)
				if(keyUp) {
					if(inLocation > 0) {
						inY -= 100;
						inLocation--;
						inputWait = 10;
					}
				}
				//S or down arrow(move selection)
				if(keyDown) {
					if(inLocation < 4) {
						inY += 100;
						inLocation++;
						inputWait = 10;
					}
				}
				//Enter key(Make a choice)
				if(keyEnter) {
					if(inLocation == 0){
						option = OPTION.ITEMS;
						inputWait = 5;
					}
					if(inLocation == 1){
						option = OPTION.EQUIPMENT;
						inputWait = 5;
					}
					if(inLocation == 2){
						option = OPTION.MAGIC;
						inputWait = 5;
					}
					if(inLocation == 3){
						option = OPTION.STATUS;
						inputWait = 5;
					}
					if(inLocation == 4){
						option = OPTION.SAVE;
						inputWait = 20;
					}
					keyEnter = false;
				}
			}

			//Set actions for specific choices in the menu
			//Items
			if(option == OPTION.ITEMS) {
				//W or up arrow(move selection)
				if(keyUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				//S or down arrow(move selection)
				if(keyDown) {
					if(sectionLoc == 3) inMenu.loadNextItems();
					if(inMenu.getTotalItems() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
				//Enter key(Make a choice)
				if(keyEnter){
					if(confirmUse) {
						inMenu.useItem(); //then use item
						confirmUse = false;
						keyEnter = false;
					}
					if(inMenu.checkCount() > 0 && keyEnter) confirmUse = true;
					inputWait = 10;
				}
				//Back space(Go back on your last choice)
				if(keyBack) confirmUse = false;
			}

			//Equipment
			if(option == OPTION.EQUIPMENT) {
				//W or up arrow(move selection)
				if(keyUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				//S or down arrow(move selection)
				if(keyDown) {
					if(sectionLoc == 3) inMenu.loadNextEquipment();
					if(inMenu.getTotalEquipment() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
			}

			//Saving
			if(option == OPTION.SAVE){
				//Key enter(Save the file)
				if(keyEnter){
					save.saveState(currentFile, playerMob.health(), currentMap.mapName(), currentOverlay.mapName(), "up", currentLevel);
					inputWait = 20;
					wait = 200;
					waitOn = true;
					wasSaving = true;
					option = OPTION.NONE;
				}
			}

			//Backspace(if a choice has been made, this backs out of it)
			if(keyBack && option != OPTION.NONE) {
				option = OPTION.NONE;
				inMenu.setItemLoc(0);
				sectionLoc = 0;
				inputWait = 8;
				keyBack = false;
			}
			//Backspace(if a choice has not been made, this closes the inventory)
			if(keyBack && option == OPTION.NONE) {
				state = STATE.GAME;
				option = OPTION.NONE;
				inLocation = 0;
				sectionLoc = 0;
				inY = 90;
				inputWait = 8;
			}
		}
		inputWait--;
	}

	/**
	 * Inherited method
	 * @param keyCode
	 * 
	 * Set keys for a new game action here using a switch statement
	 * dont forget gameKeyUp
	 */
	void gameKeyDown(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_A:
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_D:
			keyRight = true;
			break;
		case KeyEvent.VK_UP:
			keyUp = true;
			break;
		case KeyEvent.VK_W:
			keyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = true;
			break;
		case KeyEvent.VK_S:
			keyDown = true;
			break;
		case KeyEvent.VK_I:
			keyInventory = true;
			break;
		case KeyEvent.VK_F:
			keyAction = true;
			break;
		case KeyEvent.VK_ENTER:
			keyEnter = true;
			break;
		case KeyEvent.VK_BACK_SPACE:
			keyBack = true;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = true;
			break;
		case KeyEvent.VK_ESCAPE:
			keyEscape = true;
			break;
		}
	}

	/**
	 * Inherited method
	 * @param keyCode
	 * 
	 * Set keys for a new game action here using a switch statement
	 * Dont forget gameKeyDown
	 */
	void gameKeyUp(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_A:
			keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_D:
			keyRight = false;
			break;
		case KeyEvent.VK_UP:
			keyUp = false;
			break;
		case KeyEvent.VK_W:
			keyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = false;
			break;
		case KeyEvent.VK_S:
			keyDown = false;
			break;
		case KeyEvent.VK_I:
			keyInventory = false;
			break;
		case KeyEvent.VK_F:
			keyAction = false;
			break;
		case KeyEvent.VK_ENTER:
			keyEnter = false;
			break;
		case KeyEvent.VK_BACK_SPACE:
			keyBack = false;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = false;
			break;
		case KeyEvent.VK_ESCAPE:
			keyEscape = false;
			break;
		}
	}

	/**
	 * Inherited method
	 * Currently unused
	 */
	void gameMouseDown() {	
	}

	/**
	 * Inherited method
	 * Currently if the game is running and the sword is out, the player attacks with it
	 */
	void gameMouseUp() {
		//if mouse is clicked and player's sword is taken out
		if(getMouseButtons(1) == true && playerMob.isTakenOut()) {
			playerMob.attack();	//player attacks

			if(!randomNPCs.isEmpty()){//if there are NPCs alive
				if(randomNPCs.size() == npcTileNumber.size()){
					for(int i = 0; i < randomNPCs.size(); i++){//for every NPC
						//calculate X and Y bounds to see if when the player attacked, the NPC is close
						//enough to decrease its health
						int xHeadBound = Math.abs((int)randomNPCs.get(i).getBoundX(2)-(int)playerMob.getBoundX(2));
						int yHeadBound = Math.abs((int)randomNPCs.get(i).getBoundY(2)-(int)playerMob.getBoundY(2));

						//if within range and if not swinging the sword from previous attack, make damage
						if((xHeadBound < 80) && (yHeadBound < 80) && randomNPCs.get(i).health()>0){
							try {
								swordClip = new PlaySound("hard2.wav");//play the hit sound effect
								swordClip.play();
							} catch (Exception e) {
								e.printStackTrace();
							}
							Random rand = new Random();
							int randomNum = rand.nextInt((10 - 3) + 1) + 3;//random number between 3-10
							randomNPCs.get(i).takeDamage(randomNum);//NPC take damage if close to player
							int n = damageInflicted.get(i);
							damageInflicted.set(i, randomNum+n);//add the damage inflicted for later use - pie calculation

							boolean value = rand.nextBoolean();//random boolean value

							//if true
							if(value){//just to make their attacks random
								//set the randomNPC direction to the one he's currently walking
								//needed because they would just go up when swinging
								randomNPCs.get(i).setDirection(randomNPCs.get(i).getRandomMobDir());
								randomNPCs.get(i).attack();//make the npc attack
								int randomNum1 = rand.nextInt(4);//random number between 0-4
								playerMob.takeDamage(randomNum1);//make damage to the player
							}
						}//end of if NPC is close enough to player

						//if you npc dies, then remove it from the map and from the ArrayList (all 3)
						if((xHeadBound < 80) && (yHeadBound < 80) && randomNPCs.get(i).health()<=0){
							randomNPCs.get(i).stop();//stop this NPC
							mapBase.getCityO().accessTile(npcTileNumber.get(i)).removeMob();//remove this mob
							//set their bounds to 0 meaning that it is like nothing is there, you can walk by
							mapBase.getCityO().accessTile(npcTileNumber.get(i)).mob().setMultBounds(0, 0, 0, 0, 0, 0, 0, 0, 0);
							randomNPCs.remove(i);//remove from randomNPCs ArrayList
							npcTileNumber.remove(i);//remove the tile number to keep the arrays same size/parallel
							damageInflicted.remove(i);//remove the damage inflicted for same reason
						}
					}
				}
			}//end of 'if randomNPCs is not empty'

			//this code only runs if in level 3 because Shia is only in level 3
			if(currentLevel == 3){
				//calculate X and Y bounds to see if when the player attacked, shia is close enough to
				//get hit and decrease his health
				int xShiaHeadBound = Math.abs((int)shia.getBoundX(2)-(int)playerMob.getBoundX(2));
				int yShiaHeadBound = Math.abs((int)shia.getBoundY(2)-(int)playerMob.getBoundY(2));

				//if shia is close enough from the player to get hit
				if((xShiaHeadBound < 80) && (yShiaHeadBound < 80) && shia.health()>0){
					try {//play the hit sound effect
						swordClip = new PlaySound("hard2.wav");//get the file
						swordClip.play();
					}catch (Exception e) {
						e.printStackTrace();
					}
					Random rand = new Random();
					int randomNum = rand.nextInt(10);//random number between 0-10
					shia.takeDamage(randomNum);//shia takes damage
					Random rand1 = new Random();
					boolean value = rand1.nextBoolean();

					if(value){//randomly boolean
						int randomNum1 = rand1.nextInt(6);//random number between 0-6
						shia.setDirection(shia.getRandomMobDir());//set direction
						shia.attack();//shia attacks the player
						playerMob.takeDamage(randomNum1);//player takes damage
					}
				}

				//if shia dies
				if((xShiaHeadBound < 80) && (yShiaHeadBound < 80) && shia.health()<=0){
					shia.stop();//stop shia
					mapBase.getLevel3().accessTile(shiaTile).removeMob();//remove him
					mapBase.getLevel3().accessTile(shiaTile).mob().setMultBounds(0, 0, 0, 0, 0, 0, 0, 0, 0);
					state = STATE.ENDGAME;//set the state to end the game
					super.stop();//stop game loop NEEDED to display the GIF correctly
				}
			}
		}
	}

	/**
	 * Inherited Method
	 * Currently unused
	 */
	void gameMouseMove() {
	}

	/**
	 * Loads the game from the text file.
	 * Saves the player's health, map name, map overlay, direction of player, and current level
	 */
	void loadGame() {
		//********************initialization************************
		int health = 0;
		String mapName = "";
		String mapOverlay = "";
		String direction = "";
		int currLevel=1;

		//if there is a current file
		if(currentFile != "") {		
			Scanner fileInput;
			File inFile = new File("C:/gamedata/saves/" + currentFile);
			//try to read everything into the game
			try{
				fileInput = new Scanner(inFile);
				health = fileInput.nextInt();
				mapName = fileInput.next();
				mapOverlay = fileInput.next();
				direction = fileInput.next();
				currLevel = fileInput.nextInt();
				fileInput.close(); //close the file
			}catch (FileNotFoundException e){
				System.out.println(e);
			} // end catch	
			//update current level
			this.currentLevel = currLevel;
			tiles().clear();//clear the tiles
			sprites().clear();//clear the sprite animations 

			//****************same step than in game start up. Just to read in the corresponding map + overlay******************
			for(int i = 0; i < mapBase.maps.length; i++){
				if(mapBase.getMap(i) == null) continue;
				if(mapName == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
				if(mapOverlay == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
			}
			//depending on the level, load the game in the correct map/level
			if(currLevel == 2) this.levelUp("level2_0", "level2_1");
			if(currLevel == 3) this.levelUp("level2_0","level3");
			
			playerMob.setHealth(health);//set the player's health
			playerMob.setDirection(direction);//set the player's direction
			sprites().add(playerMob);//add player to the sprite animations
			currentMap.render(this, g2d, mapX, mapY);//render the map
			currentOverlay.render(this, g2d, mapX, mapY);//render the overlay

			//same step than in game start up. Just to read in the corresponding map + overlay
			for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
				addTile(currentMap.accessTile(i));
				addTile(currentOverlay.accessTile(i));
				if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
				if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			}//end for
			
			System.out.println("Load Successful");//show that load was successful
		} //end file is not empty check
	} //end load method

	/**
	 * Team A2
	 * Draws NPC's health at the tile number specified
	 * @param tileNumber: Tile where the NPC is located at
	 * @param g: graphics to draws
	 * @param number: amount of health left (green)
	 */
	public void drawNPCHealth(int tileNumber, Graphics g, int number){
		//positioning of the health pie of each NPC. can be adjusted
		int xLoc = (int)currentOverlay.accessTile(tileNumber).mob().getXLoc()-10;
		int yLoc = (int)currentOverlay.accessTile(tileNumber).mob().getYLoc();

		g.setColor(Color.RED);//set to red
		g.fillArc(xLoc, yLoc, 50, 50, 0, 365);//paint red circle. Always full
		g.setColor(Color.GREEN);//set to green
		g.fillArc(xLoc, yLoc, 50, 50, 0, number);//paint green circle health based on the number passed in
		g.setColor(Color.BLACK);//set to black
		g.setFont(small);//set font to small
		//draw numerical health left
		g.drawString("" + currentOverlay.accessTile(tileNumber).mob().health(), xLoc+20, yLoc+25);
		g.setFont(simple);//set font back to regular
	} 

	/**
	 * Levels you up depending on what map and overlay name you pass in.
	 * These names are the names you gave the maps in MapDatabase when you created
	 * the maps array of possible maps
	 * @param map: the map name where you want to level up
	 * @param overlay: the overlay where you want to level up
	 */
	public void levelUp(String map, String overlay){
		this.randomNPCs.clear();//basically making sure arrays are cleared
		this.npcTileNumber.clear();//clear it
		this.damageInflicted.clear();//clear it

		tiles().clear();//clear tiles
		sprites().clear();	//clear sprites
		sprites().add(playerMob);//add the player again

		//get the new map and overlay
		for(int i = 0; i < mapBase.maps.length; i++){
			if(mapBase.getMap(i) == null) continue;
			if(mapBase.getMap(i).mapName() == map) currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == overlay) currentOverlay = mapBase.getMap(i);

		}

		//only if in level 3
		if(currentLevel == 3){
			//add shia to level 3 map
			shia = new Mob(this, g2d, shiaCharacter, 40, TYPE.RANDOMPATH, "shia", true);//create shia
			shia.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
			shia.setMoveAnim(32, 48, 40, 56, 3, 8);
			shia.setDirection("down");//set up direction
			//If you change the starting max health, don't forget to change it in inGameMenu.java 
			//max health also
			shia.setHealth(shiaHealth);
			//new weapon can me made, i just left it as swords
			shia.addAttack("sword", 0, 5);
			shia.getAttack("sword").addMovingAnim(17, 25, 9, 1, 3, 8);
			shia.getAttack("sword").addAttackAnim(20, 28, 12, 4, 3, 6);
			shia.getAttack("sword").addInOutAnim(16, 24, 8, 0, 1, 10);
			shia.setCurrentAttack("sword"); //Starting attack
			shia.setSpeed(15);//speed
			mapBase.getLevel3().accessTile(shiaTile).addMob(shia);//add shia to the map
		}

		//Add the tiles from the map to be updated each system cycle
		for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
			mapBase.getCityO().accessTile(i).setMultBounds(0, 0, 0, 0, 0, 0, 0, 0, 0);//clear the bounds
			//only if going to level 2 to 3 to clean up the bounds of the old map
			if(currentLevel != 2){
				mapBase.getLevel2().accessTile(i).setMultBounds(0, 0, 0, 0, 0, 0, 0, 0, 0);
			}
			addTile(currentMap.accessTile(i));
			addTile(currentOverlay.accessTile(i));
			if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
			if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			currentMap.accessTile(i).getEntity().setX(-300);
			currentOverlay.accessTile(i).getEntity().setX(-300);
		}
	}
	
	/**
	 * Draws the player's health pie
	 * @param g2d: graphics to draw
	 */
	public void drawPlayersPie(Graphics2D g2d){
		//get the ratio of the damage
		double playerRatio = (60-playerMob.health())/60.0;//ratio of health inflicted
		double playerPieRatio = 365*playerRatio;//get the ratio of the pie based on above ratio
		double playerCeilNum = Math.ceil(playerPieRatio);//ceil that number
		int playerNum = 365 - (int)playerCeilNum;//convert to int and subtract by 365
		g2d.setColor(Color.RED);//color to red
		g2d.fillArc(10, 10, 70, 70, 0, 365);//paint red circle
		g2d.setColor(Color.GREEN);//color to green
		g2d.fillArc(10, 10, 70, 70, 0, playerNum);//paint green circle on top of red of health
		g2d.setColor(Color.BLACK);//color to black
		g2d.setFont(small);//font to small
		g2d.drawString("" + playerMob.health(), 40, 45);//draw the health in a value
		g2d.setFont(simple);//font to simple again
	}

	/**
	 * Deletes the saved file if you either beat shia, or you die so you
	 * can start a new game
	 * @param path: the path you the file
	 */
	public void deleteSavedFile(Path path){
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}//delete that file
	}

	//sets the frame from the main class after created
	public void setFrame(JFrame frame){
		this.frame = frame;
	}
} //end class