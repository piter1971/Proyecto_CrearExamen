package ExamenUsuario;

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
import javax.swing.JTextField;
import Conexion.*;
import Conexion.ConexionBBDDExamen1;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

public class ExamenUsuario {

	private JFrame frmExamen;
	private int contador=1;
	private JLabel lblPreg;
	static ConexionBBDDExamenUsuario miConexion;
	private JTextField txtIrAPreg;
	private JLabel LblrespA;
	private JLabel LblrespB;
	private JLabel LblrespC;
	private JLabel LblrespD;
	private JLabel lblMal;
	private JLabel lblBien;
	private JLabel lblCorPreg;
	private JLabel lblCorResp;
	private JButton btnIrAPreg;
	private JLabel lblRespuestaCorrecta;
	private JRadioButton rBa;
	private JRadioButton rBb;
	private JRadioButton rBc;
	private JRadioButton rBd;
	private String letra="";
	private ButtonGroup bG = new ButtonGroup();
	private JButton btnOk;
	private int PuntuacionBien = 0;
	static int PuntuacionMal = 0;
	private JLabel labelBien;
	private JLabel labelMal; 
	private JButton BtnRepetir;
	Iterator it = miConexion.idPregunta.iterator();
	private JLabel lblErrorNumero;
	
	
	public ExamenUsuario() {
		miConexion = new ConexionBBDDExamenUsuario();
		
		initialize();
	}

