package CrearExamenUsuario;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Conexion.ConexionBBDDExamen1;
import Conexion.ConexionBBDDExamenUsuario;
import PanelPrincipal.PanelPrincipal;

import javax.swing.JPanel;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.TextArea;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class CrearExamenUsuario {
	private JFrame frmDatosConexion;

	public static java.sql.Connection miCon;

	private JButton btnEligeArchivoPreg;

	private JButton btnCargarBD;
	private JButton btnArchivoResp;
	private JLabel lblCargaCorrecta;

	public static JLabel txtFdirArchivoResp;
	public static JLabel txtArchivoPreg;
	static FileWriter fichero = null;
	static PrintWriter pw = null;
	private JLabel lblNumRegResp;
	private JLabel lblNumRegPreg;
	public static JLabel lblErrorArchPreg;
	public static JLabel lblTodoOk;
	public static JLabel lblCargando;
	private JProgressBar bar;
	private JTextArea txtArea;
	private JLabel lblErrorArchResp;
	private JLabel label;
	static ConexionBBDDExamenUsuario  miConexion;

	public CrearExamenUsuario() {
		miConexion = new ConexionBBDDExamenUsuario();
		ConexionBBDDExamenUsuario.crearConexion();

		initialize();

	}

	private void initialize() {

		frmDatosConexion = new JFrame();
		frmDatosConexion.getContentPane().setBackground(SystemColor.textHighlight);
		frmDatosConexion.setTitle("Crear examen usuario");
		frmDatosConexion.setBounds(100, 100, 450, 300);
		frmDatosConexion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDatosConexion.getContentPane().setLayout(null);

		JLabel lblBaseDeDatos = new JLabel("Base de Datos : SQLite");
		lblBaseDeDatos.setBounds(63, 11, 150, 14);
		frmDatosConexion.getContentPane().add(lblBaseDeDatos);

		JLabel lblNombreBD = new JLabel("Directorio archivo Examen ");
		lblNombreBD.setBounds(10, 99, 167, 14);
		frmDatosConexion.getContentPane().add(lblNombreBD);

		JLabel lblDirectorioArchivoRespuesta = new JLabel("Directorio archivo respuesta correctas ");
		lblDirectorioArchivoRespuesta.setBounds(10, 139, 230, 14);
		frmDatosConexion.getContentPane().add(lblDirectorioArchivoRespuesta);

		btnEligeArchivoPreg = new JButton("Elige Archivo");
		btnEligeArchivoPreg.setBounds(563, 94, 93, 25);
		btnEligeArchivoPreg.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				lblErrorArchPreg.setText(" ");

				ConexionBBDDExamenUsuario.eligeArchivoExamenUsuario();
			}
		});
		frmDatosConexion.getContentPane().add(btnEligeArchivoPreg);

		btnCargarBD = new JButton("Carga BD");
		btnCargarBD.setBounds(124, 192, 89, 37);
		btnCargarBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				label.setText("En ejecucion...");

				WorkerDoSomething worker = new WorkerDoSomething(txtArea, label);
				worker.addPropertyChangeListener(new ProgressListener(bar));

				worker.execute();
			}

		});
		frmDatosConexion.getContentPane().add(btnCargarBD);

		btnArchivoResp = new JButton("Elige Archivo");
		btnArchivoResp.setBounds(563, 133, 93, 26);
		btnArchivoResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorArchPreg.setText(" ");
				ConexionBBDDExamenUsuario.eligeArchivoRespuestas();
			}
		});
		frmDatosConexion.getContentPane().add(btnArchivoResp);

		JPanel panel = new JPanel();
		panel.setBounds(11, 566, 645, 259);
		panel.setBackground(SystemColor.activeCaption);
		frmDatosConexion.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNumeroDeRegistro = new JLabel("Numero de registros tabla preguntas");
		lblNumeroDeRegistro.setBounds(10, 153, 238, 14);
		panel.add(lblNumeroDeRegistro);

		lblNumRegPreg = new JLabel("0");
		lblNumRegPreg.setBounds(319, 153, 46, 14);
		panel.add(lblNumRegPreg);

		JLabel lblNumeroDeRegistros = new JLabel("Numero de registros tabla respuesta correctas");
		lblNumeroDeRegistros.setBounds(10, 197, 274, 14);
		panel.add(lblNumeroDeRegistros);

		lblNumRegResp = new JLabel("0");
		lblNumRegResp.setBounds(319, 197, 46, 14);
		panel.add(lblNumRegResp);

		lblCargaCorrecta = new JLabel("");
		lblCargaCorrecta.setBounds(84, 11, 170, 14);
		panel.add(lblCargaCorrecta);

		lblErrorArchPreg = new JLabel("");
		lblErrorArchPreg.setForeground(Color.RED);
		lblErrorArchPreg.setFont(new Font("SansSerif", Font.BOLD, 16));

		lblErrorArchPreg.setBounds(10, 22, 622, 33);
		panel.add(lblErrorArchPreg);

		lblTodoOk = new JLabel("");
		lblTodoOk.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTodoOk.setBounds(10, 114, 625, 28);
		panel.add(lblTodoOk);

		lblErrorArchResp = new JLabel("");
		lblErrorArchResp.setForeground(Color.RED);
		lblErrorArchResp.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblErrorArchResp.setBounds(10, 67, 622, 33);
		panel.add(lblErrorArchResp);

		txtFdirArchivoResp = new JLabel("");
		txtFdirArchivoResp.setBounds(248, 139, 303, 14);
		frmDatosConexion.getContentPane().add(txtFdirArchivoResp);

		txtArchivoPreg = new JLabel("");
		txtArchivoPreg.setBounds(252, 99, 295, 14);
		frmDatosConexion.getContentPane().add(txtArchivoPreg);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(668, 11, 916, 807);
		panel_1.setBackground(SystemColor.controlShadow);
		frmDatosConexion.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JTextPane txtpneliminarEspacioEn = new JTextPane();
		txtpneliminarEspacioEn.setBounds(117, 46, 776, 115);
		panel_1.add(txtpneliminarEspacioEn);
		txtpneliminarEspacioEn.setText(
				"1) Abre NotePad++ y copia el texto en un documento nuevo COPIA SOLO PREGUNTAS Y RESPUESTAS\r\n2) Pregunta , respuesta A , respuesta B , respuesta C , respuesta D debe qudar en una linea\r\n\tEjemplo :  1). Mi nombre es? : A) Pedro B)Manolo C)Antonio D) Pepe\r\n3) Guarda el archivo.");
		txtpneliminarEspacioEn.setForeground(Color.BLACK);
		txtpneliminarEspacioEn.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtpneliminarEspacioEn.setEditable(false);
		txtpneliminarEspacioEn.setBackground(Color.LIGHT_GRAY);

		JTextPane txtpnAbraNotepad = new JTextPane();
		txtpnAbraNotepad.setText(
				"1) Abra Notepad++ y copie el texto en un documento nuevo  COPIE SOLO NUMERO Y LETRA.\r\n2)  Ejemplo : 1 A 2 B 3 D 4 C 5 A . El orden de los numeros da igual el programa cojera la letra a la derecha del numero\r\n     y  ordenara los numeros de menor a mayor respetando la letra a su derecha\r\n3) Guarde el archivo");
		txtpnAbraNotepad.setForeground(Color.BLACK);
		txtpnAbraNotepad.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtpnAbraNotepad.setEditable(false);
		txtpnAbraNotepad.setBackground(Color.LIGHT_GRAY);
		txtpnAbraNotepad.setBounds(117, 244, 776, 75);
		panel_1.add(txtpnAbraNotepad);

		JLabel lblNewLabel = new JLabel("Pasos para preparar el archivo de preguntas");
		lblNewLabel.setBounds(235, 13, 245, 16);
		panel_1.add(lblNewLabel);

		JLabel lblPasosParaPreparar = new JLabel("Pasos para preparar archivo de respuestas correctas");
		lblPasosParaPreparar.setBounds(235, 205, 312, 16);
		panel_1.add(lblPasosParaPreparar);

		JTextPane txtpnAntesDe = new JTextPane();
		txtpnAntesDe.setText(
				"1) Antes de pinchar sobre el boton cargarBD debe haber elegido dos archivos \r\n\t1- para las preguntas.\r\n\t2 -para las respuestas correctas.\r\n2) El programa  leera  los archivos y insertara  las preguntas y respuestas en cada campo de la base de datos\r\n3) En numero de registro tabla preguntas debe aparecer las cantidad de preguntas del archivo de texto elegido.\r\n4) En numero de registro tablas respuesta correcta debe haber la misma cantidad que en el punto 3.");
		txtpnAntesDe.setForeground(Color.BLACK);
		txtpnAntesDe.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtpnAntesDe.setEditable(false);
		txtpnAntesDe.setBackground(Color.LIGHT_GRAY);
		txtpnAntesDe.setBounds(117, 411, 776, 135);
		panel_1.add(txtpnAntesDe);

		lblCargando = new JLabel("");
		lblCargando.setBounds(234, 171, 150, 16);
		lblCargando.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblCargando.setForeground(Color.RED);
		frmDatosConexion.getContentPane().add(lblCargando);

		bar = new JProgressBar();
		bar.setBounds(259, 192, 167, 19);
		frmDatosConexion.getContentPane().add(bar);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 256, 646, 297);
		frmDatosConexion.getContentPane().add(scrollPane);

		txtArea = new JTextArea();
		txtArea.setEditable(false);
		txtArea.setText("Proceso de funciones\r\n");
		scrollPane.setViewportView(txtArea);

		label = new JLabel("");
		label.setBounds(269, 228, 157, 16);
		frmDatosConexion.getContentPane().add(label);
		frmDatosConexion.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frmDatosConexion.setBounds(0, 0, screen.width, screen.height - 30);
		frmDatosConexion.setExtendedState(Frame.MAXIMIZED_BOTH);

	}

	public void texto() {
		int c;
		int cont = 0;
		while (cont < 20) {
			try {
				Thread.sleep(1000);
				CrearExamenUsuario.lblCargando.setText(" ");
				// Thread.sleep(1000);
				cont++;

				c = cont % 2;
				System.out.println(c);
				// System.out.println(cont);
				if (c == 0) {
					CrearExamenUsuario.lblCargando.setText("cargando ");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	class ProgressListener implements PropertyChangeListener {
		private JProgressBar bar;

		ProgressListener() {
		}

		ProgressListener(JProgressBar b) {
			this.bar = b;
			bar.setValue(0);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			// Determine whether the property is progress type
			if ("progress".equals(evt.getPropertyName())) {
				bar.setValue((int) evt.getNewValue());
			}
		}
	}

	class WorkerDoSomething extends SwingWorker<String, String> {

		private JTextArea txtArea;
		private JLabel label;

		// constructor de la clase interior
		WorkerDoSomething(JTextArea t, JLabel l) {
			txtArea = t;
			label = l;
		}

		@Override
		protected String doInBackground() throws Exception {
			// To identify whether the background job is running out of EDT

			Integer progress = 0;

			publish("****************Procesos Preguntas***************************");
			ConexionBBDDExamenUsuario.EditarExamenTexto();
			progress = 12;

			setProgress(progress);
			System.out.println("RESULTADO DE EDITADOPREGBIEN " + ConexionBBDDExamenUsuario.editadoPregBien);
			if (ConexionBBDDExamenUsuario.editadoPregBien) {
				publish("El archivo de preguntas examen se ha  preparado con exito");
				ConexionBBDDExamenUsuario.EliminarTablaExamenUsuario();
				progress = 25;
				publish("eliminado la tabla ExamenUsuario de la base de datos");
				setProgress(progress);
				ConexionBBDDExamenUsuario.crearTablaExamenUsuario();
				progress = 37;
				publish("Tabla examenUsuario creado ");
				setProgress(progress);
				ConexionBBDDExamenUsuario.cargaBDExamenUsuario();
				progress = 50;
				publish("Cargado el archivo  preguntas examen con exito");
				setProgress(progress);
			} else {
				ConexionBBDDExamenUsuario.copiarArchivoExamenPreg(ConexionBBDDExamenUsuario.directorio1,
						ConexionBBDDExamenUsuario.archivoDeTextoExamen);
				lblErrorArchPreg.setText(
						"Ha habido un error en el archivo de preguntas se ha creado una copia con el nombre copiaPreguntas en el mismo directorio");
				publish("NO SE HA EDITADO BIEN EL ARCHIVO , NO SE HA CARGADO A LA BASE DE DATOS");
				publish("Se ha creado una copia del archivo elegido en el mismo directorio , revise lo por favor");

				for (String listaErrores : ConexionBBDDExamenUsuario.listaErrorGrupo) {
					publish(listaErrores);
				}
				for (String str : ConexionBBDDExamenUsuario.mensajeErrorTamano) {
					publish(str);

				}
			}

			ConexionBBDDExamenUsuario.mostrarTablasDB();
			System.out.println("Metodos Preguntas examen finalizados");
			publish("");
			publish("******************Procesos Respuestas*********************************");
			publish("");
			ConexionBBDDExamenUsuario.EditarRespuestasTexto();
			progress = 62;

			setProgress(progress);
			if (ConexionBBDDExamenUsuario.editadoRespBien) {
				publish("Preparado el archivo respuestas con exito");
				ConexionBBDDExamenUsuario.EliminarTablaRespUsuario();
				progress = 75;
				publish("Eliminado la tabla respuestaCorrectas  con exito");
				setProgress(progress);
				System.out.println();
				ConexionBBDDExamenUsuario.crearTablaRespCorrecta();
				progress = 87;
				publish("Creado la tabla respuestasCorrectas  con exito");
				setProgress(progress);
				ConexionBBDDExamenUsuario.insertarBBDDRespCorrectas();
				ConexionBBDDExamenUsuario.mostrarTablasDB();
				progress = 100;
				publish("Cargado la tabla respuestascorrectas  con exito");
				setProgress(progress);
			} else {
				ConexionBBDDExamenUsuario.copiarArchivoRespuestas(ConexionBBDDExamenUsuario.directorioResp,
						ConexionBBDDExamenUsuario.archivoDeTextoResp);
				lblErrorArchPreg.setText(
						"Ha habido un error en el archivo de respuestas se ha creado una copia con el nombre copiaRespuestas en el mismo directorio");
				publish("NO SE HA EDITADO BIEN EL ARCHIVO , NO SE HA CARGADO A LA BASE DE DATOS");
				publish("Se ha creado una copia del archivo elegido llamado copiaRespuestas  en el mismo directorio revise los fallos");
			}

			if (ConexionBBDDExamenUsuario.editadoRespBien && ConexionBBDDExamenUsuario.editadoPregBien) {
				lblNumRegPreg.setText(ConexionBBDDExamenUsuario.contarPreguntasInsertados());
				lblNumRegResp.setText(ConexionBBDDExamenUsuario.contarRespuestasInsertados());
				if (ConexionBBDDExamenUsuario.contRegistroPreg == ConexionBBDDExamenUsuario.contRegistroResp) {
					lblTodoOk.setText(
							"Todo ha ido bien puede usar el examen en : PanelPrincipal -> examen Usuario ->ira a examen");
				}
			}

			// El return se mostrara en un label con el metodo done()
			return "Hecho";
		}

		// Este metodo done() pertenece a la clase implemenado SwingWorker.
		@Override
		protected void done() {
			try {
				// Añadimos al label el return de del metodo doInBackground()
				// con get() heredado de la clase Stringworker
				label.setText(" " + get());
			} catch (Exception ignore) {
			}
		}

		// process() metodo sobre escrito de de la clase StringWorker para
		// recibir el publish() del metodo doInBackGround().
		@Override
		protected void process(List<String> listapublish) {
			for (String num : listapublish) {
				// textArea para mostrar el String de publish().
				txtArea.append(num + "\n");
			}
		}

	}

}
