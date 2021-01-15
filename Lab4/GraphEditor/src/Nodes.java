import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

/* 
 *  Program: Edytor Grafów
 *     Plik: Nodes.java
 *           
 *  Klasa Nodes reprezentuje wierzcho³ki grafu na p³aszczyŸnie.   
 *    Autor: Kamil Osiñski
 *    Nr indeksu : 241387
 *     Data:  12.12.2018 r.
 */


public class Nodes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	protected String name;
	
	protected int r;
	
	private Color color;
	
	public Nodes(int x, int y, String n) {
		this.x=x;
		this.y=y;
		this.r=10;
		this.color=Color.blue;	
		this.name=n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean MouseOver(int mx, int my) {
		if((mx-x)*(mx-x)+(my-y)*(my-y)<=r*r)
			return true;
		else
			return false;
	}

	
	public void drawNode(Graphics gr) {
		gr.setColor(color);
		gr.fillOval(x-r, y-r, 2*r, 2*r);
		gr.setColor(Color.BLACK);
		Graphics2D g2= (Graphics2D) gr;
		g2.setStroke(new BasicStroke(1));
		gr.drawOval(x-r, y-r, 2*r, 2*r);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 15));
		int l=name.length()*4;
		gr.drawString(name, x-l, y+4);
		
		 
	}

	@Override
	public String toString() {
		return name+"(" +x + ", " +y+")";
	}
}
