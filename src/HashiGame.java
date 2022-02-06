import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.StdHashiModel;
import utilities.StdCoord;

public class HashiGame {

	private JFrame mainFrame;
	private IslandsSpace spaceGame;

	private JButton[] nodeDegreeTab;
	private JButton solveButton;
	private JButton resetButton;
	private JButton activateDegreeButton;
	private int nb,wdt,hgt;
	private StdHashiModel model;
	private JLabel nodeNbLabel;
	private int placeNodeNb;
	private boolean finish = false;
	public HashiGame(int nb, int wdt, int hgt) {
		this.nb = nb;
		this.wdt = wdt;
		this.hgt = hgt;
		placeNodeNb = 0;
		createModel();
		createView();
		placeComponents();
		createController();
	}

	private void createModel() {
		this.model = new StdHashiModel(nb,wdt,hgt);
	}

	private void createView() {
		mainFrame = new JFrame("Hashiwokakero");
		mainFrame.setResizable(false);

		nodeDegreeTab = new JButton[8];
		spaceGame = new IslandsSpace(this.model);

		for (int i = 0; i < 8; i++) {
			nodeDegreeTab[i] = new JButton(""+(i+1));
			//degreeButonTab[i].setBorder(new RoundBtn(15));
		}

		solveButton = new JButton("Solve !");
		resetButton = new JButton("reset");

		nodeNbLabel = new JLabel("Remaining Islands to be Placed : " + nb);
	}
	

	private void placeComponents() {
		
		JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			JPanel r = new JPanel(new GridLayout(0, 1)); {
				JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER));{
					s.add(nodeNbLabel);
				}
				r.add(s);

				s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(1, 8)); {

						for (int i = 0; i < 8; i++) {
							t.add(nodeDegreeTab[i]);
						}							
					}
					s.add(t);
				}
				r.add(s);

				s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					s.add(solveButton);
					s.add(resetButton);
				}
				r.add(s);
			}
			q.add(r);
			q.setBorder(BorderFactory.createEtchedBorder());
		}

		mainFrame.add(q, BorderLayout.NORTH);

		q = new JPanel(); {
			q.add(spaceGame, BorderLayout.CENTER);

		}
		q.setBackground(Color.yellow);
		mainFrame.add(q, BorderLayout.CENTER);
	}

	@SuppressWarnings({ "deprecation" })
	private void createController() {

		((Observable)model).addObserver( new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				refresh();
			}
		});

		activateDegreeButton = nodeDegreeTab[0];
		activateDegreeButton.setEnabled(false);

		spaceGame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {


				int row = e.getY()/ IslandsSpace.ISLAND_SPACE_SQUARE_EDGE_SIZE;
				int column = e.getX() / IslandsSpace.ISLAND_SPACE_SQUARE_EDGE_SIZE;
				int degree = Integer.parseInt(activateDegreeButton.getText());

				StdCoord position = new StdCoord(row, column);

				if (model.hasNodeAtCoord(position)) {

					model.removeNodeAtCoord(position);

					placeNodeNb--;
					nodeNbLabel.setText("Remaining Islands to be Placed : " + (nb - placeNodeNb));
				} 
				else if (!model.isCompleteLinkedGraphe()) {
					model.placeNodeAtCoord(position, degree);

					placeNodeNb++;
					nodeNbLabel.setText("Remaining Islands to be Placed : " + (nb - placeNodeNb));
				}

				//System.out.println(position);

			}

		});


		for (JButton button : nodeDegreeTab) {
			button.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					activateDegreeButton = button;
					button.setEnabled(false);

					for (JButton jButton : nodeDegreeTab) {
						if (button != jButton) {
							jButton.setEnabled(true);
						}
					}
				}
			});
		}

		solveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				solveButton.setEnabled(false);

				for (JButton degreeButton : nodeDegreeTab) {
					degreeButton.setEnabled(false);
				}

				model.orderNodes();

				model.findNeighborsNode();

				model.findPossibleBridges();


				if (model.solve()) {
					solveButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Grid has been solved", "Solution found !", JOptionPane.INFORMATION_MESSAGE);

				} else {
					solveButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "No solution found",
							"Error !!!", JOptionPane.INFORMATION_MESSAGE);
				}

				finish = true;

			}
		});

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashiMain frame = new HashiMain();
				frame.setVisible(true);
				mainFrame.dispose();

			}
		});

	}

	public void display() {
		refresh();
		mainFrame.setSize(200, 500);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private void refresh() {
		spaceGame.repaint();
		changeButtonsSates(model.isCompleteLinkedGraphe());
	}


	private void changeButtonsSates(boolean isComplete) {

		for (JButton degreeButton : nodeDegreeTab) {
			if (degreeButton != activateDegreeButton) degreeButton.setEnabled(! isComplete);
		}
		solveButton.setEnabled(isComplete && !finish);

	}
}
