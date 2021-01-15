import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* 
 *  Program: Edytor Grafów
 *     Plik: Graphs.java
 *           
 *  Klasa Graphs reprezentuje graf na p³aszczyŸnie.   
 *    Autor: Kamil Osiñski
 *    Nr indeksu : 241387
 *    Data:  12.12.2018 r.
 */

public class Graphs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Nodes> nodes;
	private List<Lines> lines;
	
	public Graphs() {
		this.nodes = new ArrayList<Nodes>();
		this.lines = new ArrayList<Lines>();
	}
	
	public void addNode(Nodes node){
		nodes.add(node);
	}
	
	public void removeNode(Nodes node){
		nodes.remove(node);
	}
	
	public Nodes[] getNodes(){
		Nodes [] array = new Nodes[0];
		return nodes.toArray(array);
	}
	
	public void addLine(Lines line) {
		lines.add(line);
	}
	
	public void removeLine(Lines line) {
		lines.remove(line);
	}
	
	public Lines[] getLines() {
		Lines [] tab = new Lines[0];
		return lines.toArray(tab);
	}
	
	public void draw(Graphics g){
		for(Lines line : lines) {
			line.drawLine(g);
		}
		for(Nodes node : nodes){
			node.drawNode(g);
		}
		
	}
	
	
	
	
}
