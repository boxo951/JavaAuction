package Auction;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Admin {

	private JLabel timerLabel;

	private JTable Atable;

	private JTextField nameData;

	private JTextField priceData;

	private JTextField path;

	public static String adminNameData = "", adminPriceData = "";

	public static ImageIcon adminImageData;

	JFrame adminF = new JFrame();

	Timer timer;

	public static int sec = 60;

	public Admin() {
		adminF.setAutoRequestFocus(false);
		adminF.setBounds(100, 200, 615, 473);
		adminF.setTitle("판매");
		adminF.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 531, 223);
		adminF.getContentPane().add(panel);
		panel.setLayout(null);

		JButton SELECTIMAGEButton = new JButton("\uC774\uBBF8\uC9C0");
		SELECTIMAGEButton.setBounds(0, 164, 88, 23);
		panel.add(SELECTIMAGEButton);

		JLabel price = new JLabel("\uAC00\uACA9");
		price.setBounds(12, 100, 57, 15);
		panel.add(price);

		JLabel lblNewLabel_1 = new JLabel("\uC0C1\uD488\uBA85");
		lblNewLabel_1.setBounds(12, 39, 57, 15);
		panel.add(lblNewLabel_1);

		nameData = new JTextField();
		nameData.setBounds(96, 36, 116, 21);
		panel.add(nameData);
		nameData.setColumns(10);

		priceData = new JTextField();
		priceData.setBounds(96, 97, 116, 21);
		panel.add(priceData);
		priceData.setColumns(10);

		path = new JTextField();
		path.setBounds(96, 164, 116, 23);
		panel.add(path);
		path.setColumns(10);

		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(null);
		imageLabel.setBounds(288, 39, 171, 134);
		panel.add(imageLabel);

		JButton ADDITEMButton = new JButton("\uCD94\uAC00");
		ADDITEMButton.setBounds(40, 389, 97, 23);
		adminF.getContentPane().add(ADDITEMButton);

		JButton CLOSEButton = new JButton("\uB098\uAC00\uAE30");
		CLOSEButton.setBounds(447, 389, 97, 23);
		adminF.getContentPane().add(CLOSEButton);

		JButton startButton = new JButton("\uC2DC\uC791");
		startButton.setBounds(241, 389, 97, 23);
		adminF.getContentPane().add(startButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 253, 531, 106);
		adminF.getContentPane().add(scrollPane);

		Atable = new JTable();
		scrollPane.setViewportView(Atable);

		JLabel label1 = new JLabel("");
		label1.setBounds(472, 21, 57, 15);
		adminF.getContentPane().add(label1);

		JLabel timerLabel = new JLabel("");
		timerLabel.setBounds(403, 21, 57, 15);
		adminF.getContentPane().add(timerLabel);

		tableData();

		adminF.setVisible(true);

		startButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				new Customer();

			}

		});

		ADDITEMButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				if (nameData.getText().equals("") || path.getText().equals("") || priceData.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Please Fill All Fields to add Record.");

				} else {

					String sql = "insert into auction" + "(ITEM_NAME,IMAGE,PRICE)" + "values (?,?,?)";

					try {

						File f = new File(path.getText());

						InputStream inputStream = new FileInputStream(f);

						Class.forName("com.mysql.cj.jdbc.Driver");

						Connection connection = DriverManager
								.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "1234");

						PreparedStatement statement = connection.prepareStatement(sql);

						statement.setString(1, nameData.getText());

						statement.setBlob(2, inputStream);

						statement.setString(3, priceData.getText());

						statement.executeUpdate();

						JOptionPane.showMessageDialog(null, "DETAILS ADDED SUCCESSFULLY");

						nameData.setText("");

						priceData.setText("");

						imageLabel.setIcon(null);

						path.setText("");

					} catch (Exception ex) {

						JOptionPane.showMessageDialog(null, ex.getMessage());

					}

					tableData();

				}

			}

		});

		SELECTIMAGEButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "png");

				fileChooser.addChoosableFileFilter(filter);

				int rs = fileChooser.showSaveDialog(null);

				if (rs == JFileChooser.APPROVE_OPTION) {

					File selectedImage = fileChooser.getSelectedFile();

					path.setText(selectedImage.getAbsolutePath());

					imageLabel.setIcon(resize(path.getText()));

				}

			}

		});

		Atable.addMouseListener(new MouseAdapter() {

			@Override

			public void mouseClicked(MouseEvent e) {

				DefaultTableModel dm = (DefaultTableModel) Atable.getModel();

				int selectedRow = Atable.getSelectedRow();

				adminNameData = dm.getValueAt(selectedRow, 0).toString();

				nameData.setText(adminNameData);

				adminPriceData = dm.getValueAt(selectedRow, 2).toString();

				priceData.setText(adminPriceData);

			}

		});

		CLOSEButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				adminF.dispose();

			}

		});

	}

	public ImageIcon resize(String path) {

		ImageIcon myImg = new ImageIcon(path);

		Image image = myImg.getImage();

		Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

		ImageIcon finalImage = new ImageIcon(newImage);

		return finalImage;

	}

	public void startTimer() {

		timer = new Timer(1000, new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				sec--;

				if (sec == 0) {

					timer.stop();

					tableData();

				}

				else
					timerLabel.setText(Integer.toString(sec));

			}

		});
	}

	public void tableData() {

		try {

			String a = "Select* from auction";

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC",
					"root", "1234");

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(a);

			Atable.setModel(buildTableModel(rs));

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
