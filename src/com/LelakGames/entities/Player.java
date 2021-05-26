package com.LelakGames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.LelakGames.graphics.Spritesheet;
import com.LelakGames.main.Game;
import com.LelakGames.world.Camera;
import com.LelakGames.world.World;

public class Player  extends Entity{
	
	public boolean right = false, up, left, down;
	private int right_dir = 0, left_dir = 1;
	private int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex= 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean weapon = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false, mouseShoot = false;
	
	public double life = 100, maxlife = 100;
	 
	public int mx, my ;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer =  new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		for(int i =0; i < 4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 0,16, 16);
		}
		
		for(int i =0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 16,16, 16);
			}
		for(int i =0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 48,16, 16);
			}
		for(int i =0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 32,16, 16);
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
		
		if(shoot ) {
			shoot = false;
			if(weapon && ammo > 0) {
			ammo --;
			//Create bullet and shoot
			int dx = 0;
			int px = 0;
			int py = 6;
			if( dir == right_dir) {
				px = 18;
				 dx = 1;		
			}else {
				px = - 8;
				 dx = -1;
			}
			
			AmmoShoot ammo = new AmmoShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx,0);
			Game.ammos.add(ammo);
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;
			if(weapon && ammo > 0) {
			ammo --;
			//Create bullet and shoot
			int px = 0,  py = 6;
			double angle = 0;
			if( dir == right_dir) {
				px = 18;
				angle =  Math.atan2(my - ( this.getY() + py - Camera.y), mx -  (this.getX() + px - Camera.x));		
			}else {
				px = - 8;
				angle =  Math.atan2(my - ( this.getY() + py - Camera.y), mx -  (this.getX() + px - Camera.x));
				
			}
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			
		
			AmmoShoot ammo = new AmmoShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx,dy);
			Game.ammos.add(ammo);
			}
		}
		
		if(life<= 0) {
			//Game OVer
			Game.gameState = "GAME_OVER";
			
			
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
						ammo += 10;
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
			} if(up) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			}else if (down) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	
	}
}
	

