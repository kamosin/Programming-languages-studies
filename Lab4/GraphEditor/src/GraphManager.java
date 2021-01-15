import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphManager extends JFrame implements ActionListener{

	/* 
	 *  Program: Edytor Grafów
	 *     Plik: GraphManager.java
	 *           
	 *    Klasa GraphManager implementuje okno g³owne programu 
	 *    edytora grafów  
	 *    Autor: Kamil Osiñski
	 *    Nr indeksu : 241387
	 *    Data:  12.12.2018 r.
	 */	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String APP_INSTRUCTION =
			"                          O P I S   P R O G R A M U \n\n" + 
	        "Aktywna klawisze:\n" +
			"   strza³ki ==> przesuwanie wszystkich wierzcho³ków\n" +
			"   SHIFT + strza³ki ==> szybkie przesuwanie wszystkich wierzcho³ków\n\n" +
			"ponadto gdy kursor wskazuje wierzcho³ek:\n" +
			"   DEL   ==> kasowanie wierzcho³ka, krawêdzi\n" +
			"   +, -   ==> powiêkszanie, pomniejszanie wierzcho³ka\n" +
			"   r,g,b,y,w ==> zmiana koloru ko³a\n\n" +
			"Operacje myszka:\n" +
			"   przeci¹ganie ==> przesuwanie wszystkich wierzcho³ków i krawêdzi\n" +
			"   PPM ==> tworzenie nowego wierzcho³ka w niejscu kursora\n" +
	        "ponadto gdy kursor wskazuje wierzcho³ek:\n" +
	        "   przeci¹ganie ==> przesuwanie wierzcho³ka\n" +
			"   PPM ==> zmiana koloru wierzcho³ka\n" +
	        "   usuwanie ko³a lub po³¹czenie wierzcho³ka z innym\n";
	
	private static final String Author = 
			"Autor: Kamil Osiñski\n" +
			"Nr indeksu: 241387\n" +
			"Data wykonania: 12.12.2018\n";
	

	public static void main(String[] args) {
		new GraphManager();
	}
	
	private MainEditor editor = new MainEditor();
	
	
	public GraphManager() {
		super("Edytor Grafów");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setContentPane(editor);
		createMenu();
		showBuildinExample();
		setVisible(true);
	}
	
	JMenuBar bar = new JMenuBar();
	JMenu menu1 = new JMenu("Plik");
	JMenuItem item1 = new JMenuItem("Nowy plik");
	JMenuItem item2 = new JMenuItem("Otwórz plik");
	JMenuItem item3 = new JMenuItem("Zapisz do pliku");
	JMenuItem item4 = new JMenuItem("Przyk³ad");
	JMenuItem item5 = new JMenuItem("Zakoñcz");
	
	JMenu menu2 = new JMenu("Graf");
	JMenuItem item2_1 = new JMenuItem("Lista wierzcho³ków");
	JMenuItem item2_2 = new JMenuItem("Lista krawêdzi");
	
	JMenu menu3 = new JMenu("O programie");
	JMenuItem item3_1 = new JMenuItem("Instrukcja");
	JMenuItem item3_2 = new JMenuItem("O autorze");
	
	public void createMenu() {
		setJMenuBar(bar);
		bar.add(menu1);
		menu1.add(item1);
		item1.addActionListener(this);
		menu1.add(item2);
		item2.addActionListener(this);
		menu1.add(item3);
		item3.addActionListener(this);
		menu1.addSeparator();
		menu1.add(item4);
		item4.addActionListener(this);
		menu1.addSeparator();
		menu1.add(item5);
		item5.addActionListener(this);
		
		bar.add(menu2);
		menu2.add(item2_1);
		item2_1.addActionListener(this);
		menu2.add(item2_2);
		item2_2.addActionListener(this);
		
		bar.add(menu3);
		menu3.add(item3_1);
		item3_1.addActionListener(this);
		menu3.add(item3_2);
		item3_2.addActionListener(this);
		
	}
	
	private void showNodes(Graphs g) {
		Nodes tab[] = g.getNodes();
		
		StringBuilder message = new StringBuilder("Liczba wszystkich wierzcho³ków: "+ tab.length + "\n");
		int i=0;
		for(Nodes n : tab) {
			message.append(n +"  ");
			if(++i % 4 ==0) {
				message.append("\n");
			}
		}
		JOptionPane.showMessageDialog(this, message, "Lista wierzcho³ków", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	private void showLines(Graphs g) {
		Lines tab[] = g.getLines();
		
		StringBuilder message = new StringBuilder("Liczba wszystkich krawêdzi: "+ tab.length + "\n");
		int i=0;
		for(Lines l : tab) {
			message.append(l +"  ");
			if(++i % 4 ==0) {
				message.append("\n");
			}
		}
		JOptionPane.showMessageDialog(this, message, "Lista wierzcho³ków", JOptionPane.PLAIN_MESSAGE);
		
		
	}
	
	private void showBuildinExample() {
		Graphs graph = new Graphs();
		
		Nodes n1 = new Nodes(38, 152,"p");
		n1.setColor(Color.YELLOW);
		Nodes n2 = new Nodes(38, 216, "p");
		n2.setColor(Color.YELLOW);
		Nodes n3 = new Nodes(90, 92,"p");
		n3.setColor(Color.YELLOW);
		Nodes n4 = new Nodes(90, 152, "p");
		n4.setColor(Color.YELLOW);
		Nodes n5 = new Nodes(38, 92,"p");
		n5.setColor(Color.YELLOW);
		Nodes n6 = new Nodes(123, 92, "w");
		n6.setColor(Color.GREEN);
		Nodes n7 = new Nodes(130, 153,"w");
		n7.setColor(Color.GREEN);
		Nodes n8 = new Nodes(138, 216, "w");
		n8.setColor(Color.GREEN);
		Nodes n9 = new Nodes(174, 154,"w");
		n9.setColor(Color.GREEN);
		Nodes n10 = new Nodes(204, 216, "w");
		n10.setColor(Color.GREEN);
		Nodes n11 = new Nodes(215, 153,"w");
		n11.setColor(Color.GREEN);
		Nodes n12 = new Nodes(229, 92, "w");
		n12.setColor(Color.GREEN);
		Nodes n13 = new Nodes(271, 91,"r");
		n13.setColor(Color.RED);
		Nodes n14 = new Nodes(271, 153, "r");
		n14.setColor(Color.RED);
		Nodes n15 = new Nodes(271, 216,"r");
		n15.setColor(Color.RED);
		Nodes n16 = new Nodes(336, 216, "r");
		n16.setColor(Color.RED);
		Nodes n17 = new Nodes(336, 153,"r");
		n17.setColor(Color.RED);
		Nodes n18 = new Nodes(336, 91, "r");
		n18.setColor(Color.RED);
		
		Lines l1 =new Lines(n1,n2);
		Lines l2 =new Lines(n4,n1);
		Lines l3 =new Lines(n3,n4);
		Lines l4 =new Lines(n5,n3);
		Lines l5 =new Lines(n1,n5);
		Lines l6 =new Lines(n7,n6);
		Lines l7 =new Lines(n8,n7);
		Lines l8 =new Lines(n9,n8);
		Lines l9 =new Lines(n10,n9);
		Lines l10 =new Lines(n11,n10);
		Lines l11=new Lines(n12,n11);
		Lines l12 =new Lines(n14,n15);
		Lines l13 =new Lines(n13,n14);
		Lines l14 =new Lines(n18,n13);
		Lines l15 =new Lines(n17,n18);
		Lines l16 =new Lines(n14,n17);
		Lines l17 =new Lines(n16,n14);
		
		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);
		graph.addNode(n5);
		graph.addNode(n6);
		graph.addNode(n7);
		graph.addNode(n8);
		graph.addNode(n9);
		graph.addNode(n10);
		graph.addNode(n11);
		graph.addNode(n12);
		graph.addNode(n13);
		graph.addNode(n14);
		graph.addNode(n15);
		graph.addNode(n16);
		graph.addNode(n17);
		graph.addNode(n18);
		
		graph.addLine(l1);
		graph.addLine(l2);
		graph.addLine(l3);
		graph.addLine(l4);
		graph.addLine(l5);
		graph.addLine(l6);
		graph.addLine(l7);
		graph.addLine(l8);
		graph.addLine(l9);
		graph.addLine(l10);
		graph.addLine(l11);
		graph.addLine(l12);
		graph.addLine(l13);
		graph.addLine(l14);
		graph.addLine(l15);
		graph.addLine(l16);
		graph.addLine(l17);
		
		editor.setGraph(graph);
		repaint();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object s=e.getSource();
		
		if(s==item1) {
			editor.setGraph(new Graphs());
			repaint();
		}
		if(s==item2) {
			JFileChooser chooser = new JFileChooser();
			int val=chooser.showOpenDialog(this);
			if(val==JFileChooser.APPROVE_OPTION) {
				try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile().getPath()))){
				Graphs g = (Graphs) input.readObject();
				editor.setGraph(g);
				}
				catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(s==item3) {
			JFileChooser chooser =new JFileChooser();
			int value=chooser.showSaveDialog(this);
			if(value==JFileChooser.APPROVE_OPTION) {
				try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile().getPath()))){
					output.writeObject(editor.getGraph());
				}
				catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if(s==item4) {
			showBuildinExample();
		}
		if(s==item5) {
			System.exit(0);
		}
		if(s==item2_1) {
			showNodes(editor.graph);
		}
		if(s==item2_2) {
			showLines(editor.graph);
		}
		if(s==item3_1) {
			JOptionPane.showMessageDialog(this, APP_INSTRUCTION, "Instrukcja obs³ugi programu", JOptionPane.PLAIN_MESSAGE);
		}
		if(s==item3_2) {
			JOptionPane.showMessageDialog(this, Author, "O autorze", JOptionPane.PLAIN_MESSAGE);
		}
	}

	
	
}
