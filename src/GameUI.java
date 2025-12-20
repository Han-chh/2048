import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * GameUI类是2048游戏的主要用户界面类
 * 负责创建游戏窗口、处理用户输入、管理游戏逻辑和显示
 * 继承自JFrame，使用Swing组件构建图形界面
 */
@SuppressWarnings("serial")
public class GameUI extends JFrame{

	// 图片资源目录路径，从类路径根目录开始
	public static final String ImageDirectoryLocale = "/Images/";
	// 背景图片，使用类路径资源加载
	private final ImageIcon backGroundImage = new ImageIcon(GameUI.class.getResource(ImageDirectoryLocale+"back.png"));
	// 窗口默认位置和大小常量
	private final int DEFALT_X = 400;
	private final int DEFALT_Y = 200;
	private final int DEFALT_WIDTH = 360;
	private final int DEFALT_HEIGHT = 580;
	// 随机数生成器，用于生成新Tile的位置
	private Random ran = new Random();
	// 当前游戏分数
	private int score = 0;
	// 显示分数的标签
	private JLabel score_label = new JLabel(Integer.toString(score));

	/**
	 * Direction枚举定义了Tile移动的方向
	 * 每个方向都有对应的步长值，用于计算移动后的位置
	 */
	private enum Direction{
		UP(-4),DOWN(+4),LEFT(-1),RIGHT(1);

		private int steps;
		Direction(int steps){
			this.steps = steps;
		}

		public int getSteps() {
			return this.steps;
		}
	}

	// 存储所有Tile对象的HashMap，键为位置索引(0-15)，值为Tile对象
	private Tile[] Tiles = Tile.createEmptyTiles();
	

	// 放置Tile的面板，使用绝对布局
	JPanel tilePanel = new JPanel(null);
	// 背景面板，包含所有UI组件
	JPanel backGroundPanel = new JPanel(null);

