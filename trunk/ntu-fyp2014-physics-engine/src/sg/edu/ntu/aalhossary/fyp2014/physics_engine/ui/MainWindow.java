package sg.edu.ntu.aalhossary.fyp2014.physics_engine.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sg.edu.ntu.aalhossary.fyp2014.common.Model;
import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.ui.JmolDisplay;
import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.core.UpdateRegistry;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.World;

import java.awt.Component;

import javax.swing.border.EtchedBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;

/**
 * @author waiyan
 * GUI for Physics Engine
 */

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JPanel coeOfResPanel;
	private JSlider coeOfResSlider;
	private JLabel coeOfResLbl;
	
	private JPanel simLvlPanel;
	private JRadioButton atomicRadioButton;
	private JRadioButton molecularRadioButton;
	private JTextField commandTextField;
	
	private UpdateRegistry mediator;
	private List<Model> modelList;
	private JLabel lblSimulationMedium;
	private JPanel panel;
	private JButton pauseButton;
	private JButton restartButton;
	
	/**
	 * Create the main frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// add JMol Panel
		JmolDisplay jmolPanel = new JmolDisplay();
		contentPane.add(jmolPanel);
		
		// add mediator
		mediator = new UpdateRegistry(jmolPanel.getViewer(), modelList);
        jmolPanel.setMediator(mediator);
		
		// add RHS Panel for user input
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		contentPane.add(inputPanel, BorderLayout.EAST);
		
		createSimLvlPanel();
		inputPanel.add(simLvlPanel);
		
		// add slider and label for coefficient of restitution
		createCoeOfResPanel();
		inputPanel.add(coeOfResPanel);
		
		panel = new JPanel();
		inputPanel.add(panel);
		
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pauseButton.getText().equals("Pause")){
					pauseButton.setText("Resume");
					World.simulationStatus = "paused";
			}
				else{
					pauseButton.setText("Pause");
					World.simulationStatus = "running";
					World.countDownLatch.countDown();
				}
			}
		});
		panel.add(pauseButton);
		
		restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(World.simulationStatus.equals("paused")){
					World.countDownLatch.countDown();
					pauseButton.setText("Pause");
				}
				World.simulationStatus = "restart";
			}
		});
		panel.add(restartButton);
		
		// add text field for user input
		commandTextField = new JTextField();
		contentPane.add(commandTextField, BorderLayout.SOUTH);
		commandTextField.requestFocusInWindow();
		
		// add action listeners
		addActionListeners();
		this.setVisible(true);
	}
	
	/**
	 * Return display interface from JMol
	 * @return mediator
	 */
	public UpdateRegistry getMediator(){
		return mediator;
	}
	
	/**
	 * Create the panel for setting the coefficient of resolution
	 */
	private void createCoeOfResPanel (){
		
		coeOfResPanel = new JPanel();
		coeOfResPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		coeOfResPanel.setLayout(new BoxLayout(coeOfResPanel, BoxLayout.Y_AXIS));	
		
		JPanel panel1 = new JPanel();
	//	panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		JLabel label1 = new JLabel("Coefficient of Restitution: ");
		panel1.add(label1);
		
		coeOfResLbl = new JLabel(String.valueOf(World.COEFFICENT_OF_RESTITUTION));
		coeOfResLbl.setAlignmentY(Component.TOP_ALIGNMENT);
		panel1.add(coeOfResLbl);
		
		lblSimulationMedium = new JLabel("Simulation Medium");
		lblSimulationMedium.setAlignmentX(Component.CENTER_ALIGNMENT);
		coeOfResPanel.add(lblSimulationMedium);
		
		coeOfResSlider = new JSlider(0,100);
		coeOfResSlider.setSnapToTicks(true);
		coeOfResSlider.setPaintTicks(true);
		coeOfResSlider.setPaintLabels(true);
		coeOfResPanel.add(coeOfResSlider);
		coeOfResSlider.setValue((int)(World.COEFFICENT_OF_RESTITUTION*100));
		
		coeOfResPanel.add(panel1);
	}
	
	/**
	 * Create the panel for setting the simulation level (atomic or molecular)
	 */
	private void createSimLvlPanel (){
		
		simLvlPanel = new JPanel();
		simLvlPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		simLvlPanel.setLayout(new BoxLayout(simLvlPanel, BoxLayout.Y_AXIS));
		
		JLabel label1 = new JLabel("Simulation Level");
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		simLvlPanel.add(label1);
		
		ButtonGroup bg = new ButtonGroup();
		
		atomicRadioButton = new JRadioButton("atomic");
	    molecularRadioButton = new JRadioButton("molecular");
	    bg.add(atomicRadioButton);
	    bg.add(molecularRadioButton);
	    
	    if(World.simulationLvlAtomic == true)
	    	atomicRadioButton.setSelected(true);
	    else
	    	molecularRadioButton.setSelected(true);
	    
	    JPanel panel2 = new JPanel();
	    panel2.add(atomicRadioButton);
	    panel2.add(molecularRadioButton);
		simLvlPanel.add(panel2);	
	}

	/**
	 * Add ActionListeners for UI inputs
	 */
	private void addActionListeners() {
		DecimalFormat formatter = new DecimalFormat("0.00");
		
		coeOfResSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
                double currentVal = ((JSlider)event.getSource()).getValue()/100.0;
                coeOfResLbl.setText(formatter.format(currentVal));
                World.COEFFICENT_OF_RESTITUTION = currentVal;
            }
        });
		
		commandTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() != 10)	// enter key
            		return;
            	
            	JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                commandTextField.setText(text.toUpperCase()); 
                try {
					World.setCommand(textField.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
		});
		
		atomicRadioButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	JRadioButton button = (JRadioButton) e.getSource();
		        if (button.getText().equals("atomic")){
		        	World.simulationLvlAtomic = true;
		        }
		    }
		});
		
		molecularRadioButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	JRadioButton button = (JRadioButton) e.getSource();
		        if (button.getText().equals("molecular")){
		        	World.simulationLvlAtomic = false;
		        }
		    }
		});
	}
}
