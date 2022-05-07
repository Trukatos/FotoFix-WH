import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Puzzle extends JFrame{
	//=============================== panels
	private static JTabbedPane tabbedPane;
	private static JPanel puzzleArea;
	//=============================== images and icons
	private Image windowIcon = ImageLoader.loadImage("puzzleIcon.png");
	private BufferedImage newGameIcon, rngIcon, saveIcon, restoreIcon; 
	private static BufferedImage def = ImageLoader.loadImage("defaultLong.png");
	
	public static BufferedImage blocked = ImageLoader.loadImage("/redX.png");
	public static ImageIcon blocked32, blocked72, blocked128;
	
	//=============================== toolbar and its items
	private JToolBar toolbar = new JToolBar(); //horizontal aligment by default
	private JButton newGameButton;
	private JButton rngButton;
	private JButton saveButton;
	private JButton restoreButton;
	
	//=============================== labels
	private JLabel owner = new JLabel("<html><div style='text-align: center;'>" + "FotoFix Deluxe von Mathias Assmann" + "</div></html>", SwingConstants.RIGHT);
	private JLabel announcer = new JLabel("", SwingConstants.CENTER);
	private final String feld = "Feld ";
	private final String solveDisclaimer = " wurde gelöst!";
	//=============================== final values
	private int width = 950;
	private int height = 1050;
	
	private JTextField wText = new JTextField(Integer.toString(width));
	private JTextField hText = new JTextField(Integer.toString(height));
	
	private final int iconSize = 30;
	private final int fontSize = 18;
	//=============================== board, miniImage and timer (statics)
	private static LinkedList<Board> boards = new LinkedList<Board>();
	private static Container container;
	

	private final Puzzle selfP;
	
	public Puzzle(){
		//=============================== set window values
		selfP = this;
		this.setTitle("Puzzle");
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(width, height));
		this.setResizable(true);
		this.setIconImage(windowIcon);
		container = this.getContentPane();
		//=============================== load all images and icons
		newGameIcon = ImageLoader.loadImage("newIcon.png");
		rngIcon = ImageLoader.loadImage("rngIcon.png");
		saveIcon = ImageLoader.loadImage("saveIcon.png");
		restoreIcon = ImageLoader.loadImage("restoreIcon.png");
		
		blocked32 = new ImageIcon(blocked.getScaledInstance(225, 112, Image.SCALE_SMOOTH));
		blocked72 = new ImageIcon(blocked.getScaledInstance(150, 75, Image.SCALE_SMOOTH));
		blocked128 = new ImageIcon(blocked.getScaledInstance(112, 56, Image.SCALE_SMOOTH));
		
		//=============================== Initialize all toolbar items
		
		newGameButton = new JButton(new ImageIcon(newGameIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		newGameButton.setName("new");
		newGameButton.setToolTipText("Neues Spielfeld öffnen");
		newGameButton.addActionListener(new MenuButtonListener());
		
		rngButton = new JButton(new ImageIcon(rngIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		rngButton.setName("random");
		rngButton.setToolTipText("Zufälligen Teil des aktuellen Spielfelds lösen");
		rngButton.addActionListener(new MenuButtonListener());
		
		saveButton = new JButton(new ImageIcon(saveIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		saveButton.setName("save");
		saveButton.setToolTipText("Alle Spielfelder speichern");
		saveButton.addActionListener(new MenuButtonListener());
		
		restoreButton = new JButton(new ImageIcon(restoreIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		restoreButton.setName("restore");
		restoreButton.setToolTipText("Letzten Speicherstand wiederherstellen");
		restoreButton.addActionListener(new MenuButtonListener());
		
		
		//=============================== initialize panels		
		puzzleArea = new JPanel();
		puzzleArea.setOpaque(true);
		puzzleArea.setBackground(Color.BLACK);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(800, 120));
		tabbedPane.addTab("<Teamname + Bildgröße>", puzzleArea);
		
		ButtonTabComponent btc = new ButtonTabComponent(tabbedPane, boards);
		tabbedPane.setTabComponentAt(0, btc);
		
		//=============================== add buttons to the toolbar
		toolbar.add(newGameButton);
		toolbar.add(rngButton);
		toolbar.add(saveButton);
		toolbar.add(restoreButton);
		
		owner.setFont(new Font("Georgia", Font.BOLD, fontSize));
		owner.setForeground(Color.BLACK);
		
		announcer.setFont(new Font("Georgia", Font.BOLD, fontSize));
		announcer.setForeground(Color.BLACK);
		
		/*
		ActionListener textAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				width = Integer.parseInt(wText.getText());
				height = Integer.parseInt(hText.getText());	
				this.
			}
		};
		
		wText.addActionListener(new IconTimerLitener());
		hText.addActionListener(new IconTimerLitener());
		*/
		
		
		wText.addActionListener(new MenuButtonListener());
		hText.addActionListener(new MenuButtonListener());
		wText.setVisible(false);
		hText.setVisible(false);
		
		toolbar.addSeparator(new Dimension(20, 20));
		toolbar.add(announcer);
		toolbar.add(owner);
		
		toolbar.add(wText);
		toolbar.add(hText);
		
		//=============================== add components to panels
		puzzleArea.add(new JLabel(new ImageIcon(def)));
		
		container.add(toolbar, BorderLayout.NORTH);
//		container.add(puzzelArea, BorderLayout.CENTER);
		container.add(tabbedPane, BorderLayout.CENTER);
		
		
		//container.add(timeMoves, BorderLayout.CENTER);
	}
	class MenuButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object comp = e.getSource();
			if(comp instanceof JButton){
				JButton button = (JButton)comp;
					

				if(button.getName().equals("new")){
					new StartPuzzle();
				
				} else if(button.getName().equals("random")) {
					if(boards.size() > 0) {
						Board board = boards.get(tabbedPane.getSelectedIndex());					
						board.solveRandom();
						String lastSolved = board.getLastSolved();
						if(lastSolved == "") {
							announcer.setText("");
						} else {
							announcer.setText(feld + board.getLastSolved() + solveDisclaimer);
						}
						
					}
				} else if(button.getName().equals("save")) {
					SaveManager sm = new SaveManager(tabbedPane, boards);
					announcer.setText(sm.save());
				} else if(button.getName().equals("restore")) {
					SaveManager sm = new SaveManager(tabbedPane, boards);
					announcer.setText(sm.restore());
				}
			
			}else if(comp instanceof JTextField){
				width = Integer.parseInt(wText.getText());
				height = Integer.parseInt(hText.getText());	
				selfP.setSize(width, height);
				System.out.println(width + "   " + height);
				
			}
		}
		
	}
	public static void start(String imgPath, BufferedImage img, int picSize, String title, int[] savedResults) {
		
		Board board = new Board(picSize, img, imgPath, savedResults);
		boards.add(board);
		if(tabbedPane.getTabCount() == 1)
			tabbedPane.remove(puzzleArea);
		
		tabbedPane.addTab(title, board);		
		int i = tabbedPane.getTabCount() - 1;
		ButtonTabComponent btc = new ButtonTabComponent(tabbedPane, boards);
		tabbedPane.setTabComponentAt(i, btc);
		tabbedPane.setSelectedIndex(i);
		tabbedPane.validate();
	}
	
	public static Container getContainer() {
		return container;		
	}
}