import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Program: Aplikacja okienkowa z GUI, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: InputDataWindow.java
 *          
 *   Autor: Kamil Osiñski
 *   Nr Indeksu: 241387
 *   Data: 28.10.2018 2018 r.
 *
 * Klasa InputWindowDialog implementuje pomocnicze okna dialogowe
 * umo¿liwiaj¹ce wprowadzenie lub modyfikacjê danych do aplikacji
 */


public class InputDataWindow extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private Car car;
	
	
	
	JLabel lMake, lModel, lYear, lMileage, lBody;
	JTextField tMake, tModel, tYear, tMileage;
	JButton bOK, bCancel;
	JComboBox<CarType> ComboBody;
	
	public InputDataWindow(Window parent, Car car) 
	{
	super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
	setSize(400,400);
	setLocationRelativeTo(parent);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setLayout(null);
	Font font = new Font("MonoSpaced", Font.BOLD, 15);
	
	
	
	lMake = new JLabel("Marka: ");
	lMake.setBounds(95,20,65,35);
	lModel = new JLabel("Model: ");
	lModel.setBounds(95,70,65,35);
	lYear = new JLabel("Rok produkcji: ");
	lYear.setBounds(25, 120, 135, 35);
	lMileage = new JLabel("Przebieg: ");
	lMileage.setBounds(70,170,90,35);
	lBody = new JLabel("Typ nadwozia: ");
	lBody.setBounds(34,220,126,35);
	
	
	lMake.setFont(font);
	lModel.setFont(font);
	lYear.setFont(font);
	lMileage.setFont(font);
	lBody.setFont(font);
	
	
	add(lMake);
	add(lModel);
	add(lYear);
	add(lMileage);
	add(lBody);
	
	
	tMake = new JTextField("");
	tMake.setBounds(170, 20, 200,40);
	tMake.setFont(font);
	tModel = new JTextField("");
	tModel.setBounds(170,70, 200,40);
	tModel.setFont(font);
	tYear = new JTextField("");
	tYear.setBounds(170,120,200,40);
	tYear.setFont(font);
	tMileage = new JTextField("");
	tMileage.setBounds(170,170,200,40);
	tMileage.setFont(font);
	ComboBody = new JComboBox<CarType>(CarType.values());
	ComboBody.setBounds(170,220,200,40);
	ComboBody.setFont(font);
	
	
	add(tMake);
	add(tModel);
	add(tYear);
	add(tMileage);
	add(ComboBody);
	
	bOK = new JButton("Akceptuj");
	bOK.setBounds(30,290,150,40);
	bCancel = new JButton("Anuluj");
	bCancel.setBounds(210,290,150,40);
	
	add(bOK);
	add(bCancel);
	
	bOK.addActionListener(this);
	bCancel.addActionListener(this);
	
	this.car = car;
	
	if (car==null){
		setTitle("Nowy samochód");
	} else
	{
	
		setTitle(car.toString());
		tMake.setText(car.getCarMake());
		tModel.setText(car.getCarModel());
		tYear.setText(""+car.getYearOfProd());
		tMileage.setText(""+car.getMileageInKm());
		ComboBody.setSelectedItem(car.getBody());
	}
	
	
	
	setVisible(true);
	}
	
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		Object x =e.getSource();
		if (x == bOK)
		{
			try {
				if (car == null) 
				{
					car = new Car(tMake.getText(), tModel.getText());
				}
				else
				{
					
					car.setCarMake(tMake.getText());
					car.setCarModel(tModel.getText());
					 
				}	
				car.setCarMake(tMake.getText());
				car.setCarModel(tModel.getText());
				car.setMileageInKm(Double.parseDouble(tMileage.getText()));
				car.setYearOfProd(Integer.parseInt(tYear.getText()));	
				car.setBody((CarType) ComboBody.getSelectedItem());
				dispose();
		} catch (MyException exception)
			{
			JOptionPane.showMessageDialog(this, exception.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
	}
	}
		if (x == bCancel) {
			car=null;
			dispose();
		}
	}	
	
	public static Car createNewCar(Window parent) {
		InputDataWindow Input = new InputDataWindow(parent,null);
		return Input.car;
	}
	
	public static Car changeCarData(Window parent, Car car) {
		InputDataWindow Input = new InputDataWindow(parent,car);
		return Input.car;
	}
	
	
	
}