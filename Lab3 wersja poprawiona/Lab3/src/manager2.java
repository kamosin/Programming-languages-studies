import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/*
 *   Plik: manager2.java
 *
 *   Opis: Zarz¹dzanie grupami
 *
 *   Autor: Kamil Osiñski
 *   Nr indeksu: 241387
 *   Data: 25.11.2018 r.
 */


public class manager2 extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static final String GREETING_MESSAGE =
	"Program do zarz¹dzania grupami samochodów " +
    "- wersja okienkowa\n\n" +
    "Autor: Kamil Osiñski\n" +
    "Nr ineksu: 241387 \n"  +
    "Data:  24.11.2018 r.\n";
	
	 private static final String ALL_GROUPS_FILE = "LISTA_WSZYSTKICH.BIN";
	    WindowAdapter windowListener = new WindowAdapter() {

	        @Override
	        public void windowClosed(WindowEvent e) {
	            JOptionPane.showMessageDialog(null, "Program zakoñczy³ dzia³anie!");

	        }


	        @Override
	        public void windowClosing(WindowEvent e) {
	            windowClosed(e);
	        }

	    };
	
	
	
		
	JMenuBar menuBar = new JMenuBar();
	JMenu mnNewMenu = new JMenu("Grupy");
	
	JMenu Look = new JMenu("Look And Feel");
	
	JMenu mnNewMenu_1 = new JMenu("O programie");
	JMenuItem mntmNewMenuItem = new JMenuItem("Utwórz grupê");
	JMenuItem mntmNewMenuItem_1 = new JMenuItem("Edytuj grupê");
	JMenuItem mntmNewMenuItem_2 = new JMenuItem("Usuñ grupê");
	JMenuItem mntmNewMenuItem_3 = new JMenuItem("Otwórz grupê z pliku");
	JMenuItem mntmNewMenuItem_4 = new JMenuItem("Zapisz grupê do pliku");
	JMenuItem mntmNewMenuItem_5 = new JMenuItem("Informacje");
	
	JMenuItem windows= new JMenuItem("Windows");
	JMenuItem metal = new JMenuItem("Metal");
	JMenuItem nimbus = new JMenuItem("Nimbus");
	
	
	JButton btnNewButton = new JButton("Utwórz now¹ grupê");
	JButton btnNewButton_1 = new JButton("Edytuj grupê");
	JButton btnNewButton_2 = new JButton("Usuñ grupê");
	JButton btnNewButton_3 = new JButton("Wczytaj z pliku");
	JButton btnNewButton_4 = new JButton("Zapisz grupê do pliku");
	ViewGroupList viewList;
 
    private List<GroupOfCars> currentList = new ArrayList<GroupOfCars>();
	
	public manager2() {
		setTitle("GroupManager - zarz¹dzanie grupami samochodów");
        setSize(450, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent event) {
                try {
                    saveGroupListToFile(ALL_GROUPS_FILE);
                    JOptionPane.showMessageDialog(null, "Dane zosta³y zapisane do pliku " + ALL_GROUPS_FILE);
                } catch (MyException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
               
                windowClosed(e);
            }

        }
);
        try {
            loadGroupListFromFile(ALL_GROUPS_FILE);
            JOptionPane.showMessageDialog(null, "Dane zosta³y wczytane z pliku " + ALL_GROUPS_FILE);
        } catch (MyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
      
		setJMenuBar(menuBar);
		menuBar.add(mnNewMenu);
		menuBar.add(Look);
		menuBar.add(mnNewMenu_1);
		
		
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem_3);
		mnNewMenu.add(mntmNewMenuItem_4);	
		
		Look.add(metal);
		Look.add(nimbus);
		Look.add(windows);
		
		mntmNewMenuItem.addActionListener(this);
		mntmNewMenuItem_1.addActionListener(this);
		mntmNewMenuItem_2.addActionListener(this);
		mntmNewMenuItem_3.addActionListener(this);
		mntmNewMenuItem_4.addActionListener(this);
		mntmNewMenuItem_5.addActionListener(this);
		metal.addActionListener(this);
		nimbus.addActionListener(this);
		windows.addActionListener(this);
		
		btnNewButton.setBounds(10, 273, 127, 23);
		btnNewButton_1.setBounds(154, 273, 121, 23);
		btnNewButton_2.setBounds(298, 273, 103, 23);
		btnNewButton_3.setBounds(91, 307, 133, 23);
		btnNewButton_4.setBounds(248, 307, 103, 23);
		
		viewList = new ViewGroupList(currentList, 400, 300);
        viewList.refreshView();
        
        JPanel contentPane = new JPanel();
        
		contentPane.add(viewList);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_1);
		contentPane.add(btnNewButton_2);
		contentPane.add(btnNewButton_3);
		contentPane.add(btnNewButton_4);
		
		btnNewButton.addActionListener(this);
		btnNewButton_1.addActionListener(this);
		btnNewButton_2.addActionListener(this);
		btnNewButton_3.addActionListener(this);
		btnNewButton_4.addActionListener(this);

        setContentPane(contentPane);
        setVisible(true);
        
	}
	public static void main(String[] args) {

		try {
			manager2 frame = new manager2();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
    void loadGroupListFromFile(String file_name) throws MyException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file_name))) {
            currentList = (List<GroupOfCars>) in.readObject();
        } catch (FileNotFoundException e) {
            throw new MyException("Nie odnaleziono pliku " + file_name);
        } catch (Exception e) {
            throw new MyException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
        }
    }


    void saveGroupListToFile(String file_name) throws MyException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_name))) {
            out.writeObject(currentList);
        } catch (FileNotFoundException e) {
            throw new MyException("Nie odnaleziono pliku " + file_name);
        } catch (IOException e) {
            throw new MyException("Wyst¹pi³ b³¹d podczas zapisu danych do pliku.");
        }
    }
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
	
		Object s=e.getSource();
		try 
		{
		if(s==mntmNewMenuItem || s==btnNewButton)
		{
			GroupOfCars group = GroupOfCarsWindow.createNewGroupOfCars(this);
            if (group != null) {
                currentList.add(group);
                
                viewList.refreshView();
            }
		}
		
		if(s==mntmNewMenuItem_1 || s==btnNewButton_1)
		{
			 int index = viewList.getSelectedIndex();
             if (index >= 0) {
                 Iterator<GroupOfCars> iterator = currentList.iterator();
                 while (index-- > 0)
                     iterator.next();
                 new GroupOfCarsWindow(this,iterator.next());
             }
		}
		if(s==mntmNewMenuItem_2 || s==btnNewButton_2)
		{
			int index = viewList.getSelectedIndex();
            if (index >= 0) {
                Iterator<GroupOfCars> iterator = currentList.iterator();
                while (index-- >= 0)
                    iterator.next();
                iterator.remove();
            }
		}
		if(s==mntmNewMenuItem_3 || s==btnNewButton_3)
		{
			JFileChooser chooser = new JFileChooser(".");
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                GroupOfCars group = GroupOfCars.readFromFile(chooser.getSelectedFile().getName());
                currentList.add(group);
            }
		}
		if(s==mntmNewMenuItem_4 || s==btnNewButton_4)
		{
			 int index = viewList.getSelectedIndex();
             if (index >= 0) {
                 Iterator<GroupOfCars> iterator = currentList.iterator();
                 while (index-- > 0)
                     iterator.next();
                 GroupOfCars group = iterator.next();

                 JFileChooser chooser = new JFileChooser(".");
                 int returnVal = chooser.showSaveDialog(this);
                 if (returnVal == JFileChooser.APPROVE_OPTION) {
                     GroupOfCars.printToFile(chooser.getSelectedFile().getName(), group);
                 }
             }
		}
		if(s==mntmNewMenuItem_5)
		{
			JOptionPane.showMessageDialog(this,GREETING_MESSAGE);
		}
		if(s==metal)
		{
		
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		SwingUtilities.updateComponentTreeUI(this);
		}
		if(s==nimbus)
		{
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			SwingUtilities.updateComponentTreeUI(this);
		}
		if(s==windows)
		{
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		}
		catch (MyException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		viewList.refreshView();
	}

}

class ViewGroupList extends JScrollPane {
    private static final long serialVersionUID = 1L;

    private List<GroupOfCars> list;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewGroupList(List<GroupOfCars> list, int width, int height) {
        this.list = list;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista grup:"));

        String[] tableHeader = {"Nazwa grupy", "Typ kolekcji", "Liczba samochodów"};
        tableModel = new DefaultTableModel(tableHeader, 0);
        table = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }
 
    void refreshView() {
        tableModel.setRowCount(0);
        for (GroupOfCars group : list) {
            if (group != null) {
                String[] row = {group.getName(), group.getGroupType().toString(), "" + group.size()};
                tableModel.addRow(row);
            }
        }
    }

    int getSelectedIndex() {
        int index = table.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "¯adana grupa nie jest zaznaczona.", "B³¹d", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

}