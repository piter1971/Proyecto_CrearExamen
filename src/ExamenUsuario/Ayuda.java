package ExamenUsuario;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import java.awt.TextField;
import java.awt.Font;

public class Ayuda {

	private JFrame frmAyuda;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public Ayuda() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAyuda = new JFrame();
		frmAyuda.setForeground(Color.BLACK);
		frmAyuda.setBackground(Color.LIGHT_GRAY);
		frmAyuda.setVisible(true);
		frmAyuda.getContentPane().setForeground(Color.LIGHT_GRAY);
		frmAyuda.setTitle("Ayuda");
		frmAyuda.setBounds(100, 100, 469, 404);
		frmAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAyuda.getContentPane().setLayout(null);
		
		JTextPane txtpnSeDebeContestar = new JTextPane();
		txtpnSeDebeContestar.setBackground(Color.LIGHT_GRAY);
		txtpnSeDebeContestar.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtpnSeDebeContestar.setForeground(Color.BLACK);
		txtpnSeDebeContestar.setEditable(false);
		txtpnSeDebeContestar.setText("Se debe contestar las preguntas haciendo click sobre un de las cuatro circulos  \r\nEn el momento que se falla una pregunta se mostrara en la parte inferior la pregunta\r\nfallada y la respuesta correcta . Tambien se mostrara un boton 'OK' . Este boton se debe presiona para poder seguir a la siguiente pregunta.\r\n\r\nTambien se puede ir a  una pregunta concreta  ponga en la casilla 'num. pregunta' el numero de la pregunta y se activara  el boton 'Ir a la pregunta'.\r\n\r\nEn el momento que haya fallado un pregunta se mostrara el boton 'Examen recuperacion'\r\nHaciendo click sobre el boton  podra volver a responder las preguntas falladas.  El marcador del test recuperacion iniciara con la cantidad de preguntas falladas cada vez que acierta una pregunta se descontara de las preguntas falladas y se sumara de las preguntas acertadas");
		txtpnSeDebeContestar.setBounds(10, 11, 433, 343);
		frmAyuda.getContentPane().add(txtpnSeDebeContestar);
	}
}
