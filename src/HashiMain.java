import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class HashiMain extends JFrame {
	private JComboBox combo;
	private JSpinner widthField;
	private JSpinner heightField;
	private JSpinner size;
	private JButton resetSizeButton;
	private JButton confirmSizeButton;
	public int islandNb = 2;
	public int width = 3;
	public int height = 3;
    final private String[] Kingdom= {"2X2","3X3","4X4","5X5","6X6","7X7","8X8","9X9"};
	public HashiMain() {
		createView();
		placeComponents();
		createController();
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	private void createController() {
		confirmSizeButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				combo.addActionListener(this);
				if(e.getSource() == combo) {
					System.out.println(combo.getSelectedIndex());
				}
				width = combo.getSelectedIndex() + 2;
				height = width;
				islandNb = (int) size.getValue();
				System.out.println(width);
				if ((width * height) < islandNb) {
					JOptionPane.showMessageDialog(
	           			    null, 
	           			    "Number of islands must be less than or equal to the Kingdom Area !",
	            		    "Error !",
	            		    JOptionPane.ERROR_MESSAGE
	            		);
				} else {
					HashiGame hashi = new HashiGame(islandNb, width, height);
					hashi.display();
					dispose();
				}
			}
		});
		
		resetSizeButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				widthField.setValue(2);
				heightField.setValue(2);
				size.setValue(2);
			}
		});
	
		
	}

	private void placeComponents() {
		JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			JPanel r = new JPanel(new GridLayout(3, 2)); {
					r.add(new JLabel("Kingdom Area : "));
					r.add(combo);
					r.add(new JLabel("Nomber of Ilands : "));
					r.add(size);
				
				JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(1, 0)); {
						t.add(confirmSizeButton);
					}
					s.add(t);
				}
				r.add(s);		
				 s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(1, 0)); {
						t.add(resetSizeButton);
					}
					s.add(t);
				}
				
				r.add(s);
			}
			q.add(r);
			q.setBorder(BorderFactory.createEtchedBorder());
		}	
		this.add(q, BorderLayout.CENTER);
		
	}

	private void createView() {
		//
		combo = new JComboBox(Kingdom);
		//
		int maxIsland = (Toolkit.getDefaultToolkit().getScreenSize().height/ IslandsSpace.ISLAND_SPACE_SQUARE_EDGE_SIZE);
		
		size = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland * maxIsland, 1));
		widthField = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland, 1));
		heightField = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland, 1));
		
		confirmSizeButton = new JButton("Confirm");
		resetSizeButton = new JButton("Reset");
	}
	
	public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HashiMain();
            }
        });
    }


}
