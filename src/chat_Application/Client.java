package chat_Application;

import javax.swing.*; // Import Swing 
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {

	JTextField text;
//	JPanel a1;
	static JPanel messagePanel;
	JScrollPane scrollPane;
	static DataOutputStream dout;
	static Box vertical = Box.createVerticalBox();

	static JFrame f = new JFrame();

	Client() {
		f.setLayout(null);
		// Panel
		// Panel is used for doing something on above the frame
		JPanel panel = new JPanel();
		panel.setBackground(new Color(137, 207, 240));
		panel.setBounds(0, 0, 400, 70); // Will set the co-ordinates, where to put the header
		f.add(panel); // We can set component on above the frame
		panel.setLayout(null);

		// Image
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/arrow.jpg"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);

		JLabel back = new JLabel(i3);
		back.setBounds(5, 20, 25, 25);
		panel.add(back);

		// Back button action
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});

		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/2.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);

		JLabel profile = new JLabel(i6);
		profile.setBounds(50, 10, 50, 50);
		panel.add(profile);

		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));
		Image i8 = i7.getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);

		JLabel video = new JLabel(i9);
		video.setBounds(290, 20, 40, 30);
		panel.add(video);

		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);

		JLabel phone = new JLabel(i12);
		phone.setBounds(230, 20, 40, 30);
		panel.add(phone);

		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);

		JLabel morevert = new JLabel(i15);
		morevert.setBounds(350, 20, 15, 25);
		panel.add(morevert);

		// When you have to write something you can write with the help of JLabel
		JLabel name = new JLabel("Manav");
		name.setBounds(110, 15, 100, 20);
		name.setFont(new Font("SAN_SARIF", Font.BOLD, 18));
		panel.add(name);

		JLabel status = new JLabel("Active");
		status.setBounds(110, 40, 100, 20);
//		name.setFont(new Font("SAN_SARIF", Font.BOLD,18));
		panel.add(status);

//		a1 = new JPanel();
//		a1.setBounds(1, 70, 460, 580);
//		add(a1);
		messagePanel = new JPanel();
		messagePanel.setBounds(1, 70, 460, 580);
		f.add(messagePanel);

		scrollPane = new JScrollPane(messagePanel);
		scrollPane.setBounds(1, 70, 460, 580);
		f.add(scrollPane);

		text = new JTextField();
		text.setBounds(5, 650, 280, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);

		JButton send = new JButton("Send");
		send.setBounds(290, 650, 100, 40);
		send.setBackground(new Color(137, 207, 240));
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);
		f.add(send);

		f.setSize(400, 700); // Will set the size of frame
		f.setLocation(600, 15); // Set the location
		f.setUndecorated(true);

		Container c = f.getContentPane();
		c.setBackground(new Color(255, 255, 255));
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		try {
			String out = text.getText();

			JPanel p2 = formatLabel(out);
//		    p2.setLayout(new FlowLayout(FlowLayout.RIGHT));

			messagePanel.setLayout(new BorderLayout());

			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
			vertical.add(right);
			// Add the new message panel to the existing vertical box
			vertical.add(p2);
			vertical.add(Box.createVerticalStrut(15));

			// Update the layout of the message panel
//		    messagePanel.setLayout(new BorderLayout());
			messagePanel.add(vertical, BorderLayout.PAGE_START);
			dout.writeUTF(out);

			// Update UI
//		    messagePanel.revalidate();
//		    messagePanel.repaint();
//
			text.setText("");
			f.repaint();
			f.invalidate();
			f.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel output = new JLabel("<html> <p style=\"width: 50px\">" + out + "</p> </html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(135, 205, 235));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 50));
		panel.add(output);
		return panel;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Client c = new Client();
		try {
			Socket s = new Socket("127.0.0.1", 6001);
			DataInputStream din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			while (true) {
				messagePanel.setLayout(new BorderLayout());
				String msg = din.readUTF();
				JPanel panel = formatLabel(msg);

				JPanel right = new JPanel(new BorderLayout());
				right.add(panel, BorderLayout.LINE_START);
				vertical.add(right);

				vertical.add(Box.createVerticalStrut(15));
				messagePanel.add(vertical, BorderLayout.PAGE_START);
				f.validate();
				s.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