	/**
	 * GameUI构造函数
	 * 初始化游戏窗口的基本属性，设置窗口大小、标题、关闭操作
	 * 调用init()方法初始化UI组件，最后设置窗口可见
	 */
	public GameUI() {
		setBounds(DEFALT_X, DEFALT_Y, DEFALT_WIDTH, DEFALT_HEIGHT);
		setTitle("2048 Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false); // 可选：禁止调整窗口大小
		init();
		setVisible(true);
	}

	/**
	 * 初始化UI组件的方法
	 * 创建并配置所有Swing组件，包括面板、按钮、标签等
	 * 设置组件的位置、大小、样式，并添加事件监听器
	 */
	private void init() {
		add(backGroundPanel);
		Tiles = Tile.createEmptyTiles();
		// 添加Tile面板到背景面板
		backGroundPanel.add(tilePanel);
		tilePanel.setBounds(8, 115, 330, 355);
		tilePanel.setOpaque(false); // 设置透明，以便显示背景

		// 创建"新游戏"按钮
		var newGame = new JButton("New Game");
		backGroundPanel.add(newGame);
		newGame.setBounds(10, 480, 80, 50);
		newGame.setFocusPainted(false);
		newGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		// 创建操作说明标签
		var ins = new JLabel("Use arrows to move the tiles");
		ins.setBounds(100,480,250,50);
		backGroundPanel.add(ins);

		// 创建背景图片标签
		JLabel backGroundLabel = new JLabel(backGroundImage);
		backGroundLabel.setBounds(0, 0, 350, 550);
		backGroundPanel.add(backGroundLabel);

		// 配置分数标签
		score_label.setBounds(110, 68, 150, 50);
		score_label.setFont(new Font("Sancerif",Font.BOLD,22));
		score_label.setForeground(new Color(200,150,80));
		backGroundPanel.add(score_label);

		// 为"新游戏"按钮添加点击事件监听器
		newGame.addActionListener((e)->NewGame());
	}

	/**
	 * 设置游戏分数的方法
	 * 更新分数值并刷新分数标签的显示
	 * @param score 新的分数
	 */
	private void setScore(int score) {
		this.score = score;
		score_label.setText(Integer.toString(score));
	}

	/**
	 * 开始新游戏的方法
	 * 重置分数、清空所有Tile、重新生成初始Tile
	 * 添加键盘事件监听器处理用户输入
	 */
	private void NewGame() {
		setScore(0);
		Tiles = Tile.createEmptyTiles();

		tilePanel.removeAll();
		// 将所有空的Tile添加到面板
		for (Tile tile : Tiles) {
			tilePanel.add(tile);
		}
		tilePanel.repaint();

		// 生成两个初始Tile
		showNewTileOnPanel();
		showNewTileOnPanel();

		requestFocus(); // 获取焦点以接收键盘事件
		showTiles();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 处理WASD键和方向键的输入
				if(e.getKeyChar() ==  'w'  ||e.getKeyCode()== KeyEvent.VK_UP) {
					move(new int[]{0,1,2,3},Direction.UP);
				}else if(e.getKeyChar() ==  's'  ||e.getKeyCode()== KeyEvent.VK_DOWN) {
						move(new int[]{12,13,14,15},Direction.DOWN);
				}else if(e.getKeyChar() ==  'a'  ||e.getKeyCode()== KeyEvent.VK_LEFT) {
					move(new int[]{0,4,8,12},Direction.LEFT);
				}else if(e.getKeyChar() ==  'd'  ||e.getKeyCode()== KeyEvent.VK_RIGHT) {
						move(new int[]{3,7,11,15},Direction.RIGHT);
				}
			}
		});
	}

	/**
	 * 生成随机空位置的方法
	 * 确保新Tile不会出现在已有Tile的位置上
	 * @return 随机生成的空位置索引(0-15)
	 */
	private int randomPosition() {
		int position;
		do {
			position = ran.nextInt(16);
		}while(Tiles[position].isEmpty() == false);

		return position;
	}

	/**
	 * 在随机空位置生成新Tile的方法
	 * 使用数组中已存在的Tile对象，设置其数值
	 */
	private void showNewTileOnPanel() {
		int position = randomPosition();
		// 随机生成1或2作为初始数值
		int initialPower = ran.nextInt(1, 3);
		Tiles[position].setNpower(initialPower);
		tilePanel.repaint();
	}

	/**
	 * 执行实际移动逻辑的核心方法
	 * 处理Tile的移动、合并、碰撞检测等
	 * @param startLocations 需要移动的起始位置数组
	 * @param boundary 边界位置
	 * @param direction 移动方向
	 * @return 是否有Tile发生移动
	 */
	private void move(int[] startLocations, Direction direction) {
		boolean isMove =  false;
		for(int start: startLocations) {
			int boundary = start + direction.getSteps();
			int current = start;
			for(int i = 0;i<4;i++){
				boolean []added = new boolean[16]; // 标记已合并的Tile，防止重复合并
				for (int k = 0; k < 16; k++) {
					added[k] = false;
				}
				// System.out.println(current);
				if(Tiles[current].isEmpty()) {
					current -= direction.getSteps();
					continue;
				}else{
					int tmp_current = current;
					while(true){
						int next = tmp_current + direction.getSteps();
						if (next == boundary){
							break;
						}
						if(Tiles[next].isEmpty()){
							moveTileOnPanel(tmp_current, next, Tiles[tmp_current].getNpower());
							tmp_current = next;
							isMove = true;
							continue;
						}else if (Tiles[next].getNpower() == Tiles[tmp_current].getNpower()){
							// 目标位置有相同值的Tile，执行合并
							if(added[next]) {
								// 该位置的Tile已经合并过，停止移动
								break;
							}
							moveTileOnPanel(tmp_current,next,
									Tiles[tmp_current].getNpower()+1);
							setScore(score + (int)Math.pow(2, Tiles[next].getNpower()));
							Tiles[tmp_current].setEmpty();
							isMove = true;
							added[next] = true; // 标记该位置已合并
							break; // 合并后停止当前方向的移动
							
						}else{
							// 目标位置有不同值的Tile，停止移动
							break;
						}
					}
					current -= direction.getSteps();
				}
			}
		}
		if (isMove){
			new Thread(new RandomShow()).start();
			
		}
	}

	/**
	 * 在面板上移动Tile并更新其位置和数值
	 * @param old_pos 原始位置索引
	 * @param new_pos 新位置索引
	 * @param new_power 新的数值
	 */
	private void moveTileOnPanel(int old_pos,int new_pos, int new_power) {
		Tiles[new_pos].setNpower(new_power);
		Tiles[old_pos].setEmpty();

		// 更新面板显示
		tilePanel.revalidate();
		tilePanel.repaint();
	}
	private void showTiles(){
		for (int i = 0; i < 16; i++) {
			if (i % 4 == 0) {
				System.out.println();
			}
			System.out.print(Tiles[i].getNpower() + " ");
		}
		System.out.println();

	}

	/**
	 * RandomShow内部类实现Runnable接口
	 * 负责在新Tile移动完成后延迟显示新的随机Tile
	 * 同时检测游戏结束条件
	 */
	private class RandomShow implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(300); // 延迟300毫秒，提供视觉反馈
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			showNewTileOnPanel();
			// 检测游戏是否结束
			if(detectDead()) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 显示游戏结束对话框，询问是否重新开始
				if(JOptionPane.showConfirmDialog(GameUI.this, "You Dead! Restart a new game?")==
						JOptionPane.OK_OPTION) {
					GameUI.this.NewGame();
				}
			}
			showTiles();
		}

		/**
		 * 检测游戏是否结束的方法
		 * 检查是否所有格子都满了且没有可合并的相邻Tile
		 * @return true表示游戏结束，false表示还可以继续
		 */
		private boolean detectDead() {
			boolean isDead = true;
			boolean isFull = true;
			for (Tile tile:Tiles) {
				if(tile.isEmpty()) {
					isFull = false;
					break;
				}
			}
			if(isFull) { // 所有16个格子都满了
				// 检查水平方向是否有可合并的Tile
				for(int i = 0;i<13;i+=4) {
					if(!isDead) {
						break;
					}
					for(int j = 0;j<3;j++) {
						if (Tiles[i+j].getNpower() ==
								Tiles[i+j+1].getNpower()) {
							isDead = false;
							break;
						}
					}
				}
				// 检查垂直方向是否有可合并的Tile
				for(int i = 0;i<4;i++) {
					if(!isDead) {
						break;
					}
					for(int j = 0;j<3;j++) {
						if (Tiles[i+4*j].getNpower() ==
								Tiles[i+4*j+4].getNpower()) {
							isDead = false;
							break;
						}
					}
				}

			}else {
				isDead = false; // 还有空位，可以继续游戏
			}
			return isDead;

		}

	}


}
