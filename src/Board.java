import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel{

	public static Cell[][] board;
	
	private ArrayList<Cell> completeBoard = new ArrayList<Cell>(); 
	public final int dimensionX, dimensionY;
	private int x, y;
	private final int figureWidth, figureHeight;
	private JLabel label;
	public Board(int dimensionX, int dimensionY, BufferedImage puzzle){
		this.setPreferredSize(new Dimension(dimensionX, dimensionY));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		this.setBackground(Color.BLACK);
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		board = new Cell[dimensionX][dimensionY];
		x = 0;
		y = 0;
		figureWidth = puzzle.getWidth()/dimensionX;
		figureHeight = puzzle.getHeight()/dimensionY;

		System.out.println(figureWidth + "   " + figureHeight);
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//this.setLayout(new GridLayout(dimension-1, dimension));
		
		
		for(int i=0; i<dimensionY; i++){
			for(int j=0; j<dimensionX; j++){
				/*
				if(i == dimension - 1 && j == dimension -1){			
					continue;
				}
				*/
				completeBoard.add(new Cell(i, j, new Figure(i, j, new ImageIcon(puzzle.getSubimage(x, y, figureWidth, figureHeight)), dimensionX)));
				
				x += figureWidth;
			}
			x = 0;
			y += figureHeight;
		}
		messBoard();
		
		remover();
		
	}
	public void messBoard(){
		
		Random randomGenerator = new Random();
		ArrayList<Cell> cellStore = new ArrayList<Cell>(completeBoard);
		int index = 0;
		for(int i = 0; i<dimensionX; i++){
			for(int j = 0; j<dimensionY; j++){
				/*
				if(i == dimension-1 && j == dimension-1){
					board[i][j] = new Cell(i, j);
					continue;
				}
				*/
				int randomIndex = randomGenerator.nextInt(completeBoard.size());
				//completeBoard.get(randomIndex).getFigure().setPos(i, j);
				completeBoard.get(index).getFigure().setPos(i, j);
				//board[i][j] = new Cell(i, j, completeBoard.get(randomIndex).getFigure());
				board[i][j] = new Cell(i, j, completeBoard.get(index).getFigure());
				index++;
				//completeBoard.remove(randomIndex);

			}
		}
		completeBoard = cellStore;
		
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
	public void remover(){
		this.removeAll();
		updateBoard();
	}
}
