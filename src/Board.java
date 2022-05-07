import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel{

	public static Cell[][] board;
	
	private ArrayList<Cell> completeBoard = new ArrayList<Cell>();
	public final int dimensionX, dimensionY;
	private int x, y;
	private final int figureWidth, figureHeight;
	private JLabel label;
	private String lastSolved = "";
	private int picSize;
	private String imgPath;
	
	public Board(int picSize, BufferedImage puzzle, String imgPath, int[] savedResults) {
		
		this.picSize = picSize;
		this.imgPath = imgPath;
		
		switch(picSize) {
		case 32:
			this.dimensionX = 4;
			this.dimensionY = 8;
			break;
		case 72:
			this.dimensionX = 6;
			this.dimensionY = 12;
			break;
		case 128:
			this.dimensionX = 8;
			this.dimensionY = 16;
			break;
		default:
			this.dimensionX = 4;
			this.dimensionY = 8;
			break;
		}
		
		this.setPreferredSize(new Dimension(dimensionX, dimensionY));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		this.setBackground(Color.BLACK);
		
		board = new Cell[dimensionX][dimensionY];
		x = 0;
		y = 0;
		figureWidth = puzzle.getWidth()/dimensionX;
		figureHeight = puzzle.getHeight()/dimensionY;
		/*
		String params = "dimX = " + dimensionX + "   dimY = " + dimensionY + "\n" +
						"puzzleW = " + puzzle.getWidth() + "   puzzleH = " + puzzle.getHeight() + "\n" +
						"figureW = " + figureWidth + "   figureH = " + figureHeight;

		System.out.println(params);
		*/
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//this.setLayout(new GridLayout(dimension-1, dimension));
		
		for(int i=0; i<dimensionY; i++){
			for(int j=0; j<dimensionX; j++){
				/*
				if(i == dimension - 1 && j == dimension -1){			
					continue;
				}
				*/
				completeBoard.add(new Cell(i, j, new Figure(i, j, new ImageIcon(puzzle.getSubimage(x, y, figureWidth, figureHeight)), picSize, dimensionX)));
				
				x += figureWidth;
			}
			x = 0;
			y += figureHeight;
		}
		messBoard();
		
		remover();
		if(savedResults != null) {
			
			int size = completeBoard.size();
			for(int i = 0; i < size; i++) {
				completeBoard.get(i).getFigure().setState(savedResults[i]);
			}
			updateBoard();
		}
	}
	
	public void messBoard(){
		
		//Random randomGenerator = new Random();
		//ArrayList<Cell> cellStore = new ArrayList<Cell>(completeBoard);
		int index = 0;
		for(int i = 0; i<dimensionX; i++){
			for(int j = 0; j<dimensionY; j++){
				/*
				if(i == dimension-1 && j == dimension-1){
					board[i][j] = new Cell(i, j);
					continue;
				}
				*/
				//int randomIndex = randomGenerator.nextInt(completeBoard.size());
				//completeBoard.get(randomIndex).getFigure().setPos(i, j);
				completeBoard.get(index).getFigure().setPos(i, j);
				//board[i][j] = new Cell(i, j, completeBoard.get(randomIndex).getFigure());
				board[i][j] = new Cell(i, j, completeBoard.get(index).getFigure());
				index++;
				//completeBoard.remove(randomIndex);

			}
		}
		//completeBoard = cellStore;
		
		remover();
	}
	public void updateBoard(){
		
		for(int i = 0; i<dimensionX; i++){
			for(int j = 0; j<dimensionY; j++){	
				if(board[i][j].getFigure() == null){
					label = new JLabel();
					label.setPreferredSize(new Dimension(figureWidth, figureHeight));
					this.add(label);
					continue;
				}
				this.add(board[i][j].getFigure());
			}
		}
		Puzzle.getContainer().validate();
	}
	public void remover() {
		this.removeAll();
		updateBoard();
	}
	
	public void solveRandom() {
		Random rng = new Random();
		ArrayList<Figure> hidden = getHiddenFigures();
		if(hidden.size() == 0) {
			lastSolved = "";
			return;
		}
		int index = rng.nextInt(hidden.size());
		hidden.get(index).solve();	
		lastSolved = hidden.get(index).getNumLabel();
	}
	
	private ArrayList<Figure> getHiddenFigures() {
		ArrayList<Figure> hidden = new ArrayList<Figure>();
		Figure f;
		for (int i = 0; i < completeBoard.size(); i++) {
			f = completeBoard.get(i).getFigure();
			if(f.isHidden())
				hidden.add(f);
		}
		return hidden;
		
	}
	
	public String getLastSolved() {
		return lastSolved;
	}
	
	public int getPicSize() {
		return picSize;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	
	public ArrayList<Cell> getCompleteBoard() {
		return completeBoard;
	}
}
