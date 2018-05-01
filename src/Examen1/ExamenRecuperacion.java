package Examen1;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;





import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JRadioButton;

import Conexion.ConexionBBDDExamen1;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class ExamenRecuperacion {
	

	private JFrame frmExamenDeRecuperacion;
	private int contador = 0;
	private JLabel lblPreg;
	static ConexionBBDDExamen1 miConexion;

	private JLabel LblrespA;
	private JLabel LblrespB;
	private JLabel LblrespC;
	private JLabel LblrespD;
	private JLabel lblMal;
	private JLabel lblBien;
	private JLabel lblCorPreg;
	private JLabel lblCorResp;
	private JLabel lblRespuestaCorrecta;
	private JRadioButton rBa;
	private JRadioButton rBb;
	private JRadioButton rBc;
	private JRadioButton rBd;
	private String letra = "";
	private ButtonGroup bG = new ButtonGroup();
	private JButton btnOk;
	private int PuntuacionBien = 0;
	
	private JLabel labelBien;
	private JLabel labelMal;
	private ArrayList list = new ArrayList<>();
	private JLabel lblNewLabelicono;
	private JLabel lblIcono;
	private JLabel lblMuyBien;
	Image img=null;

	public ExamenRecuperacion() {
		miConexion = new ConexionBBDDExamen1();
		
		initialize();
	}

	private void initialize() {
		
		list.addAll(miConexion.idPregunta);
		frmExamenDeRecuperacion = new JFrame();
		frmExamenDeRecuperacion.setTitle("Examen de Recuperacion");
		frmExamenDeRecuperacion.getContentPane().setBackground(Color.GRAY);
		frmExamenDeRecuperacion.setBounds(100, 100, 450, 300);
		frmExamenDeRecuperacion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmExamenDeRecuperacion.getContentPane().setLayout(null);
		frmExamenDeRecuperacion.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frmExamenDeRecuperacion.setBounds(0, 0, screen.width, screen.height - 30);
		frmExamenDeRecuperacion.setExtendedState(Frame.MAXIMIZED_BOTH);

		JPanel PanRespCorrecta = new JPanel();
		PanRespCorrecta.setBounds(10, 688, 1564, 130);
		frmExamenDeRecuperacion.getContentPane().add(PanRespCorrecta);
		PanRespCorrecta.setBackground(Color.LIGHT_GRAY);
		PanRespCorrecta.setLayout(null);

		lblCorPreg = new JLabel("");
		lblCorPreg.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorPreg.setBounds(10, 58, 1550, 20);
		PanRespCorrecta.add(lblCorPreg);

		lblCorResp = new JLabel("");
		lblCorResp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorResp.setBounds(10, 99, 1550, 20);
		PanRespCorrecta.add(lblCorResp);

		// boton Ok
		btnOk = new JButton("OK");
		btnOk.setVisible(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				if (contador < list.size() - 1) {
					contador++;
					bG.clearSelection();
					lblBien.setText(" ");
					lblMal.setText(" ");
					lblCorPreg.setText("");
					lblCorResp.setText("");
					lblPreg.setText(ConexionBBDDExamen1.siguientePregunta((int) list.get(contador)));
					LblrespA.setText(ConexionBBDDExamen1.sigRespA((int) list.get(contador)));
					LblrespB.setText(ConexionBBDDExamen1.sigRespB((int) list.get(contador)));
					LblrespC.setText(ConexionBBDDExamen1.sigRespC((int) list.get(contador)));
					LblrespD.setText(ConexionBBDDExamen1.sigRespD((int) list.get(contador)));
					rBa.setEnabled(true);
					rBb.setEnabled(true);
					rBc.setEnabled(true);
					rBd.setEnabled(true);

				} else {
					rBa.setVisible(false);
					rBb.setVisible(false);
					rBc.setVisible(false);
					rBd.setVisible(false);
					lblPreg.setText("");
					LblrespA.setText("");
					LblrespB.setText("");
					LblrespC.setText("");
					LblrespD.setText("");
					if (Examen1.PuntuacionMal != 0) {
						img = new ImageIcon(this.getClass().getResource("/triste.png")).getImage();
						lblIcono.setIcon(new ImageIcon(img));
						 lblMuyBien.setText("Muy mal el objetivo era errores a 0 ");
					}
				}
			}
		});
		btnOk.setBounds(46, 11, 89, 23);
		PanRespCorrecta.add(btnOk);

		LblrespA = new JLabel(ConexionBBDDExamen1.sigRespA(contador));
		LblrespA.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespA.setBounds(45, 40, 1549, 20);
		frmExamenDeRecuperacion.getContentPane().add(LblrespA);
		LblrespA.setText(ConexionBBDDExamen1.sigRespA((int) list.get(0)));

		LblrespB = new JLabel(ConexionBBDDExamen1.sigRespB(contador));
		LblrespB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespB.setBounds(45, 70, 1549, 20);
		frmExamenDeRecuperacion.getContentPane().add(LblrespB);
		LblrespB.setText(ConexionBBDDExamen1.sigRespB((int) list.get(0)));

		LblrespC = new JLabel(ConexionBBDDExamen1.sigRespC(contador));
		LblrespC.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespC.setBounds(45, 100, 1549, 20);
		frmExamenDeRecuperacion.getContentPane().add(LblrespC);
		LblrespC.setText(ConexionBBDDExamen1.sigRespC((int) list.get(0)));

		LblrespD = new JLabel(ConexionBBDDExamen1.sigRespD(contador));
		LblrespD.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespD.setBounds(45, 130, 1549, 20);
		frmExamenDeRecuperacion.getContentPane().add(LblrespD);
		LblrespD.setText(ConexionBBDDExamen1.sigRespD((int) list.get(0)));

		JPanel PanRespBuena = new JPanel();
		PanRespBuena.setBackground(Color.GRAY);
		PanRespBuena.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		PanRespBuena.setBounds(10, 553, 68, 52);
		frmExamenDeRecuperacion.getContentPane().add(PanRespBuena);

		lblBien = new JLabel("");
		lblBien.setForeground(Color.ORANGE);
		PanRespBuena.add(lblBien);

		JPanel PanRespError = new JPanel();
		PanRespError.setBackground(Color.GRAY);
		PanRespError.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		PanRespError.setBounds(130, 553, 68, 52);
		frmExamenDeRecuperacion.getContentPane().add(PanRespError);

		lblMal = new JLabel("");
		lblMal.setForeground(Color.RED);
		PanRespError.add(lblMal);

		lblPreg = new JLabel();
		lblPreg.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPreg.setBounds(10, 11, 1584, 20);
		frmExamenDeRecuperacion.getContentPane().add(lblPreg);
		lblPreg.setText(ConexionBBDDExamen1.siguientePregunta((int) list.get(0)));

		lblRespuestaCorrecta = new JLabel("RESPUESTA CORRECTA");
		lblRespuestaCorrecta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRespuestaCorrecta.setForeground(Color.RED);
		lblRespuestaCorrecta.setBounds(630, 654, 194, 23);
		frmExamenDeRecuperacion.getContentPane().add(lblRespuestaCorrecta);
		lblRespuestaCorrecta.setVisible(false);

		rBa = new JRadioButton("");
		rBa.setBackground(Color.GRAY);
		bG.add(rBa);
		rBa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "A";
				funcionRadioButton();
			}
		});
		rBa.setBounds(12, 37, 23, 23);
		frmExamenDeRecuperacion.getContentPane().add(rBa);

		rBb = new JRadioButton("");
		rBb.setBackground(Color.GRAY);
		bG.add(rBb);
		rBb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "B";
				funcionRadioButton();
			}
		});
		rBb.setBounds(12, 67, 23, 23);
		frmExamenDeRecuperacion.getContentPane().add(rBb);

		rBc = new JRadioButton("");
		rBc.setBackground(Color.GRAY);
		bG.add(rBc);
		rBc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "C";
				funcionRadioButton();
			}
		});
		rBc.setBounds(12, 97, 23, 23);
		frmExamenDeRecuperacion.getContentPane().add(rBc);

		rBd = new JRadioButton("");
		rBd.setBackground(Color.GRAY);
		bG.add(rBd);
		
		rBd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "D";
				funcionRadioButton();
			}
		});
		rBd.setBounds(12, 127, 23, 23);
		frmExamenDeRecuperacion.getContentPane().add(rBd);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(1152, 502, 422, 178);
		frmExamenDeRecuperacion.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabelBien = new JLabel("Puntuacion Bien");
		lblNewLabelBien.setForeground(Color.ORANGE);
		lblNewLabelBien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabelBien.setBounds(48, 17, 143, 20);
		panel.add(lblNewLabelBien);

		JLabel lblNewLabelMal = new JLabel("Puntuacion Mal");
		lblNewLabelMal.setForeground(Color.RED);
		lblNewLabelMal.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabelMal.setBounds(279, 17, 143, 20);
		panel.add(lblNewLabelMal);

		labelMal = new JLabel(String.valueOf(Examen1.PuntuacionMal));
		labelMal.setForeground(Color.RED);
		labelMal.setFont(new Font("Tahoma", Font.BOLD, 90));
		labelMal.setBounds(319, 48, 62, 88);
		panel.add(labelMal);

		labelBien = new JLabel("0");
		labelBien.setForeground(Color.GREEN);
		labelBien.setFont(new Font("Tahoma", Font.BOLD, 90));
		labelBien.setBounds(82, 47, 62, 88);
		panel.add(labelBien);
		
		lblIcono = new JLabel("");
		
		
		lblIcono.setBounds(540, 201, 441, 404);
		frmExamenDeRecuperacion.getContentPane().add(lblIcono);
		
		lblMuyBien = new JLabel("");
		lblMuyBien.setForeground(Color.BLUE);
		lblMuyBien.setFont(new Font("Snap ITC", Font.BOLD, 25));
		lblMuyBien.setBounds(480, 144, 756, 46);
		frmExamenDeRecuperacion.getContentPane().add(lblMuyBien);
		
		

	}

	private void funcionRadioButton() {
		if (ConexionBBDDExamen1.comprobarResp((int) list.get(contador)).equals(letra)) {// if 1 comporbar respuesta
			Examen1.PuntuacionMal--;
			PuntuacionBien++;
			labelBien.setText(String.valueOf(PuntuacionBien));
			labelMal.setText(String.valueOf(Examen1.PuntuacionMal));
			System.out.println("Dentro del primer if  respuesta es correcta");

			if (contador < list.size() - 1) {// if 2
				contador++;
				lblBien.setText("Muy bien");
				lblMal.setText(" ");
				lblPreg.setText(ConexionBBDDExamen1.siguientePregunta((int) list.get(contador)));
				LblrespA.setText(ConexionBBDDExamen1.sigRespA((int) list.get(contador)));
				LblrespB.setText(ConexionBBDDExamen1.sigRespB((int) list.get(contador)));
				LblrespC.setText(ConexionBBDDExamen1.sigRespC((int) list.get(contador)));
				LblrespD.setText(ConexionBBDDExamen1.sigRespD((int) list.get(contador)));
				System.out.println("Dentro del segundo if contador menos que tamaño list");
			} // final if 2 evita que contador se pase de tamaño list

			else {// else 2
				rBa.setVisible(false);
				rBb.setVisible(false);
				rBc.setVisible(false);
				rBd.setVisible(false);
				lblPreg.setText("");
				LblrespA.setText("");
				LblrespB.setText("");
				LblrespC.setText("");
				LblrespD.setText("");
				if (Examen1.PuntuacionMal==0) {
					 img = new ImageIcon(this.getClass().getResource("/Medalla.png")).getImage();
					lblIcono.setIcon(new ImageIcon(img));
					 lblMuyBien.setText("Muy bien Errores 0");
				}else{
					img = new ImageIcon(this.getClass().getResource("/triste.png")).getImage();
					lblIcono.setIcon(new ImageIcon(img));
					 lblMuyBien.setText("Muy mal");
				}
			} // fin else 2
		} // final if 1 comprueba respuesta

		else {// else 1
			lblMal.setText("Muy Mal");
			lblBien.setText(" ");
			lblRespuestaCorrecta.setVisible(true);
			lblCorPreg.setText(ConexionBBDDExamen1.siguientePregunta((int) list.get(contador)));
			lblCorResp.setText(miConexion.RespCor((int) list.get(contador)));
			btnOk.setVisible(true);
			labelMal.setText(String.valueOf(Examen1.PuntuacionMal));
			System.out.println("Dentro del segundo else respuesta no es correcta");
		} // final else 1

		if (contador > ConexionBBDDExamen1.registros) {
			contador = 1;
		}
		
	

	}
}
