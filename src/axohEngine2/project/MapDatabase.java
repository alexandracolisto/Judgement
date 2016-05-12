/****************************************************************************************************
 * @author Travis R. Dewitt; edited by Team A2
 * @version 1.0
 * Date: July 5, 2015
 * 
 * Title: Map Database
 * Description: A data handling class used for large projects. This class contains all of the spritesheets,
 * tiles, events, items, mobs and map creations since they all interlock together.
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 ****************************************************************************************************/
//Package
package axohEngine2.project;

//Imports
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Event;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;

public class MapDatabase {

	//SpriteSheets
	SpriteSheet misc;
	SpriteSheet buildings;
	SpriteSheet environment32;
	SpriteSheet extras2;
	SpriteSheet mainCharacter, npcCharacter;
	SpriteSheet castle;
	SpriteSheet floorTile;
	SpriteSheet door;
	SpriteSheet lava;
	
	//Maps
	Map city;
	Map cityO, cityObanner;
	Map houses;
	Map housesO;
	Map level2_0, level2_1, level3;
	
	//Tiles - Names are defined in the constructor for better identification
	Tile d;
	Tile g;
	Tile f;
	Tile b;
	Tile r;
	Tile e;
	Tile ro;
	Tile h;
	Tile hf;
	Tile c;
	Tile cas,casB;
	Tile l; //lava
	
	//Events
	Event warp1;
	Event warp2;
	Event getPotion;
	Event getMpotion;
	Event getCastle;
	Event level2Door;
	Event getKey;
	
	//Items
	Item potion;
	Item mpotion;
	Item key;
	
	
	//NPC's and Monsters
	ArrayList<Mob> randomNPCs;
	//Mob npc;
	
