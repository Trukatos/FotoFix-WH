import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class SaveManager {
	
	/* EXAMPLE SAVE FILE
	  	1
		Team 1 Small/Big
		C:\Users\Trukatos\Desktop\AF 5.png
		2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

	 */
	
	private LinkedList<Board> boards;
	private JTabbedPane tabbedPane;
	private Path path;
	
	public SaveManager(JTabbedPane tabbedPane, LinkedList<Board> boards) {
		this.tabbedPane = tabbedPane;
		this.boards = boards;
		this.path = Paths.get(System.getProperty("user.dir") + "/FotoFix Speicherstand.txt");
	}
	
	public String save() {
		if(boards.size() == 0)
			return "Es gibt nichts zu speichern!";
		ArrayList<String> lines = new ArrayList<String>();
		int tabCount = tabbedPane.getTabCount();
		lines.add(Integer.toString(tabCount));
		for(int i = 0; i < tabCount; i++) {
			lines.add(tabbedPane.getTitleAt(i));
			Board b = boards.get(i);
			lines.add(b.getImgPath());
			ArrayList<Cell> l = b.getCompleteBoard();
			String s = Integer.toString(l.get(0).getFigure().getState());
			for(int j = 1; j < l.size(); j++) {
				s += ", " + Integer.toString(l.get(j).getFigure().getState());
			}
			lines.add(s);
		}
		try {
			if(Files.notExists(path))
				Files.createFile(path);
		    Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Speicherstand wurde erstellt!";
	}
	
	public String restore() {
		try {
			if(Files.exists(path)) {
				tabbedPane.removeAll();
				boards.removeAll(boards);
				ArrayList<String> lines = new ArrayList<String>(Files.readAllLines(path));
				int numTabs = Integer.parseInt(lines.get(0));
				int index = 1;
				for(int i = 0; i < numTabs; i++) {
					String title = lines.get(index);
					index++;
					String imgPath = lines.get(index);
					index++;
					String states = lines.get(index);
					index++;
					String[] stateArrayStrings = states.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
					int[] savedResults = new int[stateArrayStrings.length];

					for (int j = 0; j < stateArrayStrings.length; j++) {
					    try {
					        savedResults[j] = Integer.parseInt(stateArrayStrings[j]);
					    } catch (NumberFormatException nfe) {
					        //NOTE: write something here if you need to recover from formatting errors
					    }
					}
					File file = new File(imgPath);
					BufferedImage image;
					try {
						image = ImageIO.read(file);
						BufferedImage puzzleImage = ImageResizer.resizeImage(image, 900, 900);
						Puzzle.start(imgPath, puzzleImage, savedResults.length, title, savedResults);
					} catch (IOException e1) {
						System.out.println("Kein Bild erkannt! Wähle ein Bild im Format jpg oder png");
					}
				}
				return "Speicherstand wurde geladen!";
			} else
				return "Keinen Speicherstand gefunden!";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "Unerwarteter Fehler!";
		}
	}
}
