package PanelPrincipal;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Conexion.ConexionBBDDExamenUsuario;
import CrearExamenUsuario.CrearExamenUsuario;
import Examen1.Examen1;
import ExamenUsuario.ExamenUsuario;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class PanelPrincipal<PruebaConexion> {

	private JFrame frmPanelPrincipal;
	private JLabel labelIcono;
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelPrincipal window = new PanelPrincipal();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//CONSTRUCTOR
	public PanelPrincipal() {
		initialize();
	}
 
	
	private void initialize() {
		
		//Creacion Frame
		frmPanelPrincipal =new JFrame();
		frmPanelPrincipal.setVisible(true);
		frmPanelPrincipal.setTitle("Panel principal");
		frmPanelPrincipal.setResizable(false);
		frmPanelPrincipal.setBounds(100, 100, 450, 300);
		frmPanelPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creacion Frame
		JPanel panel = new JPanel();
		frmPanelPrincipal.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		JLabel label = new JLabel(new ImageIcon("Medalla.png"));
		label.setBounds(0, 0, 0, 0);
		panel.add(label);
		
		labelIcono = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/estudiante.jpg")).getImage();
		labelIcono.setIcon(new ImageIcon(img));
		labelIcono.setBounds(88, 0, 266, 247);
		panel.add(labelIcono);
		
		//Creacion menu
		JMenuBar menuBar = new JMenuBar();
		frmPanelPrincipal.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu MnExamenes = new JMenu("Examenes");
		MnExamenes.setPreferredSize(new Dimension(200, MnExamenes.getPreferredSize().height));
		menuBar.add(MnExamenes);
		
		JMenu mnMenu_1 = new JMenu("Empleo Publico 2006");
		
		MnExamenes.add(mnMenu_1);
		
		JMenuItem SubM2Examen = new JMenuItem("Ir al Examen");
		SubM2Examen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 new Examen1();
				
			}
		});
		mnMenu_1.add(SubM2Examen);
		
		JMenu mnExamenUsuario = new JMenu("ExamenUsuario");
		MnExamenes.add(mnExamenUsuario);
		
		JMenuItem mntmExamenUsuario = new JMenuItem("Ir al Examen");
		mntmExamenUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ExamenUsuario();
				
			}
		});
		mnExamenUsuario.add(mntmExamenUsuario);
		
		JMenu mnConecion = new JMenu("Conexion");
		mnConecion.setPreferredSize(new Dimension(200, mnConecion.getPreferredSize().height));
		menuBar.add(mnConecion);
		
		JMenuItem mntmDatosConexion = new JMenuItem("Datos conexion");
		mntmDatosConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CrearExamenUsuario pruebacon = new CrearExamenUsuario();
			}
		});
		mnConecion.add(mntmDatosConexion);
		
		
		
		
	}//final metdod initialize
}
