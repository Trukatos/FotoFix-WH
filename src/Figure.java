import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Figure extends JButton implements ActionListener{
	private int posX;
	private int posY;
	private final int solutionPosX;
	private final int solutionPosY;
	private final ImageIcon solved;
	private int state;
	private final int HIDDEN = 0;
	private final int BLOCKED = 1;
	private final int SOLVED = 2;
	private final String numLabel;
	private int fontSize = 32;
	
	public Figure(int solPosX, int solPosY, ImageIcon figure, int picSize, int numCols){
		switch(picSize) {
			case 32:
				fontSize = 60;
				break;
			case 64:
				fontSize = 40;
				break;
			case 128:
				fontSize = 20;
				break;
		}
		this.solutionPosX = solPosX;
		this.solutionPosY = solPosY;
		this.posX = solPosX;
		this.posY = solPosY;
		this.solved = figure;
		this.state = HIDDEN;
		this.numLabel = Integer.toString((posY + 1) + posX * numCols);
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Georgia", Font.BOLD, fontSize));
		this.setText(numLabel);
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
	
	public boolean isHidden() {
		return this.state == HIDDEN;
	}
	
	public String getNumLabel() {
		return numLabel;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) throws IllegalArgumentException {
			
		switch(state) {
			case HIDDEN:
				hide();
				break;
			case SOLVED:
				solve();
				break;
			case BLOCKED:
				block();
				break;
			default:
				throw new IllegalArgumentException("Invalid State for Figure: " + state);
		}
	}
	
	public void solve() {
		this.setIcon(solved);
		this.setText("");
		this.state = SOLVED;
	}
	
	public void hide() {
		this.setIcon(null);
		this.setForeground(Color.BLACK);
		this.setText(numLabel);
		this.state = HIDDEN;
	}
	
	public void block() {
		this.setIcon(null);
		this.setForeground(Color.RED);
		this.setText("X");
		this.state = BLOCKED;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		toggle();
	}
	
	private void toggle(){
		switch(this.state) {
			case HIDDEN:
				solve();
				break;
			case SOLVED:
				block();
				break;
			case BLOCKED:
				hide();
		}
	}
}
