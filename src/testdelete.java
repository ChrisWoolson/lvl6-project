import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class testdelete {
public static void main(String[] args) {
	testdelete td = new testdelete();
	td.setup();
			
}
	void setup() {
		JFrame f = new JFrame();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JLabel l = new JLabel();
		
		f.add(p1);
		f.add(p2);
		f.setSize(300,300);
		f.setVisible(true);
		
		p1.setVisible(true);
		p2.setVisible(true);
		
		p1.setLayout(null);
		p2.setLayout(null);
		
		p1.setBounds(0,0,300,200);
		p2.setBounds(0,200,300,100);
		
		p2.add(l);
		l.setBounds(0,200,100,100);
		l.setText("test");
		l.setVisible(true);
		
	}
	
}
