import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Setup {

	JFrame frame = new JFrame();
	JPanel panelTitle = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.fillRect(0,  100, width,  3);
		}
	};
	JPanel panelNames = new JPanel();
	int level = 0;
	JLabel labelLevel = new JLabel();
	JLabel labelNames = new JLabel();
	JLabel labelLocation = new JLabel();
	ArrayList<Student> students = new ArrayList<Student>();
	// ArrayList<JPanel> studentPanels = new ArrayList<JPanel>();
	ArrayList<JLabel> studentLabels = new ArrayList<JLabel>();
int width = 0;
int height = 0;
	// this int will be modified so that it scrolls
	int y = 0;

	public static void main(String[] args) {

		Setup set = new Setup();
		set.setup();

	}

	public void setup() {
		String input = JOptionPane.showInputDialog("What level is this screen displaying?");
		level = Integer.parseInt(input);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
frame.setSize(d);
width= d.width;
height= d.height;

		frame.add(panelNames);
		frame.add(panelTitle);
		panelTitle.setBounds(0, 0, width, 100);
		panelNames.setBounds(0, 101, width, height-101);
		
		panelTitle.setLayout(null);
		panelNames.setLayout(null);
		// frame.add(panelNames);
		// panelNames.setBounds(0, 200, 300, 300);
		// panelNames.setLayout(null);

		panelTitle.add(labelLevel);
		
		labelLevel.setFont(new Font("FontLevel", Font.BOLD, 40));
		labelLevel.setBounds(0, 0, width, 50);

		panelTitle.add(labelNames);
		labelNames.setBounds(0, 50, 250, 50);
		labelNames.setFont(new Font("FontNames", Font.ITALIC, 50));
		
		panelTitle.add(labelLocation);
		labelLocation.setBounds(475, 50, 250, 50);
		labelLocation.setFont(new Font("FontLocation", Font.ITALIC, 50));
		
		
		labelLevel.setVisible(true);
		labelNames.setVisible(true);
		labelLocation.setVisible(true);

		labelLevel.setText("Level: " + level);
		labelNames.setText("Name:");
		labelLocation.setText("Location:");

		//frame.setSize(500, 500);

		// panelNames.setVisible(true);

		LoadInfo("/Users/league/Desktop/lvl6-project/realjson");
		System.out.println();
		System.out.println("~~~~~~~~Loaded info for " + level + "~~~~~~~~~");
		ArrayList<ArrayList<Student>> sortedStudentsInLevels = sortStudentsIntoLevels(students);

		for (int i = 0; i < sortedStudentsInLevels.get(level).size(); i++) {
			String spacing = "";
			for (int j = 0; j < 28 - sortedStudentsInLevels.get(level).get(i).Name.length(); j++) {
				spacing += " ";
			}
			// studentPanels.add(new JPanel());
			// studentPanels.get(i).setVisible(true);
			// setUpStudentPanel(studentPanels.get(i), students.get(i));
			studentLabels.add(new JLabel());
			// studentLabels.get(i).setVisible(true);
			System.out.println(sortedStudentsInLevels.get(level).get(i).Name + "      "
					+ sortedStudentsInLevels.get(level).get(i).Location);
			studentLabels.get(i).setBounds(0, (y + (50 * i)), width, 50);
			studentLabels.get(i).setFont(new Font("Fonty", Font.PLAIN, 40));
			studentLabels.get(i).setText(sortedStudentsInLevels.get(level).get(i).Name + spacing
					+ sortedStudentsInLevels.get(level).get(i).Location);

			studentLabels.get(i).setVisible(true);
			panelNames.add(studentLabels.get(i));

		}
		frame.setVisible(true);

		/*
		 * for (int i = 0; i < students.size(); i++) {
		 * 
		 * String spacing = ""; for (int j = 0; j < 28 - students.get(i).Name.length();
		 * j++) { spacing += " "; } // studentPanels.add(new JPanel()); //
		 * studentPanels.get(i).setVisible(true); //
		 * setUpStudentPanel(studentPanels.get(i), students.get(i));
		 * studentLabels.add(new JLabel()); // studentLabels.get(i).setVisible(true);
		 * System.out.println(students.get(i).Name + "      " +
		 * students.get(i).Location); studentLabels.get(i).setBounds(0, (y + (20 * i)),
		 * 500, 20);
		 * 
		 * studentLabels.get(i).setText(students.get(i).Name + spacing +
		 * students.get(i).Location);
		 * 
		 * studentLabels.get(i).setVisible(true); panelNames.add(studentLabels.get(i));
		 * }
		 */

	}

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
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Student> parseJson(String line) {
		int openP = 0;
		int closeP = 0;
		// This cuts out the {"People":[
		line = line.substring(11, line.length());
		System.out.println("Whole Line:   " + line);
		for (int i = 0; i < line.length(); i++) {
			if (line.substring(i, i + 1).equals("{")) {
				openP++;
			}
			if (line.substring(i, i + 1).equals("}")) {
				closeP++;
			}
			if (openP == closeP && closeP != 0) {
				Student s = SortStudent(line.substring(0, i));
				students.add(s);
				openP = 0;
				closeP = 0;
				line = line.substring(i, line.length());
				i = 0;
				System.out.println("New line    " + line);
			}
		}

		/*
		 * int startStudent = 0; int closeStudent = 0; int openP = 0; int closeP = 0;
		 * 
		 * ArrayList<Student> students = new ArrayList<Student>();
		 * 
		 * 
		 * 
		 * for (int i = 2; i < line.length(); i++) {
		 * 
		 * 
		 * if (line.substring(i, i + 1).equals("{")) { if (openP == 0) { startStudent =
		 * i; } openP++; } if (line.substring(i, i + 1).equals("}")) { closeP++; }
		 * 
		 * if (openP == closeP && openP != 0 ) {
		 * 
		 * closeStudent = i;
		 * 
		 * Student s = SortStudent(line.substring(startStudent, closeStudent));
		 * students.add(s);
		 * 
		 * 
		 * openP = 0; closeP = 0; startStudent = 0; closeStudent = 0; i=0; line =
		 * line.substring(closeStudent+2, line.length());
		 * 
		 * System.out.println("Line: "+line);
		 * 
		 * 
		 * }
		 * 
		 * } System.out.println("returning students"); return students;
		 */
		return students;
	}

	public Student SortStudent(String studentLine) {

		boolean firstNameFound = false;
		boolean lastNameFound = false;
		boolean classLevelFound = false;
		boolean locationFound = false;
		System.out.println("sorting student");
		System.out.println("Student line: " + studentLine);
		String firstName = "";
		String lastName = "";
		String classLevel = "";
		String location = "";
		ArrayList<String> betweenQuotes = getBetweenQuotes(studentLine);

		for (String string : betweenQuotes) {

		}

		for (int i = 0; i < betweenQuotes.size(); i++) {
			if (betweenQuotes.get(i).equals("first_name") && firstNameFound == false) {

				firstName = betweenQuotes.get(i + 2);
				System.out.println("First name is " + firstName);
				firstNameFound = true;
			}
			if (betweenQuotes.get(i).equals("last_name") && lastNameFound == false) {
				lastName = betweenQuotes.get(i + 2);
				System.out.println("Last name is " + lastName);
				lastNameFound = true;
			}
			if (betweenQuotes.get(i).equals("Current Class Level") && classLevelFound == false) {
				classLevel = betweenQuotes.get(i + 4);
				System.out.println("Class Level is " + classLevel);
				classLevelFound = true;
			}
			if (betweenQuotes.get(i).equals("location") && locationFound == false) {
				location = betweenQuotes.get(i + 6);
				System.out.println("location is " + location);
				locationFound = true;
			}
		}
		Student s = new Student();

		s.Name = firstName + " " + lastName;
		s.Location = location;
		s.Level = Integer.parseInt(classLevel);
		System.out.println(s.Name + " " + s.Level);

		/*
		 * int startFirstName = 0; int endFirstName = 0; Student s = new Student();
		 * 
		 * 
		 * for (int i = 0; i < studentLine.length(); i++) {
		 * 
		 * if (studentLine.substring(i, i + 1).equals("\"")) {
		 * 
		 * startFirstName = i;
		 * 
		 * for (int j = i; j < studentLine.length(); j++) { if (studentLine.substring(j,
		 * j + 1).equals("\"")) { endFirstName = j; //Search between parenthesis here.
		 * Do for next class if(studentLine.substring(startFirstName,
		 * endFirstName).equals("first_name")) {
		 * System.out.println(studentLine.substring(endFirstName+2,)); } break; } } }
		 * 
		 * }
		 */

		return s;

	}

	ArrayList<String> getBetweenQuotes(String string) {
		ArrayList<String> inBetweenQuotes = new ArrayList<String>();
		for (int i = 0; i < string.length(); i++) {
			if (string.substring(i, i + 1).equals("\"")) {
				for (int j = i + 1; j < string.length(); j++) {
					if (string.substring(j, j + 1).equals("\"")) {
						inBetweenQuotes.add(string.substring(i + 1, j));
						break;
					}
				}
			}
		}
		return inBetweenQuotes;
	}

	public ArrayList<ArrayList<Student>> sortStudentsIntoLevels(ArrayList<Student> students) {
		ArrayList<Student> level0 = new ArrayList<Student>();
		ArrayList<Student> level1 = new ArrayList<Student>();
		ArrayList<Student> level2 = new ArrayList<Student>();
		ArrayList<Student> level3 = new ArrayList<Student>();
		ArrayList<Student> level4 = new ArrayList<Student>();
		ArrayList<Student> level5 = new ArrayList<Student>();
		ArrayList<Student> level6 = new ArrayList<Student>();
		ArrayList<Student> level7 = new ArrayList<Student>();
		ArrayList<Student> level8 = new ArrayList<Student>();
		for (Student student : students) {
			if (student.Level == 0) {
				level0.add(student);
			}
			if (student.Level == 1) {
				level1.add(student);
			}
			if (student.Level == 2) {
				level2.add(student);
			}
			if (student.Level == 3) {
				level3.add(student);
			}
			if (student.Level == 4) {
				level4.add(student);
			}
			if (student.Level == 5) {
				level5.add(student);
			}
			if (student.Level == 6) {
				level6.add(student);
			}
			if (student.Level == 7) {
				level7.add(student);
			}
			if (student.Level == 8) {
				level8.add(student);
			}
		}
		ArrayList<ArrayList<Student>> sortedStudentsInLevels = new ArrayList<ArrayList<Student>>();
		sortedStudentsInLevels.add(level0);
		sortedStudentsInLevels.add(level1);
		sortedStudentsInLevels.add(level2);
		sortedStudentsInLevels.add(level3);
		sortedStudentsInLevels.add(level4);
		sortedStudentsInLevels.add(level5);
		sortedStudentsInLevels.add(level6);
		sortedStudentsInLevels.add(level7);
		sortedStudentsInLevels.add(level8);

		return sortedStudentsInLevels;
	}

	/*
	 * public void setUpStudentLabel(JLabel label, Student student) {
	 * System.out.println("used student label");
	 * System.out.println("Set up student label"); label.setBounds(0,y,500,50);
	 * label.setVisible(true); label.setText(student.Name + "    "+ student.Level
	 * +"     "+ student.Location);
	 * 
	 * }
	 */

}
