import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Setup implements KeyListener {

	JFrame frame = new JFrame();
	JPanel panelTitle = new JPanel();
	JPanel panelNames = new JPanel();
	JLabel labelLevel = new JLabel();
	JLabel labelNames = new JLabel();
	JLabel labelLocation = new JLabel();
	ArrayList<Student> students = new ArrayList<Student>();
	ArrayList<String> levelNumber = new ArrayList<String>();
	int currentLevel = 0;

	public static void main(String[] args) {
		Setup set = new Setup();
		set.setup();

	}

	public void setup() {
		System.out.println("ran setup");

		levelNumber.add("Level 1");
		levelNumber.add("Level 2");
		levelNumber.add("Level 3");
		levelNumber.add("Level 4");
		levelNumber.add("Level 5");

		frame.add(panelTitle);
		panelTitle.setBounds(0,0,300,100);
		panelTitle.setLayout(null);
		
		frame.add(panelNames);
		panelNames.setBounds(0,200,200,100);
		panelNames.setLayout(null);
		
		
		panelTitle.add(labelLevel);
		labelLevel.setBounds(0,0,50,30);
		
		panelTitle.add(labelNames);
		labelNames.setBounds(0,20,50,50);
		
		panelTitle.add(labelLocation);
		labelLocation.setBounds(150,20,90,50);
		
		labelLevel.setVisible(true);
		labelNames.setVisible(true);
		labelLocation.setVisible(true);
		
		labelLevel.setText("Level: 0");
		labelNames.setText("Names:");
		labelLocation.setText("Location:");
		
		
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.addKeyListener(this);

		LoadInfo("NamesLocations");
		
	}

	public void levelChange() {
		System.out.println(currentLevel);
		labelLevel.setText("Level: " + currentLevel);
		
		for (int i = 0; i < students.size(); i++) {
			if(students.get(i).Level == currentLevel) {
				JLabel label = new JLabel();
				panelNames.add(label);
				label.setBounds(0,i*20, 20,200);
				
			}
		}
		
		
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == 32) {

			if (currentLevel == 5) {
				currentLevel = 0;
			} else {
				currentLevel++;
			}

			levelChange();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void LoadInfo(String string) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			String s = br.readLine();
			while(s != null) {
				students.add(SortStudentInfo(s));
				s= br.readLine();
			}
			
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Student SortStudentInfo(String line) {
		int commaLocation = 0;
		int semicolonLocation = 0;
		Student student = new Student();
		
		for (int i = 0; i < line.length(); i++) {
			if(line.substring(i, i+1).equals(",")) {
				commaLocation=i;
			}
			if(line.substring(i,i+1).equals(";")) {
				semicolonLocation = i;
			}
		}
		
		String l = line.substring(0,1);
		int level = Integer.parseInt(l);
		student.Level= level;
		String n = line.substring(commaLocation+2, semicolonLocation-1);
		student.Name= n;
		
		String location = line.substring(semicolonLocation+2, line.length());
		student.Location= location;
		
		return student;
	}
	
	
}
