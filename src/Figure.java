import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Figure extends JButton implements ActionListener{
	private int posX;
	private int posY;
	private final int solutionPosX;
	private final int solutionPosY;
	private int dimension;
	private final ImageIcon blank;
	private final ImageIcon solved;
	
	public Figure(int solPosX, int solPosY, ImageIcon figure, ImageIcon blank, int dimension){
		this.dimension = dimension;
		this.solutionPosX = solPosX;
		this.solutionPosY = solPosY;
		this.posX = solPosX;
		this.posY = solPosY;
		this.blank = blank;
		this.solved = figure;
		
		this.setIcon(blank);
		this.setPreferredSize(new Dimension(figure.getIconWidth(), figure.getIconHeight()));
		this.addActionListener(this);
		
	}

	public int getPosX() {
		return posX;
	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosY() {
		return posY;
	}

	public int getSolutionPosX() {
		return solutionPosX;
	}

	public int getSolutionPosY() {
		return solutionPosY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Move();
	}
	private void Move(){
		Cell[][] board = Board.board;
		try{
			this.setIcon(solved);
			return;

		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
	}
	private void CheckAnswer(){
		Figure figure = null;
		for(int i = 0; i<dimension; i++){
			for(int j = 0; j<dimension; j++){
				
				figure = Board.board[i][j].getFigure();
				if(figure == null)
					continue;
				
				if(figure.getPosX() != figure.getSolutionPosX() || figure.getPosY() != figure.getSolutionPosY()){
					return;
				}
			}	
		}
		Puzzle.getTimer().stop();
		JOptionPane.showMessageDialog(new JPanel(), "Congratulations", "Puzzle completed", JOptionPane.INFORMATION_MESSAGE);
	}
}
