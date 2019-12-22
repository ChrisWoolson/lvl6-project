import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Setup {

	JFrame frame = new JFrame();
	JPanel panelTitle = new JPanel();
	JPanel panelNames = new JPanel();
int level=0;
	JLabel labelLevel = new JLabel();
	JLabel labelNames = new JLabel();
	JLabel labelLocation = new JLabel();
	ArrayList<Student> students = new ArrayList<Student>();
	
	

	public static void main(String[] args) {
		
		Setup set = new Setup();
		set.setup();

	}

	public void setup() {
		String input = JOptionPane.showInputDialog("What level is this screen displaying?");
		level = Integer.parseInt(input);

		
		frame.add(panelTitle);
		panelTitle.setBounds(0, 0, 300, 100);
		panelTitle.setLayout(null);

		//frame.add(panelNames);
		//panelNames.setBounds(0, 200, 300, 300);
		//panelNames.setLayout(null);

		panelTitle.add(labelLevel);
		labelLevel.setBounds(0, 0, 50, 30);

		panelTitle.add(labelNames);
		labelNames.setBounds(0, 20, 50, 50);

		panelTitle.add(labelLocation);
		labelLocation.setBounds(150, 20, 80, 50);

		labelLevel.setVisible(true);
		labelNames.setVisible(true);
		labelLocation.setVisible(true);

		labelLevel.setText("Level: "+level);
		labelNames.setText("Names:");
		labelLocation.setText("Location:");

		frame.setVisible(true);
		frame.setSize(500, 500);
		
		LoadInfo("/Users/league/Desktop/lvl6-project/pike13.json");

		

		

	}

	/* public void levelChange() {
		int slotNum = 0;

		panelNames.removeAll();
		panelNames.revalidate();
		panelNames.setBackground(Color.gray);

		labelLevel.setText("Level: " + level);

		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).Level == currentLevel) {
				JLabel label = new JLabel();
				panelNames.add(label);
				label.setBounds(0, (slotNum * 30) + 100, 250, 30);
				label.setText(students.get(i).Name + "                 " + students.get(i).Location);
				slotNum++;
			}
		}

	}
*/
	
	
/*
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
*/
	
	
	public void LoadInfo(String string) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			String s = br.readLine();
			while (s != null) {
				students = parseJson(s);
				s = br.readLine();
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

	public ArrayList<Student> parseJson(String line) {

		int startStudent = 0;
		int closeStudent = 0;
		int openP = 0;
		int closeP = 0;

		ArrayList<Student> students = new ArrayList<Student>();

		

		for (int i = 2; i < line.length(); i++) {
			if (line.substring(i, i + 1).equals("{")) {
				if (openP == 0) {
					startStudent = i;
				}
				openP++;
			}
			if (line.substring(i, i + 1).equals("}")) {
				closeP++;
			}
			if (openP == closeP && openP != 0) {
				closeStudent = i;

				Student s = SortStudent(line.substring(startStudent, closeStudent));
				students.add(s);
			}

		}
		
		return students;
		
	}

	public Student SortStudent(String studentLine) {
		boolean firstNameFound = false;
		boolean lastNameFound = false;
		boolean classLevelFound = false;
		boolean locationFound = false;
		System.out.println("sorting student");
		System.out.println(studentLine);
		String firstName = "";
		String lastName = "";
		String classLevel = "";
		String location = "";
		ArrayList<String>  betweenQuotes = getBetweenQuotes(studentLine);
		
		for (String string : betweenQuotes) {
			System.out.println(string);
		}
		
		for (int i = 0; i < betweenQuotes.size(); i++) {
			if(betweenQuotes.get(i).equals("first_name") && firstNameFound == false) {
				
				firstName = betweenQuotes.get(i+2);
				System.out.println("First name is "+firstName);
				firstNameFound = true;
			}
			if(betweenQuotes.get(i).equals("last_name") && lastNameFound == false) {
				lastName = betweenQuotes.get(i+2);
				System.out.println("Last name is "+lastName);
				lastNameFound= true;
			}
			if(betweenQuotes.get(i).equals("Current Class Level") && classLevelFound == false) {
				classLevel = betweenQuotes.get(i+4);
				System.out.println("Class Level is "+classLevel);
				classLevelFound= true;
			}
			if(betweenQuotes.get(i).equals("location") && locationFound == false) {
				location = betweenQuotes.get(i+6);
				System.out.println("location is "+location);
				locationFound = true;
			}
		}
		Student s = new Student();
		s.Name = firstName+" "+lastName;
		s.Location = location;
		s.Level= Integer.parseInt(classLevel);
		/* int startFirstName = 0;
		int endFirstName = 0;
		Student s = new Student();
		

		for (int i = 0; i < studentLine.length(); i++) {

			if (studentLine.substring(i, i + 1).equals("\"")) {

				startFirstName = i;
				
				for (int j = i; j < studentLine.length(); j++) {
					if (studentLine.substring(j, j + 1).equals("\"")) {
						endFirstName = j;
					//Search between parenthesis here. Do for next class
						if(studentLine.substring(startFirstName, endFirstName).equals("first_name")) {
							System.out.println(studentLine.substring(endFirstName+2,));
						}
					break;
					}
				}
			}

		} */

		return s;

	}
	
	ArrayList<String> getBetweenQuotes(String string) {
		ArrayList<String> inBetweenQuotes = new ArrayList<String>();
		for (int i = 0; i < string.length(); i++) {
			if(string.substring(i,i+1).equals("\"")) {
				for (int j = i+1; j < string.length(); j++) {
					if(string.substring(j,j+1).equals("\"")) {
						inBetweenQuotes.add(string.substring(i+1,j));
						break;
					}
				}
			}
		}
		return inBetweenQuotes;
	}

}