	//Array of maps
	public Map[] maps;
	
	
	/****************************************************************
	 * Constructor
	 * Instantiate all variables for the game
	 * 
	 * @param frame - JFrame Window for the map to be displayed on
	 * @param g2d - Graphics2D object needed to display images
	 * @param scale - Number to be multiplied by each image for correct on screen display
	 * @param damageList - Runs parallel with randomNPCs array list. Holds total damage taken 
	 * by each NPC (parallel) to compute the health pie.
	 *******************************************************************/
	public MapDatabase(JPanel panel, Graphics2D g2d, int scale, ArrayList<Integer> damageList) {
		
		//Currently a maximum of 200 maps possible(Can be changed if needed)
		maps = new Map[200];
		randomNPCs = new ArrayList<Mob>();//holds the random NPCs 
				
		//Set up spriteSheets
		misc = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16, scale);
		buildings = new SpriteSheet("/textures/environments/cabin.png", 1, 1, 100, 2);
		environment32 = new SpriteSheet("/textures/environments/32SizeEnvironment.png", 8, 8, 32,scale);
		door = new SpriteSheet("/textures/environments/32SizeEnvironment.png", 8, 8, 32,3);
		extras2 = new SpriteSheet("/textures/extras/extras2.png", 16, 16, 16, scale);
		mainCharacter = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);
		npcCharacter = new SpriteSheet("/textures/characters/npc.png", 8, 8, 32, scale);
		castle = new SpriteSheet("/textures/environments/castle.png",1,1,128,scale);//castle
		floorTile = new SpriteSheet("/textures/environments/tile.png",1,1,12,scale+2); //in general
		lava = new SpriteSheet("/textures/environments/lava.png",1,1,12,scale+2);//lava
		
		//Set up tile blueprints and if they are animating
		d = new Tile(panel, g2d, "door", door, 0,true);
		f = new Tile(panel, g2d, "flower", misc, 1);
		g = new Tile(panel, g2d, "grass", misc, 0);
		b = new Tile(panel, g2d, "bricks", misc, 16, true);
		r = new Tile(panel, g2d, "walkWay", floorTile, 0);//floor
		e = new Tile(panel, g2d, "empty", misc, 7);
		ro = new Tile(panel, g2d, "rock", misc, 2);
		h = new Tile(panel, g2d, "house", buildings, 0, true);
		hf = new Tile(panel, g2d, "floor", misc, 8);
		cas = new Tile(panel, g2d, "castle", castle, 0, true);
		c = new Tile(panel, g2d, "chest", extras2, 3, true);
		l = new Tile(panel, g2d, "lavaTile", lava, 0, true);
		
		//Set the tile blueprints in an array for the Map
		Tile[] cityTiles = {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b};
					
			Tile [] cityOTiles = 
				    {e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, h, e, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, e, h, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, c, c, c, c, e, e, e, e, e, e, e, c, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, h, e, e, e, e, e, e, ro, ro, e, c, c, c, c, e, e, e, e, e, e, e, c, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, h, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, h, e, e, e, e, e, h, e, e, e, e, e, e, cas, e, e, e, e, e, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,  e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, h, e, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,  h, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, h, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, h, e, e, e, e, e, h, e, e, e, e, e, h, e, e, e, e, e, h, e, e, e, e, e, h, e, e, e, e, e, h, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
					 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e};
		
		//portion of level 2 maze....................................
		 Tile[] level2O = {
				            l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
						    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, c, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, l, l, l, e, e, e, l, e, e, e, e, e, e, l, l, l, l, e, e, e, l, e, e, e, l, l, l, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, l, l, l, l, e, e, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, e, e, e, l, l, l, l, e, e, e, e, l, l,
						    l, l, d, e, e, e, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, l, l, l, l, l, l, l, l, e, e, e, l, l, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, l, l, l, l, l, l, l, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, l, l, l, l, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, l, l, l, l, l, l, l, l, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, l, l, l, l, l, l, l, l, e, e, e, l, l, l, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, l, l, l, l, l, l, l, l, l, l, e, e, l, e, e, l, l, l, l, l, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, l, e, e, l, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, l, e, e, l, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, l, e, e, l, e, e, e, l, l, l, l, l, l, e, e, l, e, e, e, e, l, l,
						    l, l, l, l, l, l, l, l, l, e, e, e, l, e, e, e, l, l, l, e, e, l, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, l, l, l, l, l, l, l, l, e, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, e, e, e, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, l,
						    l, l, l, l, l, l, e, e, l, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, l, l, l, l, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, l, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, l, e, e, e, e, l, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
						    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
						    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l};
		
		 //lava tiles
		 Tile[] lavaTiles = {
				    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
				    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, l, l,
				    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
				    l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l};
		 


		 //this was commented out by Team A2. It was created by the original creator of 
		 //the game. It was never used, but I left it there just in case the next
		 //team finds use of it
		/*Tile[] houseTiles = {hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf};
		
		Tile[] houseOTiles = {hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf,
						 	  hf, hf, hf, hf, hf, hf};	*/
		
		
		//Put the maps together and add them to the array of possible maps
		city = new Map(panel, g2d, cityTiles, 40, 40, "city");
		maps[0] = city;
		
		//Put the maps together and add them to the array of possible maps
		cityO = new Map(panel, g2d, cityOTiles, 40, 40, "cityO");
		maps[1] = cityO;
		
		maps[2] = houses;
		maps[3] = housesO;
		
		//Put the maps together and add them to the array of possible maps
		level2_0 = new Map(panel, g2d, lavaTiles, 40,40,"level2_0");
		level2_1 = new Map(panel, g2d, level2O, 40,40,"level2_1");
		maps[4] = level2_0;
		maps[5] = level2_1;
		
		//necessary because I tried to do this in the constructor and it seemed like 
		//it was taking too much memory allocation for the constructor. I had to
		//make it a separate method and call it from here
		this.addLevel3(panel, g2d); //****************maps[6] spot taken by this method
		
		//Put together all events
		//Warping events
		//created by original creator. My team did not use these warps
		warp1 = new Event("fromHouse", TYPE.WARP);
		warp1.setWarp("city", "cityO", 200, -50);
		warp2 = new Event("toHouse", TYPE.WARP);
		warp2.setWarp("houses", "housesO", 620, 250);
		
		//Item events
		getPotion = new Event("getPotion", TYPE.ITEM);
		getPotion.setItem(potion);
		
		getMpotion = new Event("getMpotion", TYPE.ITEM);
		getMpotion.setItem(mpotion);
		
		getKey = new Event("getKey", TYPE.KEY);
		getKey.setItem(key);
		
		getCastle = new Event("getCastle", TYPE.CASTLE);
		level2Door = new Event("getDoor", TYPE.DOOR);
		
		//to add the potion to every tile you want. These were
		//for the old map, since I changed stuff around, the chest
		//position changed. Need to be adjusted.
		//Add the events to their specific tiles and maps
		//houses.accessTile(5).addEvent(warp1);
		/*cityO.accessTile(92).addEvent(getPotion);
		cityO.accessTile(242).addEvent(getPotion);
		cityO.accessTile(328).addEvent(getPotion);
		cityO.accessTile(327).addEvent(getMpotion);
		cityO.accessTile(326).addEvent(getMpotion);
		cityO.accessTile(325).addEvent(getMpotion);
		cityO.accessTile(93).addEvent(getMpotion);
		cityO.accessTile(94).addEvent(getMpotion);
		cityO.accessTile(95).addEvent(getMpotion);
		cityO.accessTile(96).addEvent(getMpotion);*/
		
		//add castle, level 2 door and the key
		cityO.accessTile(655).addEvent(getCastle);
		level2_1.accessTile(362).addEvent(level2Door);
		level2_1.accessTile(117).addEvent(getKey);
		
		
		//initialize how many NPCs you want and add them to array list
		//needs to be initialized here because of some collision issues
		//The creator of the game did the same thing with 1 npc.
		//All I did was follow his path, but for multiple NPCs in an ArrayList
		for(int i = 0; i < 2; i++){
			Mob tempNPC = new Mob(panel, g2d, npcCharacter, 40, TYPE.RANDOMPATH, "npc", false);
			tempNPC.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
			tempNPC.setMoveAnim(32, 48, 40, 56, 3, 8);
			//if you want to change their health, you have to go to Judgement.java line 370
			//to update the way the pie health is drawn because it thinks that the health 
			//is 30 for all of them
			tempNPC.setHealth(30);
			tempNPC.addAttack("sword", 0, 5);
			tempNPC.getAttack("sword").addMovingAnim(17, 25, 9, 1, 3, 8);
			tempNPC.getAttack("sword").addAttackAnim(20, 28, 12, 4, 3, 6);
			tempNPC.getAttack("sword").addInOutAnim(16, 24, 8, 0, 1, 10);
			tempNPC.setCurrentAttack("sword"); //Starting attack
			randomNPCs.add(tempNPC);
			damageList.add(i,0);
		}
	}
	
	/**
	 * Creates level 3 map and adds it to the maps array
	 * @param panel: panel to initialize the map
	 * @param g2d: brush
	 */
	public void addLevel3(JPanel panel, Graphics2D g2d){
		Tile [] level3O =
			   {l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
	    		l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, l, l, l, l, l, l, e, e, e, e, e, e, e, e, e, l, l, l, l, l, l, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l, l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l, l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, l, l, l, l, l, l, e, e, e, e, e, e, e, e, e, l, l, l, l, l, l, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, l, l,
	    		l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l,
	    		l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l};
		
		level3 = new Map(panel, g2d,level3O , 40, 40, "level3");
		maps[6] = level3;
	}
	
	/************************************************************
	 * Get a map back  based on its index in the array of maps
	 * 
	 * @param index - Position in the maps array
	 * @return - Map
	 *************************************************************/
	public Map getMap(int index) {
		return maps[index];
	}
	
	//getter
	public SpriteSheet getExtras2(){
		return extras2;
	}

	//getter
	public Map getCityO() {
		return cityO;
	}
	
	//getter
	public Map getLevel2(){
		return level2_1;
	}
	
	//getter
	public Map getLevel3(){
		return maps[6];
	}
	//getter
	public Map getCityObanner(){
		return cityObanner;
	}
	//getter
	public ArrayList<Mob> getRandomNPCs() {
		return randomNPCs;
	}	
}