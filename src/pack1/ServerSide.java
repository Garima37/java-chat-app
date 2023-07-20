package pack1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerSide extends JFrame {

	private static JPanel contentPane;
	private static JTextField chat;
	private static JTextArea msgBox;
	
	static ServerSocket ss;
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
					ServerSide frame = new ServerSide();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
			try {
				ss= new ServerSocket(4444);
				socket=ss.accept();
				
				while(true) 
				{
				dis= new DataInputStream(socket.getInputStream());
				String msg= dis.readUTF();
				
				String data= msgBox.getText()+"\n"+ "Client  Message: "+msg;
				msgBox.setText(data);
				}
		
		}
	            catch (IOException e) {
				
				e.printStackTrace();
				msgBox.setText(msgBox.getText()+"\n"+"Network Issue! ");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					
					e2.printStackTrace();
				}
				
			}
	}
	
	/**
	 * Create the frame.
	 */
	public ServerSide() {
		setTitle("Server Chat Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 150, 650, 500);    //col,row,width,height
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Message");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(34, 12, 168, 40);
		contentPane.add(lblNewLabel);
		
		chat = new JTextField();
		chat.setFont(new Font("Tahoma", Font.BOLD, 20));
		chat.setBounds(34, 52, 393, 46);
		contentPane.add(chat);
		chat.setColumns(10);
		
		JLabel lblChatMessage = new JLabel(" Chat Message of server side");
		lblChatMessage.setForeground(new Color(0, 139, 139));
		lblChatMessage.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblChatMessage.setBounds(34, 137, 368, 40);
		contentPane.add(lblChatMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(36, 178, 558, 250);
		contentPane.add(scrollPane);
		
		msgBox = new JTextArea();
		msgBox.setBackground(new Color(255, 255, 255));
		msgBox.setFont(new Font("Monospaced", Font.BOLD, 16));
		msgBox.setText("Chat Message:-");
		scrollPane.setViewportView(msgBox);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String text= chat.getText();
				if(text.isBlank())
					JOptionPane.showMessageDialog(getParent(), "Nothing to send!");
				else
				{
				
					try {
					dos= new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(text);
					chat.setText(" ");
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
					msgBox.setText(msgBox.getText()+"\n"+"Network Issue! ");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					dispose();
				}
					msgBox.setText(msgBox.getText()+"\n"+"Server side: "+text);
				}
				}
			}
		);
		btnNewButton.setBackground(new Color(32, 178, 170));
		btnNewButton.setForeground(new Color(0, 0, 205));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		btnNewButton.setBounds(447, 50, 139, 46);
		contentPane.add(btnNewButton);
	}
}
