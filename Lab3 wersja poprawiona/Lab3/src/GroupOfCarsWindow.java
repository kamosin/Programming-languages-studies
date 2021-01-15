import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

/*
 *   Plik: GroupOfCarsWindow.java
 *
 *   Opis: Zarz¹dzanie grup¹ klasy GroupOfCars.java
 *
 *   Autor: Kamil Osiñski
 *   Nr indeksu: 241387
 *   Data: 25.11.2018 r.
 */



public class GroupOfCarsWindow extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final String GREETING_MESSAGE =
			"Program do zarz¹dzania grupami samochodów " +
		    "- wersja okienkowa\n\n" +
		    "Autor: Kamil Osiñski\n" +
		    "Nr indeksu: 241387\n" +
		    "Data:  24.11.2018 r.\n";
	
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
	
	
	
	
	JTextField textField = new JTextField();
	JTextField textField_1= new JTextField();
	GroupOfCars currentGroup;
	
	
	JMenuBar menuBar = new JMenuBar();
	
	JMenu mnNewMenu = new JMenu("Lista samochodów");
	JMenu mnNewMenu_1 = new JMenu("Sortowanie");
	JMenu mnNewMenu_2 = new JMenu("W³aœciwoœci");
	JMenu mnNewMenu_3 = new JMenu("O programie");
	
	JMenuItem mntmNewMenuItem = new JMenuItem("Dodaj nowy samochód");
	JMenuItem mntmNewMenuItem_1 = new JMenuItem("Edytuj dane samochodu");
	JMenuItem mntmNewMenuItem_2 = new JMenuItem("Usuñ samochód");
	JMenuItem mntmNewMenuItem_3 = new JMenuItem("Wczytaj samochód z pliku");
	JMenuItem mntmNewMenuItem_4 = new JMenuItem("Zapisz samochód do pliku");
	JMenuItem mntmNewMenuItem_5 = new JMenuItem("Sortuj alfabetycznie");
	JMenuItem mntmNewMenuItem_6 = new JMenuItem("Sortuj wg roku produkcji");
	JMenuItem mntmNewMenuItem_8 = new JMenuItem("Sortuj wg typu nadwozia");
	JMenuItem mntmNewMenuItem_9 = new JMenuItem("Zmieñ nazwê grupy");
	JMenuItem mntmNewMenuItem_7 = new JMenuItem("Sortuj wg przebiegu");
	JMenuItem mntmNewMenuItem_10 = new JMenuItem("Zmieñ typ kolekcji");
	JMenuItem about = new JMenuItem("Informacje");
	
	JLabel lblNewLabel = new JLabel("  Nazwa grupy:");
	JLabel lblNewLabel_1 = new JLabel("Rodzaj kolekcji:");
	
	JButton btnNewButton = new JButton("Dodaj nowy samochód");
	JButton btnNewButton_1 = new JButton("Edytuj dane samochodu");
	JButton btnNewButton_2 = new JButton("Usuñ samochód");
	JButton btnNewButton_3 = new JButton("Wczytaj samochód z pliku");
	JButton btnNewButton_4 = new JButton("Zapisz samochód do pliku");
	
	CarListView ViewList;
	
	public GroupOfCarsWindow(Window parent, GroupOfCars group) throws MyException{
	
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		setTitle("Modyfikacja grup samochodów");
		setSize(450,520);
		setLocationRelativeTo(parent);
		setResizable(false);
	
		setJMenuBar(menuBar);
		
		currentGroup=group;
		
		textField.setText(currentGroup.getName());
		textField.setEditable(false);
		textField_1.setText(currentGroup.getGroupType().toString());
		textField_1.setEditable(false);
		
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.addSeparator();
		mnNewMenu.add(mntmNewMenuItem_3);
		mnNewMenu.add(mntmNewMenuItem_4);
		
		menuBar.add(mnNewMenu);
		menuBar.add(mnNewMenu_1);
		menuBar.add(mnNewMenu_2);
		menuBar.add(mnNewMenu_3);
		
		mnNewMenu_1.add(mntmNewMenuItem_5);
		mnNewMenu_1.add(mntmNewMenuItem_6);
		mnNewMenu_1.add(mntmNewMenuItem_7);
		mnNewMenu_1.add(mntmNewMenuItem_8);
		
		mnNewMenu_2.add(mntmNewMenuItem_9);
		mnNewMenu_2.add(mntmNewMenuItem_10);
		
		mnNewMenu_3.add(about);
		
		about.addActionListener(this);
		
		
		mntmNewMenuItem.addActionListener(this);
		mntmNewMenuItem_1.addActionListener(this);
		mntmNewMenuItem_2.addActionListener(this);
		mntmNewMenuItem_3.addActionListener(this);
		mntmNewMenuItem_4.addActionListener(this);
		mntmNewMenuItem_5.addActionListener(this);
		mntmNewMenuItem_6.addActionListener(this);
		mntmNewMenuItem_8.addActionListener(this);
		mntmNewMenuItem_9.addActionListener(this);
		mntmNewMenuItem_7.addActionListener(this);
		mntmNewMenuItem_10.addActionListener(this);
		
		ViewList = new CarListView(currentGroup, 400, 300);
		ViewList.refreshView();
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel);
		panel.add(textField);
		textField.setColumns(30);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel_1);
		panel.add(textField_1);
		textField_1.setColumns(30);
		
		panel.add(ViewList);
		
		panel.add(btnNewButton);
		panel.add(btnNewButton_1);
		panel.add(btnNewButton_2);
		panel.add(btnNewButton_3);
		panel.add(btnNewButton_4);
		
		btnNewButton.addActionListener(this);
		btnNewButton_1.addActionListener(this);
		btnNewButton_2.addActionListener(this);
		btnNewButton_3.addActionListener(this);
		btnNewButton_4.addActionListener(this);
		
		setContentPane(panel);
		setVisible(true);
		
		
	}
	

	static private GroupType chooseCollectionWindow(Window parent, String message) {
        GroupType group = (GroupType) JOptionPane.showInputDialog(parent, "Wybierz kolekcjê:", "Wynierz kolekcje:", JOptionPane.QUESTION_MESSAGE, null, GroupType.values(), GroupType.ARRAY_LIST);
        return group;
    }

	
	
	 public static GroupOfCars createNewGroupOfCars(manager2 manager) throws MyException {
	        String nameOfGroup = JOptionPane.showInputDialog("Podaj nazwê grupy:");
	        GroupType groupType = chooseCollectionWindow(manager, null);
	        GroupOfCars gr=new GroupOfCars(groupType,nameOfGroup);
	        GroupOfCarsWindow APP = new GroupOfCarsWindow(manager,gr);
	        APP.currentGroup.setName(nameOfGroup);
	        APP.currentGroup.setGroupType(groupType);
	        APP.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        return APP.currentGroup;
	    }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();
		
		try {
			if(source==mntmNewMenuItem || source==btnNewButton)
			{
				currentGroup.add(InputDataWindow.createNewCar(this));
				
			}
			if(source == mntmNewMenuItem_1 || source==btnNewButton_1 )
			{
				Car carToEdit = null;
                int index = ViewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- >= 0)
                        carToEdit = iterator.next();
                    InputDataWindow.changeCarData(this, carToEdit);
                }
			}
			if(source==mntmNewMenuItem_2 || source==btnNewButton_2)
			{
				int index = ViewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- >= 0)
                        iterator.next();
                    iterator.remove();
                }
			}
			if(source==mntmNewMenuItem_3 || source==btnNewButton_3)
			{
				JFileChooser chooser = new JFileChooser(".");
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Car car = Car.DataFromFile(chooser.getSelectedFile().getName());
                    currentGroup.add(car);
                }
			}
			if(source==mntmNewMenuItem_4 || source==btnNewButton_4)
			{
				int index = ViewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- > 0)
                        iterator.next();
                    Car car = iterator.next();

                    JFileChooser chooser = new JFileChooser(".");
                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        Car.DataToFile(chooser.getSelectedFile().getName(), car);
                    }
                }
			}
			if(source==mntmNewMenuItem_5)
			{
				currentGroup.sortName();
			}
			if(source==mntmNewMenuItem_6)
			{
				currentGroup.sortYearOfProd();
			}
			if(source==mntmNewMenuItem_7)
			{
				currentGroup.sortMileage();
			}
			if(source==mntmNewMenuItem_8)
			{
				currentGroup.sortBodyType();
			}
			if(source==mntmNewMenuItem_9)
			{
				JFrame frame = new JFrame("Edycja nazwy grupy");
                currentGroup.setName(JOptionPane.showInputDialog(frame, "Podaj nazwê grupy:"));
                textField.setText(currentGroup.getName());
			}
			if(source == mntmNewMenuItem_10)
			{
				currentGroup.setGroupType(chooseCollectionWindow(this, null));
                textField_1.setText(currentGroup.getGroupType().name());
			}
			if(source==about)
			{
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			 
		}
		catch (MyException e1)
		{
			JOptionPane.showMessageDialog(this, e1.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		ViewList.refreshView();
	}
	 
	
}
	
	class CarListView extends JScrollPane {
	    private static final long serialVersionUID = 1L;

	    private GroupOfCars goc;
	    private JTable table;
	    private DefaultTableModel tableModel;

	    public CarListView(GroupOfCars _goc, int width, int height) {
	        this.goc = _goc;
	        setPreferredSize(new Dimension(width, height));
	        setBorder(BorderFactory.createTitledBorder("Lista samochodów:"));

	        String[] tableHeader = {"Marka", "Model", "Rok produkcji", "Przebieg", "Typ nadwozia"};
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
	        for (Car car : goc) {
	            if (car != null) {
	                String[] row = {car.getCarMake(), "" + car.getCarModel(), "" + car.getYearOfProd(), Double.toString(car.getMileageInKm()), car.getBody().toString()};
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

