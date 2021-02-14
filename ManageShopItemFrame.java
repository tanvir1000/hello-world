import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class ManageShopItemFrame extends JFrame implements ActionListener
{
	private JLabel shopIDLabel, shopItemNameLabel, itemQuantityLabel, manuDateLabel, itemPriceLabel, welcomeLabel;
	private JTextField shopItemIdTF, shopItemNameTF, itemQuantityTF, manuDateTF, itemPriceTF;
	private JButton loadBtn, insertBtn, deleteBtn, getAllBtn, backBtn;
	private JPanel panel;
	private JTable shopItemTable;
	private JScrollPane shopItemTableSP;
	
	private User user;
	private ShopRepo mtr;
	private UserRepo ur;
	
	
	public ManageShopItemFrame(LoginFrame lf, String username)
	{
		super("Manage Item");
		this.setSize(800,460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.user = user;
		
		mtr = new ShopRepo();
		ur = new UserRepo();
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		
		String data[][] = {{"", "", "", "", ""}};
		
		String head[] = {"Item Id", "Item Name", "Quantity", "Manufacture", "Item Price"};

		shopItemTable = new JTable(data,head);
		shopItemTableSP = new JScrollPane(shopItemTable);
		shopItemTableSP.setBounds(345, 100, 430, 150);
		shopItemTable.setEnabled(false);
		panel.add(shopItemTableSP);

		welcomeLabel = new JLabel("Welcome "+username);
		welcomeLabel.setBounds(570,30,150,30);
		panel.add(welcomeLabel);
		
		shopIDLabel = new JLabel("Item ID :");
		shopIDLabel.setBounds(130,100,100,30);
		panel.add(shopIDLabel);

		shopItemIdTF = new JTextField();
		shopItemIdTF.setBounds(220,100,100,30);
		panel.add(shopItemIdTF);
		
		shopItemNameLabel = new JLabel("Item Name :");
		shopItemNameLabel.setBounds(106,150,100,30);
		panel.add(shopItemNameLabel);

		shopItemNameTF = new JTextField();
		shopItemNameTF.setBounds(220,150,100,30);
		panel.add(shopItemNameTF);
		
		itemQuantityLabel = new JLabel("Quantity : ");
		itemQuantityLabel.setBounds(130,200,100,30);
		panel.add(itemQuantityLabel);

		itemQuantityTF = new JTextField();
		itemQuantityTF.setBounds(220,200,100,30);
		panel.add(itemQuantityTF);
		
		itemPriceLabel = new JLabel("Item Price :");
		itemPriceLabel.setBounds(111,250,100,30);
		panel.add(itemPriceLabel);

		itemPriceTF = new JTextField();
		itemPriceTF.setBounds(220,250,100,30);
		panel.add(itemPriceTF);

		manuDateLabel = new JLabel("Manufacture :");
		manuDateLabel.setBounds(108,300,100,30);
		panel.add(manuDateLabel);

		manuDateTF = new JTextField();
		manuDateTF.setBounds(220,300,100,30);
		panel.add(manuDateTF);

		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(100,350,80,30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		insertBtn = new JButton("Insert");
		insertBtn.setBounds(200,350,80,30);
		insertBtn.addActionListener(this);
		panel.add(insertBtn);
	
		
		deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(400,350,80,30);
		deleteBtn.addActionListener(this);
		deleteBtn.setEnabled(false);
		panel.add(deleteBtn);
	
		
		getAllBtn = new JButton("Get All");
		getAllBtn.setBounds(500,260,70,25);
		getAllBtn.addActionListener(this);
		panel.add(getAllBtn);
		
		backBtn = new JButton("Logout");
		backBtn.setBounds(695, 30, 80, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		
		if(command.equals(loadBtn.getText()))
		{
			if(!shopItemIdTF.getText().equals("") || !shopItemIdTF.getText().equals(null))
			{
				Shop mt = mtr.searchItem(shopItemIdTF.getText());
				if(mt!= null)
				{
					shopItemNameTF.setText(mt.getitemName());
					itemQuantityTF.setText(mt.getquantity());
					manuDateTF.setText(mt.getmanuDate());
					itemPriceTF.setText(mt.getshopItemPrice()+"");
					
					shopItemIdTF.setEnabled(false);
					shopItemNameTF.setEnabled(true);
					itemQuantityTF.setEnabled(true);
					manuDateTF.setEnabled(true);
					itemPriceTF.setEnabled(true);
					
					//updateBtn.setEnabled(true);
					deleteBtn.setEnabled(true);
					//resetBtn.setEnabled(true);
					insertBtn.setEnabled(false);
					loadBtn.setEnabled(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Invaild ID");
				}
			}
		}
		else if(command.equals(insertBtn.getText()))
		{
			Shop mt = new Shop();
			
			mt.setShopId(shopItemIdTF.getText());
			mt.setitemName(shopItemNameTF.getText());
			mt.setquantity(itemQuantityTF.getText());
			mt.setmanuDate(manuDateTF.getText());
			mt.setshopItemPrice(Double.parseDouble(itemPriceTF.getText()));
			
			mtr.insertInDB(mt);
			
			JOptionPane.showMessageDialog(this, "Inserted");
			
			shopItemIdTF.setText("");
			shopItemNameTF.setText("");
			itemQuantityTF.setText("");
			manuDateTF.setText("");
			itemPriceTF.setText("");
			
			loadBtn.setEnabled(true);
			insertBtn.setEnabled(true);
			//updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			//resetBtn.setEnabled(false);
			
		}

		else if(command.equals(deleteBtn.getText()))
		{
			mtr.deleteFromDB(shopItemIdTF.getText());
			ur.deleteUser(shopItemIdTF.getText());
			
			JOptionPane.showMessageDialog(this,"Deleted");
			
			shopItemIdTF.setText("");
			shopItemNameTF.setText("");
			itemQuantityTF.setText("");
			manuDateTF.setText("");
			itemPriceTF.setText("");
			
			shopItemIdTF.setEnabled(true);
			shopItemNameTF.setEnabled(true);
			itemQuantityTF.setEnabled(true);
			manuDateTF.setEnabled(true);
			itemPriceTF.setEnabled(true);
	
			loadBtn.setEnabled(true);
			insertBtn.setEnabled(true);
			//updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			//resetBtn.setEnabled(false);
		}
		else if(command.equals(getAllBtn.getText()))
		{
			String data[][] = mtr.getAllMovie();
			String head[] = {"Item ID", "Item Name", "Quantity", "Manufacture", "Item Price"};
			
			panel.remove(shopItemTableSP);
			
			shopItemTable = new JTable(data,head);
			shopItemTable.setEnabled(false);
			shopItemTableSP = new JScrollPane(shopItemTable);
			shopItemTableSP.setBounds(345, 100, 430, 150);
			panel.add(shopItemTableSP);
			
			panel.revalidate();
			panel.repaint();
			
		}
		else if(command.equals(backBtn.getText()))
		{
			LoginFrame lf = new LoginFrame();
			lf.setVisible(true);
			this.setVisible(false);
		}

		else{}
		
	}
}