package fr.umlv.babaisyou.main;

import java.awt.Color;

import fr.umlv.babaisyou.Area;
import fr.umlv.babaisyou.Block;
import fr.umlv.babaisyou.Name;
import fr.umlv.babaisyou.Window;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

public class Main {

	public static void main(String[] args) {
		Application.run(Color.DARK_GRAY, context -> {

			// get the size of the screen
			ScreenInfo screenInfo = context.getScreenInfo();
			float width = screenInfo.getWidth();
			float height = screenInfo.getHeight();
			var win = new Window(width, height, 30, 20);
			var area = new Area(win);
			System.out.println("size of the screen (" + width + " x " + height + ")");
			
			new Block(1, 3, Name.Lava, area);
			new Block(6, 6, Name.Water, area);
			new Block(5, 6, Name.Wall, area);
			new Block(3, 3, Name.Rock, area);
			new Block(3, 4, Name.Rock, area);
			new Block(2, 2, Name.Baba, area);
			new Block(3, 0, Name.Baba, area);
			new Block(4, 2, Name.Flag, area);
			new Block(5, 5, Name.BabaT, area);
			new Block(5, 6, Name.Is, area);
			new Block(5, 7, Name.You, area);
			new Block(6, 4, Name.Push, area);
			new Block(8, 8, Name.RockT, area);
			new Block(15, 6, Name.Is, area);
			new Block(5, 15, Name.WallT, area);
			
			for (;;) {
				Event event = context.pollOrWaitEvent(10);
				if (event == null) { // no event
					continue;
				}
				Action action = event.getAction();
				if (action == Action.KEY_PRESSED) {
					if (event.getKey() == KeyboardKey.Q) {
						System.out.println("abort abort !");
						context.exit(0);
						return;
					}	
					area.applyProps(event.getKey());
					area.rulesOfGames();
					area.draw(context);
				}
				System.out.println(event);
			}
		});

	}

}
