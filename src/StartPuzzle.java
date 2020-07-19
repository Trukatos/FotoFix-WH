import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private JLabel puzzleImage = new JLabel("Select an image and difficulty level");
	private JFileChooser filechooser;
	
	private BufferedImage image = null;
	private int level = 0;
	private Image windowIcon = ImageLoader.loadImage("/puzzleIcon.png");
	
	public StartPuzzle(){
		setTitle("New puzzle");
		setSize(270, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		main.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setIconImage(windowIcon);
		
		open = new JButton(new ImageIcon(ImageLoader.loadImage("/openImage.jpg").getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
		open.setName("Open");
		open.addActionListener(this);
		start = new JButton("START");
		start.setName("Start");
		start.setFont(new Font("Georgia", Font.BOLD, 40));
		start.addActionListener(this);
		
		main.add(puzzleImage);
		main.add(open);
		
		main.add(start);
		add(main);
		setVisible(true);
		
		System.out.println(start.getWidth() + " " + start.getHeight());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton)e.getSource();
		if(button.getName().equals("Open")){
			
			filechooser = new JFileChooser();
			int action = filechooser.showOpenDialog(null);
			if(action == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				try {
					image = ImageIO.read(file);
					open.setIcon( new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
					
				} catch (IOException e1) {
					System.out.println("You must select an image");
				}
			}
		}else if(button.getName().equals("Start")){
			level = 6;
			if(image == null)
				return;
			BufferedImage puzzelImage = ImageResizer.resizeImage(image, 400, 800);
			BufferedImage miniImage = ImageResizer.resizeImage(image, 200, 400);
			
			Puzzle.start(puzzelImage, level, miniImage);
			this.dispose();
		}
		
	}
}
