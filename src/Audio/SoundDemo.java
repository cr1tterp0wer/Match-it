package Audio;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SoundDemo extends JFrame {

	public SoundDemo() {
		final SoundTrack backgroundMusic = SoundTrack.TRACK_TWO;
	
		// Set up UI components
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnSound1 = new JButton("sound1");
		btnSound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundEffect.MENU_OPEN.play();
			}
		});
		cp.add(btnSound1);
		
		JButton btnSound2 = new JButton("sound2");
		btnSound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundEffect.MENU_CLOSE.play();
			}
		});
		cp.add(btnSound2);
		
		JButton btnSound3 = new JButton("Play");
		btnSound3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.play();
				try {
					backgroundMusic.setVolume(0.5f);
				} catch (OutOfRangeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		cp.add(btnSound3);
		
		JButton btnSound4 = new JButton("Stop");
		btnSound4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.stop();
				
			}
		});
		cp.add(btnSound4);
		
		JButton btnSound5 = new JButton("Pause");
		btnSound5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.pause();
				
			}
		});
		cp.add(btnSound5);
		
		
		JButton btnSound6 = new JButton("+");
		btnSound6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.volumeUp();
				SoundEffect.volumeUp();
			}
		});
		cp.add(btnSound6);
		
		JButton btnSound7 = new JButton("-");
		btnSound7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.volumeDown();
				SoundEffect.volumeDown();
			}
		});
		cp.add(btnSound7);
		
		
		final JButton btnSound8 = new JButton("OFF");
		btnSound8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundMusic.switchSound();
				SoundEffect.switchSound();
				if (btnSound8.getText().equals("ON"))
					btnSound8.setText("OFF");
				else
					btnSound8.setText("ON");
			}
		});
		cp.add(btnSound8);
		


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test SoundEffect");
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SoundDemo();
	}
}