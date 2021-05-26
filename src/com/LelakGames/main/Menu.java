package com.LelakGames.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"New Game" , "Load Game" , "Quit"};
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public boolean up, down, enter;
	
	 public boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
				if(currentOption < 0)
					currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
				if(currentOption > maxOption)
					currentOption = 0;
		}
		
		if(enter) {
			enter = false;
			if(options[currentOption] == "New Game" || options[currentOption] == "Continue" ) {
				Game.gameState = "NORMAL";
				pause = false;
			}else if (options[currentOption]  == "Quit") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color (0,0,0,210));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.cyan);
		g.setFont(new Font ("arial", Font.BOLD, 40));
		g.drawString("The Legend of Münchi",(Game.WIDTH*Game.SCALE ) / 2 - 205, (Game.HEIGHT*Game.SCALE) / 2 - 150);
		
		//menu options
		g.setColor(Color.white);
		g.setFont(new Font ("arial", Font.BOLD, 20));
		if(pause == false)
		g.drawString("New Game",(Game.WIDTH*Game.SCALE ) / 2 - 45, (Game.HEIGHT*Game.SCALE) / 2 - 30);
		else
			g.drawString("Continue",(Game.WIDTH*Game.SCALE ) / 2 - 40, (Game.HEIGHT*Game.SCALE) / 2 - 30);
		g.setFont(new Font ("arial", Font.BOLD, 20));
		g.drawString("Load Game",(Game.WIDTH*Game.SCALE ) / 2 - 45, (Game.HEIGHT*Game.SCALE) / 2 + 40);
		g.setFont(new Font ("arial", Font.BOLD, 20));
		g.drawString("Quit",(Game.WIDTH*Game.SCALE ) / 2 - 15, (Game.HEIGHT*Game.SCALE) / 2 + 115);
		
		if(options[currentOption] == "New Game") {
			g.drawString(">",(Game.WIDTH*Game.SCALE ) / 2 - 85, (Game.HEIGHT*Game.SCALE) / 2 - 30);
		}else if(options[currentOption] == "Load Game") {
			g.drawString(">",(Game.WIDTH*Game.SCALE ) / 2 - 85, (Game.HEIGHT*Game.SCALE) / 2 + 40);
		}else if(options[currentOption] == "Quit") {
			g.drawString(">", (Game.WIDTH*Game.SCALE ) / 2 - 85, (Game.HEIGHT*Game.SCALE) / 2 + 115);
		}
	}

}
