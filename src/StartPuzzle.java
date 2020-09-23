import java.awt.Dimension;
import java.awt.FileDialog;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StartPuzzle extends JFrame implements ActionListener{
	private JPanel main = new JPanel();
	private JButton open, start;
	private JRadioButton S, M, L;
	private JPanel sizeButtonsPanel = new JPanel();
	private JLabel sizeDescriptor;
	private JLabel descriptorLabel = new JLabel("<html><body>&nbsp&nbsp&nbsp Wähle Bild und<br>Schwierigkeitsgrad</body></html>", SwingConstants.CENTER);
	private JLabel picTitleLabel = new JLabel("Bildbeschreibung:", SwingConstants.CENTER);
	private JTextField picTitleField = new JTextField("<Teamname>");
	private JFileChooser fileChooser;
	private int picSize = 32;
	private String picLabel = "S";
	private BufferedImage image = null;
	private Font font = new Font("Georgia", Font.BOLD, 40);
	private Image windowIcon = ImageLoader.loadImage("/puzzleIcon.png");
	private String imgPath = "";  //imgPath is needed for the save file
	
	public StartPuzzle(){
		setTitle("Neues Pic");
		setSize(500, 900);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		main.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 10));
		this.setIconImage(windowIcon);
		
		
		
		open = new JButton(new ImageIcon(ImageLoader.loadImage("/openImage.jpg").getScaledInstance(440, 400, Image.SCALE_DEFAULT)));
		open.setPreferredSize(new Dimension(440, 400));
		open.setName("Open");
		open.addActionListener(this);
		
		sizeDescriptor = new JLabel(picLabel + "-Pic: " + picSize + " Felder");
		sizeDescriptor.setFont(font);
		
		descriptorLabel.setFont(font);
		
		//horizontal gap = 20; vertical gap = 0
		sizeButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		
		S = new JRadioButton("S");
		S.setName("S");
		S.setFont(font);
		S.setSelectedIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonChecked.png")));
		S.setIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonUnchecked.png")));
		S.setSelected(true);
		S.addActionListener(this);
		
		M = new JRadioButton("M");
		M.setName("M");
		M.setFont(font);
		M.setSelectedIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonChecked.png")));
		M.setIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonUnchecked.png")));
		M.addActionListener(this);
		
		L = new JRadioButton("L");
		L.setName("L");
		L.setFont(font);
		L.setSelectedIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonChecked.png")));
		L.setIcon(new ImageIcon(ImageLoader.loadImage("/radioButtonUnchecked.png")));
		L.addActionListener(this);
		
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(S);
        bgroup.add(M);
        bgroup.add(L);
        
		sizeButtonsPanel.add(S);
		sizeButtonsPanel.add(M);
		sizeButtonsPanel.add(L);
		
		start = new JButton("S T A R T");
		start.setPreferredSize(new Dimension(440, 100));
		start.setName("Start");
		start.setFont(new Font("Georgia", Font.BOLD, 80));		
		start.addActionListener(this);
		
		picTitleLabel.setFont(font);
		picTitleField.setFont(font);
		
		main.add(descriptorLabel);
		main.add(open);
		main.add(sizeDescriptor);
		main.add(sizeButtonsPanel);
		main.add(picTitleLabel);
		main.add(picTitleField);
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
			picSize = 72;
			picLabel = "M";
			sizeDescriptor.setText(picLabel + "-Pic: " + picSize + " Felder");
			
		} else if(button.getName().equals("L")){
			picSize = 128;
			picLabel = "L";
			sizeDescriptor.setText(picLabel + "-Pic: " + picSize + " Felder");
			
		} else if(button.getName().equals("Open")){			
			/*
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setPreferredSize(new Dimension(1200, 800));
			//fileChooser.setFileSystemView();
			 * 
			 */
			FileDialog fd = new FileDialog(this, "Wähle das Bild", FileDialog.LOAD);
			fd.setDirectory("Desktop");
			fd.setPreferredSize(new Dimension(1200, 800));
			fd.setVisible(true);
			//fd.
			//int action = fileChooser.showOpenDialog(null);
		//	if(action == JFileChooser.APPROVE_OPTION){
				//File file = fileChooser.getSelectedFile();
			imgPath = fd.getDirectory() + fd.getFile();
			//imgPath is needed for the save file
			File file = new File(fd.getDirectory() + fd.getFile());
			try {
				image = ImageIO.read(file);
				open.setIcon( new ImageIcon(image.getScaledInstance(440, 400, Image.SCALE_DEFAULT)));
				
			} catch (IOException e1) {
				System.out.println("Kein Bild erkannt! Wähle ein Bild im Format jpg oder png");
			}
		//	}
			
		} else if(button.getName().equals("Start")){
			if(image == null)
				return;
			BufferedImage puzzleImage = ImageResizer.resizeImage(image, 900, 900);
			
			String title = picTitleField.getText() + " " + picLabel;
			/*
			String titleSuffix = "";
			switch(picLabel) {
			case "S":
				titleSuffix = "Small";
				break;
			case "M":
				titleSuffix = "Medium";
				break;
			case "L":
				titleSuffix = "Large";
				break;
			}
			title += " " + titleSuffix;
			*/
			Puzzle.start(imgPath, puzzleImage, picSize, title, null);
			this.dispose();
		}
		
	}
}
