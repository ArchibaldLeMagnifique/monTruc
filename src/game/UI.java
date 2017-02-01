package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class UI {

	Group root;
	Game game;
	
	double x;
	AnimationTimer shakeAnim;
	double strength;
	
	public MiniMap miniMap;
	WeaponBar weaponBar;
	public ScorePan scorePan;
	Group group;
	
	public UI (Group root, Game game) {
		this.root = root;
		this.game = game;
		
		group = new Group();
		root.getChildren().add(group);
		
		miniMap = new MiniMap(game.screenWidth, game.screenHeight, game.mapSizeW, game.mapSizeH, game, game.l_l_triangles, game.pan, game.j);
		miniMap.translateCamX(game.mapSizeW/2-game.screenWidth/2);
		miniMap.translateCamY(game.mapSizeH/2-game.screenHeight/2+40);
		group.getChildren().add(miniMap);
		
		weaponBar = new WeaponBar(game.screenWidth, game.screenHeight, 3, game);
		game.listeArmes[0] = new Gun(0.5, -1, 8, 1, game);
		weaponBar.switchWeapon(game.listeArmes[0], 0);
		game.listeArmes[1] = new MiniGun(0.05, -1, 8, 1, game);
		weaponBar.switchWeapon(game.listeArmes[1], 1);
		group.getChildren().add(weaponBar);
		
		scorePan = new ScorePan(game);
		group.getChildren().add(scorePan);
		
		group.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				game.mouseX = m.getScreenX()-game.pan.x; game.mouseY = m.getScreenY()-game.pan.y;
				game.depCamX2 = 0;
				game.depCamY2 = 0;
				if (m.getScreenX() < 10) {
					game.depCamX2 = -15;
				}
				if (m.getScreenX() > 1910) {
					game.depCamX2 = 15;
				}
				if (m.getScreenY() < 10) {
					game.depCamY2 = -15;
				}
				if (m.getScreenY() > 1070) {
					game.depCamY2 = 15;
				}
			}
		});
		
		shakeAnim = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				group.setTranslateX(Math.sin(x) * strength);
				group.setTranslateY(Math.sin(2*x) * strength);
				x += 0.5;
				strength -= 1;

				if (strength < 0) {
					group.setTranslateX(0);
					group.setTranslateY(0);
					this.stop();
				}
			}
		};
	}

	
		
	public void shake (double strength) {
		this.strength = strength;
		this.x = 0;
		shakeAnim.start();

		
	}
	
	
	
}
