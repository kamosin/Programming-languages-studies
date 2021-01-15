import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/* 
 *  Program: Edytor Grafów
 *     Plik: MainEditor.java
 *           
 *    Klasa MainEditor implementuje podstawowe funkcjonalnoœci
 *    graficznego edytora grafu   
 *    Autor: Kamil Osiñski
 *    Nr indeksu : 241387
 *    Data:  12.12.2018 r.
 */

public class MainEditor extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 

	protected Graphs graph;
	
	private int MouseX=0;
	private int MouseY=0;
	private boolean MbuttonLeft=false;
	@SuppressWarnings("unused")
	private boolean MbuttonRight=false;
	protected int mCursor=Cursor.DEFAULT_CURSOR;
	
	protected Nodes nodeUnderCursor=null;
	protected Nodes nodeConnecting=null;
	protected Lines lineUnderCursor=null;
	
	public MainEditor() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
	
	
	public Graphs getGraph() {
		return graph;
	}
	
	public void setGraph(Graphs graph) {
		this.graph = graph;
	}
	
	private Nodes findNode(int mx, int my) {
		for(Nodes n : graph.getNodes()) {
			if(n.MouseOver(mx, my)) {
				return n;
			}
			
		}
		return null;
	}
	
	private Nodes findNode(MouseEvent e) {
		return findNode(e.getX(), e.getY());
	}
	
	private Lines findLine(int mx, int my) {
		for(Lines l : graph.getLines()) {
			if(l.isMouseOverLine(mx, my))
				return l;
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private Lines findLine(MouseEvent ev) {
		return findLine(ev.getX(),ev.getY());
	}
	
	protected void setMouseCursor(MouseEvent event) {
		nodeUnderCursor = findNode(event);
		lineUnderCursor = findLine(event);
		
		if(nodeUnderCursor!=null ) {
			mCursor=Cursor.HAND_CURSOR;
		}
		else if(lineUnderCursor!=null) {
			mCursor=Cursor.CROSSHAIR_CURSOR;
		}
		
		else if(MbuttonLeft) {
			mCursor=Cursor.MOVE_CURSOR;
		}
		else if(nodeConnecting!=null){
			mCursor=Cursor.WAIT_CURSOR;
		}
		else {
			mCursor=Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mCursor));
		MouseX=event.getX();
		MouseY=event.getY();
	}
	
	protected void setMouseCursor() {
		nodeUnderCursor = findNode(MouseX,MouseY);
		lineUnderCursor = findLine(MouseX,MouseY);
		
		if(nodeUnderCursor!=null) {
			mCursor=Cursor.HAND_CURSOR;
		}
		
		else if(lineUnderCursor!=null) {
			mCursor=Cursor.CROSSHAIR_CURSOR;
		}
		
		else if(MbuttonLeft) {
			mCursor=Cursor.MOVE_CURSOR;
		}
		else if(nodeConnecting!=null){
			mCursor=Cursor.WAIT_CURSOR;
		}
		else {
			mCursor=Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mCursor));
	}
	
	private void moveNode(int mx, int my, Nodes no) {
		no.setX(no.getX()+mx);
		no.setY(no.getY()+my);
		for(Lines line :graph.getLines()) {
			if(line.x1==no.getX()) {
				if(line.y1==no.getY()) {
					line.setX1(no.getX()+mx);
					line.setY1(no.getY()+my);
					
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void moveNodes(int mx, int my) {
		for(Nodes node: graph.getNodes()) {
			moveNode(mx, my, node);
		}
		
	}
	private void moveLines(int dx, int dy) {
		for(Lines line :graph.getLines()) {
			line.setX1(line.getX1()+dx);
			line.setX2(line.getX2()+dx);
			line.setY1(line.getY1()+dy);
			line.setY2(line.getY2()+dy);
		}
	}
	
	private void moveLine(int dx, int dy, Lines li) {
		li.setX1(li.getX1()+dx);
		li.setX2(li.getX2()+dx);
		li.setY1(li.getY1()+dy);
		li.setY2(li.getY2()+dy);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (graph==null) return;
		graph.draw(g);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int d;
		if(e.isShiftDown()==true) {
			d=10;
		}
		else {
			d=1;
		}
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			moveNodes(0, -d);
			break;
		
		case KeyEvent.VK_DOWN:
			moveNodes(0, d);
			break;
		
		case KeyEvent.VK_LEFT:
			moveNodes(-d, 0);
			break;
			
		case KeyEvent.VK_RIGHT:
			moveNodes(d, 0);
			break;
		
		case KeyEvent.VK_DELETE:
			if(nodeUnderCursor!=null) {
				graph.removeNode(nodeUnderCursor);
				for(Lines l : graph.getLines()) {
					if(l.n1==nodeUnderCursor || l.n2==nodeUnderCursor) {
						graph.removeLine(l);
					}
				}
			}
			else if(lineUnderCursor!=null) {
				graph.removeLine(lineUnderCursor);
			}
			break;
		}
		repaint();
		setMouseCursor();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char z;
		z=e.getKeyChar();
		if(nodeUnderCursor!=null) {
			switch (z) {
			case 'r':
				nodeUnderCursor.setColor(Color.RED);
				break;
				
			case 'b':
				nodeUnderCursor.setColor(Color.BLUE);
				break;
				
			case 'g':
				nodeUnderCursor.setColor(Color.GREEN);
				break;
				
			case 'w':
				nodeUnderCursor.setColor(Color.WHITE);
				break;
				
			case 'y':
				nodeUnderCursor.setColor(Color.YELLOW);
				break;
				
			case 'p':
				nodeUnderCursor.setColor(Color.PINK);
				break;
				
			case 'o':
				nodeUnderCursor.setColor(Color.ORANGE);
				break;
				
			case 'd':
				nodeUnderCursor.setColor(Color.BLACK);
				break;
				
			case '+':
				int r=nodeUnderCursor.getR()+10;
				nodeUnderCursor.setR(r);
				break;
				
			case '-':
				int r1=nodeUnderCursor.getR()-5;
				if(r1>=10) {
					nodeUnderCursor.setR(r1);
				}
				break;
			}
			
				
			
		}
		else if(lineUnderCursor!=null) {
			switch (z) {
			case 'r':
				lineUnderCursor.setColor(Color.RED);
				break;
				
			case 'b':
				lineUnderCursor.setColor(Color.BLUE);
				break;
				
			case 'g':
				lineUnderCursor.setColor(Color.GREEN);
				break;
				
			case 'w':
				lineUnderCursor.setColor(Color.WHITE);
				break;
				
			case 'y':
				lineUnderCursor.setColor(Color.YELLOW);
				break;
				
			case 'p':
				lineUnderCursor.setColor(Color.PINK);
				break;
				
			case 'o':
				lineUnderCursor.setColor(Color.ORANGE);
				break;
				
			case 'd':
				lineUnderCursor.setColor(Color.BLACK);
				break;
			}
		}
		
		
		repaint();
		setMouseCursor();
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if(MbuttonLeft) {
			if(nodeUnderCursor!=null) {
				moveNode(event.getX()-MouseX, event.getY()-MouseY, nodeUnderCursor);
			}
			else if(lineUnderCursor!=null) {
				moveLine(event.getX()-MouseX, event.getY()-MouseY, lineUnderCursor);
				moveNode(event.getX()-MouseX, event.getY()-MouseY, lineUnderCursor.n1);
				moveNode(event.getX()-MouseX, event.getY()-MouseY, lineUnderCursor.n2);
			}
			else {
				moveNodes(event.getX()-MouseX, event.getY()-MouseY);
				moveLines(event.getX()-MouseX, event.getY()-MouseY);
			}
		}
		MouseX=event.getX();
		MouseY=event.getY();
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		setMouseCursor(event);
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
			if (event.getButton()==1) MbuttonLeft = true;
			if (event.getButton()==3) MbuttonRight = true;
			setMouseCursor(event);
		}
		

	@Override
	public void mouseReleased(MouseEvent event) {
		if (event.getButton() == 1)
			MbuttonLeft = false;
		if(event.getButton()==1 && nodeConnecting!=null) {
			Nodes n=findNode(event);
			if(n!=null && n!=nodeConnecting) {
			graph.addLine(new Lines(n,nodeConnecting));
			}
			repaint();
			nodeConnecting=null;
		}
		if (event.getButton() == 3)
			MbuttonRight = false;
		setMouseCursor(event);
		if(event.getButton()==3) {
			if(nodeUnderCursor!=null) {
				CreatePopUpMenu(event,nodeUnderCursor);
			}
			else if(lineUnderCursor!=null) {
				CreatePopUpMenu(event, lineUnderCursor);
			}
			else {
				CreatePopUpMenu(event);
			}
		}
	}	
	
	
	protected void CreatePopUpMenu(MouseEvent e) {
		JMenuItem item;
		
		 JPopupMenu popup = new JPopupMenu();
	        item = new JMenuItem("Utwórz nowy wierzcho³ek");
	        
	        item.addActionListener((action) -> {
	        	String n=JOptionPane.showInputDialog("Podaj nazwê");
	        	if(n!=null) {
				graph.addNode(new Nodes(e.getX(), e.getY(),n));
	        	}
				repaint();
			});
	        popup.add(item);
	        popup.show(e.getComponent(), e.getX(), e.getY());
	        
	}
	
	protected void CreatePopUpMenu(MouseEvent e, Nodes node) {
		JMenuItem item;
		
		JPopupMenu pop = new JPopupMenu();
		item = new JMenuItem("Zmieñ kolor");
		
		item.addActionListener((c) -> {
			Color colo = JColorChooser.showDialog(this, "Wybierz kolor", node.getColor());
			if(colo!=null) {
				node.setColor(colo);
			}
			repaint();
		});
		pop.add(item);
		
		item=new JMenuItem("Usuñ wierzcho³ek");
		item.addActionListener((a)->{
			graph.removeNode(node);
			for(Lines l : graph.getLines()) {
				if(l.n1.equals(node) || l.n2.equals(node)) {
					graph.removeLine(l);
				}
			}
			repaint();
		});
		pop.addSeparator();
		pop.add(item);
		
		item =new JMenuItem("Zmieñ nazwê");
		item.addActionListener((x)->{
			String n=JOptionPane.showInputDialog("Podaj nazwê");
			if(n!=null) {
			node.setName(n);
			}
			repaint();
		});
		pop.addSeparator();
		pop.add(item);
		
		item= new JMenuItem("Utwórz liniê z wybranym wierzcho³kiem");
		item.addActionListener((action)->{
			nodeConnecting=node;
		});
		
		pop.addSeparator();
		pop.add(item);
		pop.show(e.getComponent(), e.getX(), e.getY());
	}
	
	protected void CreatePopUpMenu(MouseEvent event, Lines line) {
		JMenuItem it;
		JPopupMenu popup = new JPopupMenu();
		
		it=new JMenuItem("Usuñ liniê");
		
		it.addActionListener((act)->{
			graph.removeLine(line);
			repaint();
		});
		popup.add(it);
		
		it=new JMenuItem("Zmieñ kolor");
		
		it.addActionListener((c)->{
			Color color=JColorChooser.showDialog(this, "Wybierz kolor", line.getColor());
			if(color!=null)
			line.setColor(color);
			
			repaint();
		});
		popup.addSeparator();
		popup.add(it);
		
		popup.show(event.getComponent(),event.getX(), event.getY());
		
	}
	

}
