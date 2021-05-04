package com.LelakGames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.LelakGames.graphics.Spritesheet;
import com.LelakGames.main.Game;
import com.LelakGames.world.Camera;
import com.LelakGames.world.World;

public class Player  extends Entity{
	
	public boolean right = true, up, left, down;
	private int right_dir = 0, left_dir = 1;
	private int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex= 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean weapon = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public double life = 100, maxlife = 100;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		for(int i =0; i < 4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 0,16, 16);
		}
		
		for(int i =0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 16,16, 16);
			}
		
		
		}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true; 
			dir = right_dir;
			x+=speed;
		}
		else if (left && World.isFree((int)(x-speed),this.getY())) {
			moved = true; 
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))) {
			moved = true; 
			y-=speed;}
		else if (down && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true; 
			y+=speed;
			
		}
		
		if( moved ) { 
			frames ++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();	
		checkCollisionWeapon();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(life<= 0) {
			Game.entities.clear();
			Game.enemies.clear();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/res/spritesheet3.png");
			Game.player = new Player(0, 0, 16, 16,Game.spritesheet.getSprite(32,0,16,16));
			Game.entities.add(Game.player);
			Game.world = new World("/res/map.png");
			return;
			}
	
		
		Camera.x = Camera.clamp (this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game .WIDTH) ;
		Camera.y = Camera.clamp (this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT) ;
		
     }

        
	public void checkCollisionWeapon(){
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					weapon = true;
					System.out.println("Picked up weapon" ); 
					Game.entities.remove(atual);
			}
		}
		
	}
	
}
		public void checkCollisionAmmo(){
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Ammo) {
					if(Entity.isColidding(this, atual)) {
						ammo += 2;
						System.out.println("Current ammo:" + ammo); 
						Game.entities.remove(atual);
				}
			}
			
		}
		
	}
		
		public void checkCollisionLifePack() {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Lifepack) {
					if(Entity.isColidding(this, atual)) {
						if(life>100)
							life = 100;
						life+=15;
						Game.entities.remove(atual);
				}
			}
			
		}
	}
	
	
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			if(weapon) {
				// draw weapon to the right
				g.drawImage(Entity.WEAPON_RIGHT,this.getX() + 8 - Camera.x,  this.getY() - Camera.y, null);
			}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(weapon) {
					// draw weapon to the left
					g.drawImage(Entity.WEAPON_LEFT,this.getX() - 8 - Camera.x,  this.getY() - Camera.y, null);
				}
			}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	
	}
}
