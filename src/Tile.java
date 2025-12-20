import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tile extends JLabel{
	private int npower = 1;
	private static final Random ran = new Random();
	
	private Tile(Rectangle rectangle, int npower) {
		
		super();
        this.setBounds(rectangle);
		this.npower = npower;
		if (npower > 0) {
			this.setIcon(new ImageIcon(GameUI.class.getResource(
					GameUI.ImageDirectoryLocale+(int)Math.pow(2, npower)+".png")));
		}
		
	}

	// 16个游戏格子的位置和大小定义，使用Rectangle表示
	public static List<Rectangle> Locations = List.of(new Rectangle(1,17,80,82),new Rectangle(85,17,78,82),
		new Rectangle(168,17,77,82),new Rectangle(248,17,79,82),new Rectangle(1,102,80,79),
		new Rectangle(85,102,78,79),new Rectangle(168,102,78,79),new Rectangle(248,102,81,79),
		new Rectangle(1,185,81,78),new Rectangle(85,185,77,78),new Rectangle(168,185,77,78),
		new Rectangle(248,185,79,78),new Rectangle(1,266,80,84),new Rectangle(85,266,78,84),
		new Rectangle(168,266,77,85),new Rectangle(248,266,80,84));
	
	public void setEmpty() {
		this.npower = 0;
		updateIcon();
	}

	public static Tile[] createEmptyTiles() {
		Tile[] tiles = new Tile[16];
		for (int i = 0; i < 16; i++) {
			tiles[i] = new Tile(Locations.get(i), 0);
			tiles[i].setEmpty();
		}
		return tiles;
	}

	public boolean isEmpty() {
		return this.npower == 0;
	}
	
	public int getNpower() {
		return npower;
	}


	public void addNpower() {
		this.npower++;
		updateIcon();
	}

	public void setNpower(int npower) {
		this.npower = npower;
		updateIcon();
	}

	public void updateIcon() {
		if (npower > 0) {
			String imagePath = GameUI.ImageDirectoryLocale + (int) Math.pow(2, npower) + ".png";
			var resource = GameUI.class.getResource(imagePath);
			// System.out.println("Loading image: " + imagePath + " -> " + (resource != null ? "Found" : "NOT FOUND"));
			this.setIcon(new ImageIcon(resource));
		} else {
			this.setIcon(null);
		}
		this.repaint();
	}

	
	

	@Override
	public String toString() {
		return "Tile [ntimes=" + npower +"]";
	}


	public static Tile createNewTile(int position) {
		return new Tile(Locations.get(position), ran.nextInt(1,3));
	}
}
