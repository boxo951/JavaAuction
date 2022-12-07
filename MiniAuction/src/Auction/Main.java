package Auction;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main {

	static JFrame auctionF = new JFrame();

	public static void main(String[] args) {

		auctionF.setBounds(100, 200, 615, 473);

		auctionF.setTitle("미니 경매 프로그램");
		auctionF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		auctionF.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 90, 575, 318);
		auctionF.getContentPane().add(panel);
		panel.setLayout(null);

		JButton ADMINButton = new JButton("판매자");
		ADMINButton.setBounds(369, 130, 140, 47);
		panel.add(ADMINButton);

		JButton CUSTOMERButton = new JButton("구매자");
		CUSTOMERButton.setBounds(59, 130, 140, 47);
		panel.add(CUSTOMERButton);

		JLabel TLabel = new JLabel("미니 경매 프로그램");
		TLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TLabel.setForeground(Color.ORANGE);
		TLabel.setFont(new Font("한컴 윤고딕 250", Font.PLAIN, 23));
		TLabel.setBounds(12, 51, 575, 29);
		auctionF.getContentPane().add(TLabel);

		auctionF.setVisible(true);

		CUSTOMERButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				new Customer();

			}

		});

		ADMINButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				new Admin();

			}

		});

	}

}
