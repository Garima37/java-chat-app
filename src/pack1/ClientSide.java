package pack1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class ClientSide extends JFrame {

	private JPanel contentPane;
	private JTextField chat;
	private static JTextArea textBox;
	static Socket socket;
	static DataInputStream dis;
	DataOutputStream dos;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientSide frame = new ClientSide();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					textBox.setText(textBox.getText()+"\n"+"Network Issue! ");
					try {
						Thread.sleep(3000);
					} 
					catch (InterruptedException e2)
					{
						
						e2.printStackTrace();
					}
				}
			}
		});
		
		try {
			socket=new Socket("localhost",4444);
			
			while(true) 
			{
			dis= new DataInputStream(socket.getInputStream());
			String msg= dis.readUTF();
			
			String data= textBox.getText()+"\n"+ "Server Message: "+msg;
			textBox.setText(data);
			
			}
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ClientSide() {
		setTitle("Client Chat Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(720, 150, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Message");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(28, 24, 157, 32);
		contentPane.add(lblNewLabel);
		
		chat = new JTextField();
		chat.setFont(new Font("Tahoma", Font.BOLD, 16));
		chat.setBounds(28, 67, 447, 48);
		contentPane.add(chat);
		chat.setColumns(10);
		
		JLabel lblChatMessageOf = new JLabel("Chat Message of client side");
		lblChatMessageOf.setForeground(new Color(0, 139, 139));
		lblChatMessageOf.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblChatMessageOf.setBounds(28, 139, 332, 32);
		contentPane.add(lblChatMessageOf);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(28, 181, 563, 247);
		contentPane.add(scrollPane);
		
		textBox = new JTextArea();
		textBox.setFont(new Font("Monospaced", Font.BOLD, 16));
		textBox.setText("Chat Message:-");
		scrollPane.setViewportView(textBox);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//get data from textField
				//textfieldobject.getText();  setText();
				String text= chat.getText();
				
				if(text.isBlank())
					JOptionPane.showMessageDialog(getParent(), "Nothing to send!");
				else {
				try {
					dos= new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(text);
					chat.setText(" ");
					
				} catch (IOException e1) {
					e1.printStackTrace();
					textBox.setText(textBox.getText()+"\n"+"Network Issue! ");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					dispose();
					
				}
				textBox.setText(textBox.getText()+"\n"+"Client side: "+text);
				}
				
				
				
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 205));
		btnNewButton.setBackground(new Color(0, 139, 139));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		btnNewButton.setBounds(485, 67, 106, 48);
		contentPane.add(btnNewButton);
		
		
	}
}