	private void initialize() {
		PuntuacionMal=0;
		frmExamen = new JFrame();
		frmExamen.setTitle("Examen Usuario");
		frmExamen.getContentPane().setBackground(SystemColor.textHighlight);
		frmExamen.setBounds(100, 100, 450, 300);
		frmExamen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmExamen.getContentPane().setLayout(null);
		frmExamen.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frmExamen.setBounds(0, 0, screen.width, screen.height - 30);
		frmExamen.setExtendedState(Frame.MAXIMIZED_BOTH);

		// BOTON IR A PREGUNTA
		btnIrAPreg = new JButton("Ir a Pregunta");
		btnIrAPreg.setEnabled(false);
		btnIrAPreg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorNumero.setText(" ");
				if (txtIrAPreg.getText().matches("[0-9]")) {
					
				
				contador = Integer.parseInt(txtIrAPreg.getText());
				lblPreg.setText(ConexionBBDDExamenUsuario.siguientePregunta(contador));
				LblrespA.setText(ConexionBBDDExamenUsuario.sigRespA(contador));
				LblrespB.setText(ConexionBBDDExamenUsuario.sigRespB(contador));
				LblrespC.setText(ConexionBBDDExamenUsuario.sigRespC(contador));
				LblrespD.setText(ConexionBBDDExamenUsuario.sigRespD(contador));
				btnIrAPreg.setEnabled(false);
				}else{
					lblErrorNumero.setText("Debe ser un numero");
				}
					
			}
		});
		btnIrAPreg.setBounds(10, 616, 295, 23);
		frmExamen.getContentPane().add(btnIrAPreg);

		JPanel PanRespCorrecta = new JPanel();
		PanRespCorrecta.setBounds(10, 688, 1564, 130);
		frmExamen.getContentPane().add(PanRespCorrecta);
		PanRespCorrecta.setBackground(Color.LIGHT_GRAY);
		PanRespCorrecta.setLayout(null);

		lblCorPreg = new JLabel("");
		lblCorPreg.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorPreg.setBounds(10, 58, 1544, 20);
		PanRespCorrecta.add(lblCorPreg);

		lblCorResp = new JLabel("");
		lblCorResp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCorResp.setBounds(10, 99, 1544, 20);
		PanRespCorrecta.add(lblCorResp);

		// boton Ok
		btnOk = new JButton("OK");
		btnOk.setVisible(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				contador++;
				bG.clearSelection();
				lblBien.setText(" ");
				lblMal.setText(" ");
				lblCorPreg.setText("");
				lblCorResp.setText("");
				lblPreg.setText(ConexionBBDDExamenUsuario.siguientePregunta(contador));
				LblrespA.setText(ConexionBBDDExamenUsuario.sigRespA(contador));
				LblrespB.setText(ConexionBBDDExamenUsuario.sigRespB(contador));
				LblrespC.setText(ConexionBBDDExamenUsuario.sigRespC(contador));
				LblrespD.setText(ConexionBBDDExamenUsuario.sigRespD(contador));
				rBa.setEnabled(true);
				rBb.setEnabled(true);
				rBc.setEnabled(true);
				rBd.setEnabled(true);

				if (PuntuacionMal > 0) {
					BtnRepetir.setVisible(true);
				}
			}
		});
		btnOk.setBounds(46, 11, 89, 23);
		PanRespCorrecta.add(btnOk);

		LblrespA = new JLabel(ConexionBBDDExamenUsuario.sigRespA(contador));
		LblrespA.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespA.setBounds(45, 67, 1549, 20);
		frmExamen.getContentPane().add(LblrespA);
		LblrespA.setText(ConexionBBDDExamenUsuario.sigRespA(contador));

		LblrespB = new JLabel(ConexionBBDDExamenUsuario.sigRespB(contador));
		LblrespB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespB.setBounds(45, 97, 1549, 20);
		frmExamen.getContentPane().add(LblrespB);
		LblrespB.setText(ConexionBBDDExamenUsuario.sigRespB(contador));

		LblrespC = new JLabel(ConexionBBDDExamenUsuario.sigRespC(contador));
		LblrespC.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespC.setBounds(45, 127, 1549, 20);
		frmExamen.getContentPane().add(LblrespC);
		LblrespC.setText(ConexionBBDDExamenUsuario.sigRespC(contador));

		LblrespD = new JLabel(ConexionBBDDExamenUsuario.sigRespD(contador));
		LblrespD.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LblrespD.setBounds(45, 157, 1549, 20);
		frmExamen.getContentPane().add(LblrespD);
		LblrespD.setText(ConexionBBDDExamenUsuario.sigRespD(contador));

		JPanel PanRespBuena = new JPanel();
		PanRespBuena.setBackground(SystemColor.textHighlight);
		PanRespBuena.setBorder(new LineBorder(new Color(0, 0, 0)));
		PanRespBuena.setBounds(10, 540, 68, 65);
		frmExamen.getContentPane().add(PanRespBuena);

		lblBien = new JLabel("");
		lblBien.setForeground(Color.BLACK);
		lblBien.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanRespBuena.add(lblBien);

		JPanel PanRespError = new JPanel();
		PanRespError.setBackground(SystemColor.textHighlight);
		PanRespError.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		PanRespError.setBounds(237, 540, 68, 65);
		frmExamen.getContentPane().add(PanRespError);

		lblMal = new JLabel("");
		lblMal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMal.setForeground(Color.RED);
		PanRespError.add(lblMal);

		txtIrAPreg = new JTextField();
		txtIrAPreg.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				btnIrAPreg.setEnabled(true);
			}

		});
		txtIrAPreg.setBounds(128, 585, 40, 20);
		frmExamen.getContentPane().add(txtIrAPreg);
		txtIrAPreg.setColumns(10);

		lblRespuestaCorrecta = new JLabel("RESPUESTA CORRECTA");
		lblRespuestaCorrecta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRespuestaCorrecta.setForeground(Color.RED);
		lblRespuestaCorrecta.setBounds(630, 654, 194, 23);
		frmExamen.getContentPane().add(lblRespuestaCorrecta);
		lblRespuestaCorrecta.setVisible(false);

		rBa = new JRadioButton("");
		rBa.setBackground(SystemColor.textHighlight);
		bG.add(rBa);
		rBa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "A";
				funccionRadioButon();
			}
		});
		rBa.setBounds(10, 64, 23, 23);
		frmExamen.getContentPane().add(rBa);

		rBb = new JRadioButton("");
		rBb.setBackground(SystemColor.textHighlight);
		bG.add(rBb);
		rBb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "B";
				funccionRadioButon();
			}
		});
		rBb.setBounds(10, 94, 23, 23);
		frmExamen.getContentPane().add(rBb);

		rBc = new JRadioButton("");
		rBc.setBackground(SystemColor.textHighlight);
		bG.add(rBc);
		rBc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "C";

				funccionRadioButon();

			}
		});
		rBc.setBounds(10, 127, 23, 23);
		frmExamen.getContentPane().add(rBc);

		rBd = new JRadioButton("");
		rBd.setBackground(SystemColor.textHighlight);
		bG.add(rBd);
		rBd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOk.setVisible(false);
				lblRespuestaCorrecta.setVisible(false);
				lblCorPreg.setText("");
				lblCorResp.setText("");
				letra = "D";
				funccionRadioButon();
			}
		});
		rBd.setBounds(10, 157, 23, 23);
		frmExamen.getContentPane().add(rBd);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(1152, 502, 422, 185);
		frmExamen.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabelBien = new JLabel("Puntuacion Bien");
		lblNewLabelBien.setForeground(Color.BLACK);
		lblNewLabelBien.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabelBien.setBounds(48, 17, 143, 20);
		panel.add(lblNewLabelBien);

		JLabel lblNewLabelMal = new JLabel("Puntuacion Mal");
		lblNewLabelMal.setForeground(Color.RED);
		lblNewLabelMal.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabelMal.setBounds(279, 17, 143, 20);
		panel.add(lblNewLabelMal);

		labelMal = new JLabel("0");
		labelMal.setForeground(Color.RED);
		labelMal.setFont(new Font("Tahoma", Font.BOLD, 90));
		labelMal.setBounds(319, 48, 62, 88);
		panel.add(labelMal);

		labelBien = new JLabel("0");
		labelBien.setForeground(Color.BLACK);
		labelBien.setFont(new Font("Tahoma", Font.BOLD, 90));
		labelBien.setBounds(82, 47, 62, 88);
		panel.add(labelBien);

		BtnRepetir = new JButton("Examen recuperacion");
		BtnRepetir.setVisible(false);
		BtnRepetir.setToolTipText("Haz click para  repetir las preguntas falladas");
		BtnRepetir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExamenUsuarioRecuperacion eR = new ExamenUsuarioRecuperacion();
				 frmExamen.dispose();
				 
			}
		});
		BtnRepetir.setBounds(1150, 453, 424, 43);
		frmExamen.getContentPane().add(BtnRepetir);
				
						lblPreg = new JLabel();
						lblPreg.setBounds(10, 37, 1564, 19);
						frmExamen.getContentPane().add(lblPreg);
						lblPreg.setFont(new Font("Tahoma", Font.PLAIN, 16));
						lblPreg.setText(ConexionBBDDExamenUsuario.siguientePregunta(contador));
						
						JMenuItem menuAyuda = new JMenuItem("Ayuda");
						menuAyuda.setBackground(Color.LIGHT_GRAY);
					    menuAyuda.setBounds(1, 0, 77, 22);
					    menuAyuda.setToolTipText("Informacion sobre el funcionamiento  del test");
						frmExamen.getContentPane().add(menuAyuda);
						
						JLabel lblNewLabel = new JLabel("num. de preg.");
						lblNewLabel.setBounds(117, 540, 77, 14);
						frmExamen.getContentPane().add(lblNewLabel);
						
						lblErrorNumero = new JLabel("");
						lblErrorNumero.setFont(new Font("Tahoma", Font.BOLD, 11));
						lblErrorNumero.setForeground(Color.RED);
						lblErrorNumero.setBounds(88, 560, 139, 14);
						frmExamen.getContentPane().add(lblErrorNumero);
						menuAyuda.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 new Ayuda();
							}
						});

	}

	private void funccionRadioButon() {
		if (ConexionBBDDExamenUsuario.comprobarResp(contador).equals(letra)) {
			contador++;
			lblBien.setText("Muy bien");
			lblMal.setText(" ");
			lblPreg.setText(ConexionBBDDExamenUsuario.siguientePregunta(contador));
			LblrespA.setText(ConexionBBDDExamenUsuario.sigRespA(contador));
			LblrespB.setText(ConexionBBDDExamenUsuario.sigRespB(contador));
			LblrespC.setText(ConexionBBDDExamenUsuario.sigRespC(contador));
			LblrespD.setText(ConexionBBDDExamenUsuario.sigRespD(contador));
			PuntuacionBien++;
			labelBien.setText(String.valueOf(PuntuacionBien));

		} else {
			rBa.setEnabled(false);
			rBb.setEnabled(false);
			rBc.setEnabled(false);
			rBd.setEnabled(false);
			lblMal.setText("Muy Mal");
			lblBien.setText(" ");
			lblRespuestaCorrecta.setVisible(true);
			lblCorPreg.setText(ConexionBBDDExamenUsuario.siguientePregunta(contador));
			lblCorResp.setText(miConexion.RespCor(contador));
			btnOk.setVisible(true);
			PuntuacionMal++;
			labelMal.setText(String.valueOf(PuntuacionMal));
			miConexion.ingresarEnArrayList(contador);
			
			}
			if (contador == ConexionBBDDExamenUsuario.registros) {
			contador = 0;
		}
	}
}
