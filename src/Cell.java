
public class Cell {
	private final int x, y;
	private Figure figure;
	private final Figure solvedFigure;
	
	public Cell(int x, int y, Figure figure){
		this.x = x;
		this.y = y;
		this.figure = figure;
		this.solvedFigure = figure;
		
	}
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		figure = null;
		solvedFigure = null;
	}
	public Figure getFigure(){
		return figure;
	}
	public void setFigure(Figure figure){
		this.figure = figure;
	}
	public Figure getSolvedFigure() {
		return solvedFigure;
	}
	public void solveFigure() {
		figure = solvedFigure;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
