import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StartPuzzle extends JFrame implements ActionListener{
	private JPanel main = new JPanel();
	private JButton open, start;
	private JRadioButton S, M, L;
	private JPanel sizeButtonsPanel = new JPanel();
	private JLabel sizeDescriptor;
	private JLabel puzzleImage = new JLabel("Wähle ein Bild und den Schwierigkeitsgrad");
	private JFileChooser filechooser;
	private int picSize = 32;
	private String picLabel = "S";
	private BufferedImage image = null;
	private Image windowIcon = ImageLoader.loadImage("/puzzleIcon.png");
	
	public StartPuzzle(){
		setTitle("Neues Pic");
		setSize(270, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		main.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setIconImage(windowIcon);
		
		
		open = new JButton(new ImageIcon(ImageLoader.loadImage("/openImage.jpg").getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
		open.setName("Open");
		open.addActionListener(this);
		
		sizeDescriptor = new JLabel(picLabel + "-Pic: " + picSize + " Felder");
		sizeDescriptor.setFont(new Font("Georgia", Font.BOLD, 20));
		
		//horizontal gap = 20; vertical gap = 0
		sizeButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		S = new JRadioButton("S");
		S.setName("S");
		S.setFont(new Font("Georgia", Font.BOLD, 20));
		S.setSelected(true);
		S.addActionListener(this);
		
		M = new JRadioButton("M");
		M.setName("M");
		M.setFont(new Font("Georgia", Font.BOLD, 20));
		M.addActionListener(this);
		
		L = new JRadioButton("L");
		L.setName("L");
		L.setFont(new Font("Georgia", Font.BOLD, 20));
		L.addActionListener(this);
		
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(S);
        bgroup.add(M);
        bgroup.add(L);
        
		sizeButtonsPanel.add(S);
		sizeButtonsPanel.add(M);
		sizeButtonsPanel.add(L);
		
		start = new JButton("START");
		start.setName("Start");
		start.setFont(new Font("Georgia", Font.BOLD, 40));
		start.addActionListener(this);
		
		main.add(puzzleImage);
		main.add(open);
		main.add(sizeDescriptor);
		main.add(sizeButtonsPanel);
		main.add(start);
		add(main);
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*AbstractButton is a parent class to JButton and JRadioButton
		 * and is used to get the clicked button's name  
		 * for the following if statements 
		 * for all buttons of this windows in one ActionListener
		 */
		AbstractButton button = (AbstractButton)e.getSource();
		
		
		if(button.getName().equals("S")){
			picSize = 32;
			picLabel = "S";
			sizeDescriptor.setText(picLabel + "-Pic: " + picSize + " Felder");
			
		} else if(button.getName().equals("M")){
			picSize = 64;
			picLabel = "M";
			sizeDescriptor.setText(picLabel + "-Pic: " + picSize + " Felder");
			
		} else if(button.getName().equals("L")){
			picSize = 128;
			picLabel = "L";
			sizeDescriptor.setText(picLabel + "-Pic: " + picSize + " Felder");
			
		} else if(button.getName().equals("Open")){			
			filechooser = new JFileChooser();
			int action = filechooser.showOpenDialog(null);
			if(action == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				try {
					image = ImageIO.read(file);
					open.setIcon( new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
					
				} catch (IOException e1) {
					System.out.println("Kein Bild erkannt! Wähle ein Bild im Format jpeg oder png");
				}
			}
			
		} else if(button.getName().equals("Start")){
			if(image == null)
				return;
			BufferedImage puzzelImage = ImageResizer.resizeImage(image, 600, 800);
			BufferedImage miniImage = ImageResizer.resizeImage(image, 200, 400);
			
			Puzzle.start(puzzelImage, picSize, miniImage);
			this.dispose();
		}
		
	}
}
