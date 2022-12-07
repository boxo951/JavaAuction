package Auction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Customer extends Admin {

	private JTextField bidName;

	private JTextField bidPrice;

	private String priceS = "", name = "", bidder = "";

	private ImageIcon imageS;

	private int bid;

	Timer timer;

	JFrame customerF = new JFrame();

	private JTable Btable;

	public Customer() {

		customerF.setVisible(true);
		customerF.setBounds(100, 200, 615, 473);
		customerF.setTitle("구매화면");
		customerF.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 43, 534, 172);
		customerF.getContentPane().add(panel);
		panel.setLayout(null);

		bidName = new JTextField();
		bidName.setBounds(82, 41, 116, 21);
		panel.add(bidName);
		bidName.setColumns(10);

		bidPrice = new JTextField();
		bidPrice.setBounds(82, 120, 116, 21);
		panel.add(bidPrice);
		bidPrice.setColumns(10);

		JLabel lblNewLabel = new JLabel("\uAD6C\uB9E4\uC790 :");
		lblNewLabel.setBounds(12, 44, 57, 15);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\uC785\uCC30\uAC00 :");
		lblNewLabel_1.setBounds(12, 123, 57, 15);
		panel.add(lblNewLabel_1);

		Btable = new JTable();
		Btable.setBounds(328, 21, 161, 115);
		panel.add(Btable);

		JButton ADDBIDButton = new JButton("\uC785\uCC30");
		ADDBIDButton.setBounds(12, 385, 181, 23);
		customerF.getContentPane().add(ADDBIDButton);

		JButton close = new JButton("\uB098\uAC00\uAE30");
		close.setBounds(414, 385, 97, 23);
		customerF.getContentPane().add(close);

		JLabel timerLabel = new JLabel("");
		timerLabel.setBounds(217, 10, 134, 15);
		customerF.getContentPane().add(timerLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(331, 242, 215, 118);
		customerF.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel price1 = new JLabel("PRICE");
		price1.setBounds(12, 49, 57, 15);
		panel_1.add(price1);

		JLabel image1 = new JLabel("IMAGE");
		image1.setBounds(12, 73, 57, 15);
		panel_1.add(image1);

		JLabel itemName = new JLabel("itemName");
		itemName.setBounds(12, 25, 68, 15);
		panel_1.add(itemName);

		Btable = new JTable();

		Btable.setBounds(163, 124, -152, 59);
		panel_1.add(Btable);

		sec = Admin.sec;

		startTimer();

		timer.start();

		name = Admin.adminNameData;

		priceS = Admin.adminPriceData;

		imageS = Admin.adminImageData;

		itemName.setText("ITEM");

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(50, 263, 187, 83);
		customerF.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		ADDBIDButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				if (bidName.getText().equals("") || bidPrice.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Please Fill All Fields to add Record.");

				} else {

					try {

						String sql = "insert into bid" + "(Bidder_Name,Bid)" + "values (?,?)";

						Class.forName("com.mysql.cj.jdbc.Driver");

						Connection connection = DriverManager
								.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "1234");

						PreparedStatement statement = connection.prepareStatement(sql);

						statement.setString(1, bidName.getText());

						statement.setInt(2, Integer.parseInt(bidPrice.getText()));

						statement.executeUpdate();

						JOptionPane.showMessageDialog(null, "ITEM ADDED SUCCESSFULLY");

						bidName.setText("");

						bidPrice.setText("");

					} catch (Exception ex) {

						JOptionPane.showMessageDialog(null, ex.getMessage());

					}

					tableData();

				}

			}

		});

		close.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				customerF.dispose();

			}

		});

	}

	public void tableData() {

		try {

			String a = "Select* from bid";

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC",
					"root", "1234");

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(a);

			Btable.setModel(buildTableModel(rs));

		} catch (Exception ex1) {

			JOptionPane.showMessageDialog(null, ex1.getMessage());

		}

	}

	public static DefaultTableModel buildTableModel(ResultSet rs)

			throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		Vector<String> columnNames = new Vector<String>();

		int columnCount = metaData.getColumnCount();

		for (int column = 1; column <= columnCount; column++) {

			columnNames.add(metaData.getColumnName(column));

		}

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		while (rs.next()) {

			Vector<Object> vector = new Vector<Object>();

			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {

				vector.add(rs.getObject(columnIndex));

			}

			data.add(vector);

		}

		return new DefaultTableModel(data, columnNames);

	}

}