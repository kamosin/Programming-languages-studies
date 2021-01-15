import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

/* 
 *  Program: Edytor Grafów
 *     Plik: Lines.java
 *           
 *  Klasa Lines reprezentuje krawêdzie grafu na p³aszczyŸnie.   
 *    Autor: Kamil Osiñski
 *    Nr indeksu: 241387
 *     Data:  12.12.2018 r.
 */


public class Lines implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;
	
	private Color color;
	
	Nodes n1= new Nodes(x1, y1,"");
	Nodes n2= new Nodes(x2, y2,"");
	
	
	public Lines(Nodes node1, Nodes node2) {
		//x1=node1.x;
		//y1=node1.y;
		//x2=node2.x;
		//y2=node2.y;
		this.n1=node1;
		this.n2=node2;
		this.color=Color.BLACK;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public boolean isMouseOverLine(int mx, int my) {
		double A=0;
		double C=0;
		if(n1.x==n2.x) {
			if(n1.y==n2.y) {
				return false;
			}
			else if(n1.y>n2.y){
		while(my>n2.y && my<n1.y) {
		A=1;
		C=n1.x;
		return Math.abs((double)mx-C)<=3;
		}
			}
			else {
				while(my>n1.y && my<n2.y) {
					A=1;
					C=n1.x;
					return Math.abs((double)mx-C)<=3;
					}
			}
		}
		else if (n1.y==n2.y) {
			if(n1.x>n2.x) {
				while(mx>n2.x && mx<n1.x) {
					C=n1.y;
					return Math.abs((double)my-(double)C)<=3;
				}
			}
			else {
				while(mx>n1.x && mx<n2.x) {
					C=n1.y;
					return Math.abs((double)my-(double)C)<=3;
				}
			}
		}
		else if(n1.x>n2.x){
			while(mx>n2.x && mx<n1.x) {
			A=(((double)n1.y-(double)n2.y)/((double)n1.x-(double)n2.x));
			C=(double)n1.y-(((double)n1.y-(double)n2.y)/((double)n1.x-(double)n2.x))*(double)n1.x;
			return Math.abs(A*(double)mx-(double)my+C)/Math.sqrt(A*A+1)<=3;
			}
		}
		else {
			while(mx>n1.x && mx<n2.x) {
				A=(((double)n1.y-(double)n2.y)/((double)n1.x-(double)n2.x));
				C=(double)n1.y-(((double)n1.y-(double)n2.y)/((double)n1.x-(double)n2.x))*(double)n1.x;
				return Math.abs(A*(double)mx-(double)my+C)/Math.sqrt(A*A+1)<=3;
			}
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		return "[ "+n1.getName()+"("+n1.getX()+", "+n1.getY()+")"+"==>"+n2.getName()+"("+n2.getX()+", "+n2.getY()+")]";
	}

	public void drawLine(Graphics g) {
		g.setColor(color);
		Graphics2D g2= (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(n1.x, n1.y, n2.x, n2.y);
	}

	

}
