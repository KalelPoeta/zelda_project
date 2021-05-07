package com.LelakGames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.LelakGames.main.Game;
import com.LelakGames.world.Camera;

public class AmmoShoot extends Entity {
	
	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life = 20, curLife = 0;
	
	
		public AmmoShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
			super(x, y, width, height, sprite);
			this.dx = dx;
			this.dy = dy;
			
		}
	
	
	public void tick( ) {
		x+=dx*spd;
		y+= dy*spd;
		curLife ++;
		if(curLife == life) {
			Game.ammos.remove(this);
			return;
		}
		
	}
	
	public void render(Graphics g){
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		
	}
	

}
