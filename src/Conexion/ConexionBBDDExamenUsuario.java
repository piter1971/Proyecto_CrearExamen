package Conexion;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import CrearExamenUsuario.CrearExamenUsuario;


import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.JFileChooser;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class ConexionBBDDExamenUsuario<PruebaConexion> {
	
	CrearExamenUsuario crearExUs = null;	
	static JFileChooser chooser;
	
	static ResultSet rs = null;	
	
	static String  mensajeErrorA;
	static String  mensajeErrorB;
	static String  mensajeErrorC;
	static String  mensajeErrorD;
	static String letra ;
	static int numero;

	public static TreeSet<Integer> idPregunta = new TreeSet<Integer>();
	static Map<Integer, String> treeMap = new TreeMap<Integer, String>();
	public static List<String> listaMensajeError= new ArrayList<>();
	public static List<String> listaErrorGrupo = new ArrayList<>();
	static List<String> pregList = new ArrayList<>();
	static List<String> respAList = new ArrayList<>();
	static List<String> respBList = new ArrayList<>();
	static List<String> respCList = new ArrayList<>();
	static List<String> respDList = new ArrayList<>();
	static List<Integer> listaErrores = new ArrayList<>();
	static ArrayList<String> lista = new ArrayList<>();
	static ArrayList<String> sincomaslista = new ArrayList<>();
	public static ArrayList<String> mensajeErrorTamano = new ArrayList<>();
	static List<String> contestacionList = new ArrayList<>();
	static List<String> contSinComasLista = new ArrayList<>();
	 static ArrayList<String> lineasModificadas = new ArrayList<>();
	 public static ArrayList<String> listaFinal = new ArrayList<>();
	 
	public static String archivoDeTextoExamen;	
	static String linea;
	static String preg = "";
	static String respA = "";
	static String respB = "";
	static String respC = "";
	static String respD = "";	
	static String archivoDeTextoRespuesta;	
	public static String directorioResp;
	public static String directorio1;		
	public static String archivoDeTextoResp;
	
	static int contadorlinea = 0;
	public static int contRegistroPreg;
	public static  int contRegistroResp;
	static int cont = 0;
	public static int registros;
	static int conta=0;
	
	static public boolean editadoPregBien=true;
	static public boolean editadoRespBien=true;
	static boolean todoBien = true;
	private static String mensajeErrorGrupo;

	/********************pruebas*************************************/
	static void conectarBD(){
		 Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	    //  System.out.println("Opened database successfully");
	}
	
	public static void crearConexion() {
		 registros=0;
		 
		 Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
		Statement st =c.createStatement();
		//	System.out.println("estamos conectados");
			ResultSet rs = st.executeQuery("SELECT * FROM  PreguntasExamenUsuario");
			while (rs.next()) {
				registros++;
			}
	
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/*Metodos  Examen Preguntas*/
	public static void EditarExamenTexto() {
		String preg = null;
		String respA = null;
		String respB = null;
		String respC = null;
		String respD = null;
		String sinComas = "";
		String reempl = null;
		String lineaLeido;
		String grupoParentesis = "";
		String grupoComa = "";
		String aComa = "";
		String bComa = "";
		String cComa = "";
		String dComa = "";
		String mensajeErrorTamA;
		String mensajeErrorTamB;
		String mensajeErrorTamC;
		String mensajeErrorTamD;
		
		List<String> lineasParentesis = new ArrayList<>();
		List<String> lineaList = new ArrayList<>();		
		List<String> lineaSinEspacio = new ArrayList<>();
		List<String> lineaSinEspacMod = new ArrayList<>();		 
		int contLineas = 0;
		int contErrores = 0;		
		boolean ctrl_Interno=true;
		try {
			//

			Scanner scannerLeerArchivo = new Scanner(new File(ConexionBBDDExamenUsuario.directorio1));
			// while para leer el archivo con Scanner y eliminar lineas en
			// blanco y todas la lineas que no empiece por un numero de
			// pregunta.
			while (scannerLeerArchivo.hasNextLine()) {
				lineaLeido = scannerLeerArchivo.nextLine();
				/* System.out.println(lineaLeido); */

				// no insertar en ArrayList lineaSinEspacio si la linea leido es
				// una linea en blanco
				if (lineaLeido.equals(" ") || !lineaLeido.matches("(\\d.*)")) {

				} else {
					// Guardado en Arraylist lneaSinEspacio el contenido del
					// archivo elegido sin lineas en blanco y solo lineas que
					// empiece por un numero
					lineaSinEspacio.add(lineaLeido);
					contLineas++;
				}
			} /* final while scannerLeerArchivo */
		} catch (Exception e) {
			System.out.println("ERROR en la lectura");
			System.out.println(e);
		}

		//System.out.println(				"************************************************************************************************");		

		// Reemplazar de Arraylist lineaSinEspacio las lineas que contenga letra
		// ) y letra) para que solo las opciones de respuestas tenga letra).
		for (String str : lineaSinEspacio) {
		
			Pattern pat = Pattern.compile("(\\w\\) y \\w\\))");
			Matcher mat = pat.matcher(str);

			
			grupoParentesis = "";
			aComa = "";
			bComa = "";
			cComa = "";
			dComa = "";

			if (mat.find()) {
				// grupo capturado con mat.group() guardado en variable
				// capturadoGrupo
				grupoParentesis = mat.group();
				
				lineasParentesis.add(str);

			} /* final if mat.find() */

			// solo entraran las lineas donde se haya encontrado un grupo de
			// parentesis que no sean opciones de respuestas.
			if (!grupoParentesis.equals("")) {

				for (String string1 : grupoParentesis.split(" ")) {
					if (string1.equals("a)")) {
						aComa = string1.replace("a)", "a,");
					} else if (string1.equals("b)")) {
						bComa = string1.replace("b)", "b,");
					} else if (string1.equals("c)")) {
						cComa = string1.replace("c)", "c,");
					} else if (string1.equals("d)")) {
						dComa = string1.replace("d)", "d,");
					}

				} /* final for string1 : grupoParentesis.split(" ") */

				grupoComa = aComa + " " + bComa + " " + cComa + " " + dComa;
				
				// System.out.println(grupoParentesis +" =>"+ grupoComa);
				// llega la str= linea con parentesis
				// Guardamos en la variable reempl la actual linea reemplazado
				// los parentesis por comas
				reempl = str.replace(grupoParentesis, grupoComa);
				// Reemplazamos la str = linea con parentesis por reempl = linea
				// con coma
				str = reempl;

			} /* final if !grupoParentesis.equals("") */
			// System.out.println(str);
			lineaSinEspacMod.add(str);
		} /* final for lineaSinEspacio */
		/*
		 * for (String string : lineaSinEspacMod) { System.out.println(string);
		 * }
		 */
		int contadorLineaSinEspacio=0;
		for (String string : lineaSinEspacMod) {
			Pattern pat = Pattern.compile("a\\).*b\\).*c\\).*d\\).*");
			Matcher mat = pat.matcher(string);
			contadorLineaSinEspacio++;
			if (mat.find()){
			
			
			}else{
				mensajeErrorGrupo="Linea  :"+ contadorLineaSinEspacio +" no tiene el grupo de opciones a) , b) , c) , d)";
				listaErrorGrupo.add(mensajeErrorGrupo);
				ctrl_Interno=false;
			}
			sinComas = string.replace(" a)", ";a)");
			sinComas = sinComas.replace(" b)", ";b)");
			sinComas = sinComas.replace(" c)", ";c)");
			sinComas = sinComas.replace(" d)", ";d)");
			// añadir letras reemplazados a ArrayList sincomaslista
			listaFinal.add(sinComas);
		}
		if (ctrl_Interno) {
			
		
		//listaFinal contiene las lineas con todo   modificados a) b) c) d) que NO sean opciones de respuestas  por a, b, c, d,  y    a) b) c) d )  por ;a)  ;b) ;c) ;d)
		for (String string : listaFinal) {
			//separando  por ; y guardando cada parte en ArrayList   lineaList
			lineaList = Arrays.asList(string.split(";"));
			preg = lineaList.get(0);
			// Recorrer ArrayList  lineaList y guardar cada posicion en una variable para obtener la pregunta y contestaciones de cada linea
			for (int i = 0; i < lineaList.size(); i++) {
				preg = lineaList.get(0);
				respA = lineaList.get(1);
				respB = lineaList.get(2);
				respC = lineaList.get(3);
				respD = lineaList.get(4);
				
				

			} // final for*/
			//metemos cada las variables anterior en una Arraylist para su comprobacion . Cada pregunta debe empezar por a)    b)     c)    o     d)
				pregList.add(preg);
				respAList.add(respA);
				respBList.add(respB);
				respCList.add(respC);
				respDList.add(respD);
			
			
			contLineas=pregList.size();
			if (!(respAList.size()==contLineas)  ){
				editadoPregBien= false;
			mensajeErrorTamA="Hay un ERROR en A";
			mensajeErrorTamano.add(mensajeErrorTamA);
			}else if(!(respBList.size()==contLineas) ){
				editadoPregBien= false;
				mensajeErrorTamB="Hay un ERROR en B";
				mensajeErrorTamano.add(mensajeErrorTamB);
			}else if(!(respCList.size()==contLineas) ){
				editadoPregBien= false;
				mensajeErrorTamC="Hay un ERROR en C";
				mensajeErrorTamano.add(mensajeErrorTamC);
			}else if(!(respDList.size()==contLineas) ){
				editadoPregBien= false;
				mensajeErrorTamD="Hay un ERROR en D";
				mensajeErrorTamano.add(mensajeErrorTamD);
				
			}
				

		} // final for
		}
		//Comprobacion de los ArrayList anteriores.  cargaBDExamenUsuario() solo funcionara si la variable  todoBien esta a true
		if (ctrl_Interno) {
			
		
		for (int i = 0; i < respAList.size(); i++) {
			contErrores++;
			if (!respAList.get(i).startsWith("a)")) {
				mensajeErrorA="Error en linea " + contErrores + " respA no empieza por :  a)  ¿Esta la coma  bien puesta ,a) ?";
				ctrl_Interno = false;
			} else if (!respBList.get(i).startsWith("b)")) {
				mensajeErrorB=	"Error en linea " + contErrores + " respB no empieza por :  b) ¿Esta la coma  bien puesta ,b) ?";
				ctrl_Interno= false;
			} else if (!respCList.get(i).startsWith("c)")) {
				mensajeErrorC="Error en linea " + contErrores + " respC  no empieza por :  c) ¿Esta la coma  bien puesta ,c) ?";
				ctrl_Interno = false;
			} else if (!respDList.get(i).startsWith("d)")) {
				mensajeErrorA="Error en linea " + contErrores+ " respD  no empieza por :  d) ¿Esta la coma  bien puesta ,d) ?";
				ctrl_Interno= false;
			}
		}
		}
		editadoPregBien=ctrl_Interno ;
		System.out.println(editadoPregBien);
		
		
		}
	
	public static void crearTablaExamenUsuario(){
		 Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
	         System.out.println("Opened database successfully crearTablaExamenUsuario");

	         stmt = c.createStatement();
	         String sql = "CREATE TABLE PreguntasExamenUsuario " +
	                        "(ID INTEGER PRIMARY KEY   AUTOINCREMENT  NOT NULL," +
	                        " Pregunta          TEXT    NOT NULL, " + 
	                        " RespA           TEXT    NOT NULL,  " + 
	                        " RespB       TEXT    NOT NULL, " + 
	                        " RespC       TEXT    NOT NULL, " + 
	                        " RespD         TEXT    NOT NULL)"; 
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Table created successfully");
	   }
		
	public static void cargaBDExamenUsuario(){
		Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully cargaExmaenUsuario");

	         stmt = c.createStatement();
	     	for (int i = 0; i < respAList.size(); i++) {				
				String instruccionFechaManual = "INSERT INTO PreguntasExamenUsuario  (Pregunta,RespA,RespB,RespC,RespD) VALUES ('"
						+ pregList.get(i) + "','" + respAList.get(i) + "','" + respBList.get(i) + "','"
						+ respCList.get(i) + "','" + respDList.get(i) + "')";
					stmt.executeUpdate(instruccionFechaManual);
				//System.out.println("datos insertados correctamente");
		}
	         
	         
	         stmt.close();
	         c.commit();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Records created successfully");
	   }

	public static void EliminarTablaExamenUsuario(){
		Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("org.sqlite.JDBC");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
		    
		      
		      //STEP 4: Execute a query
		     
		      stmt = conn.createStatement();
		      
		      String sql = "DROP TABLE PreguntasExamenUsuario  ";
		 
		      stmt.executeUpdate(sql);
		     
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		 
		}//end main
		
	public static void eligeArchivoExamenUsuario() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));

		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// System.out.println(chooser.getCurrentDirectory());
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			/*
			 * System.out.println("getCurrentDirectory(): " +
			 * chooser.getCurrentDirectory());
			 */
			// System.out.println("getSelectedFile() : "+
			// chooser.getSelectedFile());
			directorio1 = chooser.getSelectedFile().getAbsolutePath();
			CrearExamenUsuario.txtArchivoPreg.setText(directorio1);
			ConexionBBDDExamenUsuario.archivoDeTextoExamen = directorio1;
			
			String directorio = chooser.getCurrentDirectory().toString();
			CrearExamenUsuario.txtArchivoPreg.setText(directorio1);
			///System.out.println(directorio + "copiaPreguntas.txt");
			archivoDeTextoExamen = directorio + "\\copiaPreguntas.txt";
			
			// System.out.println(directorio);
		} else {
			System.out.println("No Selection ");
		}
	}
	
	public static void copiarArchivoExamenPreg(String origenArchivo, String destinoArchivo) {

		final Logger LOGGER = Logger.getAnonymousLogger();
		try {
			Path origenPath = Paths.get(origenArchivo);
			Path destinoPath = Paths.get(destinoArchivo);
			// sobreescribir el fichero de destino si existe y lo copia
			Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
			//System.out.println("Todo ha ido bien copia realizado " + destinoArchivo);
			PrintWriter pr = new PrintWriter(destinoArchivo);

			for (String string : listaFinal) {
				// System.out.println(string);
				pr.println(string);
				pr.flush();
			}
			pr.close();
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
	}

	public static String contarPreguntasInsertados() {
		String numeroRegistro = "";
		ResultSet Rset = null;
		 Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			 stmt = c.createStatement();

			Rset = stmt.executeQuery("SELECT count(*) from PreguntasExamenUsuario");
			numeroRegistro = Rset.getString(1);
			contRegistroPreg = Integer.parseInt(numeroRegistro);
		c.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return numeroRegistro;
	}

	/*Fin Metodos Examen usuario*/
	//****************************************************
	/*Metodos Respuestas correctas*/
	public static void EditarRespuestasTexto() {
		int numerosNum=0;
		int numerosLetras=0;
		String sinComas = "";
		String linea = "";
		try {
			File archivo = new File(ConexionBBDDExamenUsuario.directorioResp);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			int contador=0;
			while ((linea = br.readLine()) != null) {
				if (linea.equals("")) {

				} else {
					// contSinComasLista.add(linea);
					StringTokenizer st = new StringTokenizer(linea);
					while (st.hasMoreTokens()) {
						
						contador++;
						try {
						
								numero = Integer.parseInt(st.nextToken());
							//System.out.println(numero);
							 
							
							
							 String n =String.valueOf(numero);	
							
								 letra = st.nextToken();
								 if (letra.equals("A") ) {
									
								}else if (letra.equals("B")){
									
								}else if (letra.equals("C")){
									
								}else if (letra.equals("D")){
									
								}else if (letra.equals(" ")){
									
								}else{
									letra="NO hay letra";
								}
						//	System.out.println(letra);
								
								treeMap.put(numero, letra);
						} catch (NumberFormatException e) {
							editadoRespBien=false;
						 //   System.out.println("numero incorecto "+ contador);
						    
						}					
							}
						 }	
			}
						
			br.close();
			System.out.println("br cerrado");
					
		} catch (Exception e) {
			System.out.println("Error en el archivo con las respuestas correctas revise el archivo porfavor");
			editadoRespBien=false;
			System.out.println(e);

		}
		/*Iterator it = treeMap.keySet().iterator();
		while (it.hasNext()) {
			Integer key = (Integer) it.next();

			System.out.println("Clave: " + key + " -> Valor: " + treeMap.get(key));
		}*/
	}
	
	public static void crearTablaRespCorrecta(){
		 Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
	         System.out.println("Opened database successfully crearTablaExamenUsuario");

	         stmt = c.createStatement();
	         String sql = "CREATE TABLE ExamenUsuarioRespCorrectas " +
	                        "(ID INTEGER PRIMARY KEY   AUTOINCREMENT  NOT NULL," +
	                        " RespCor         TEXT    NOT NULL)"; 
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Table ExamenUsuarioRespCorrectas created successfully");
	   }
		
	public static void insertarBBDDRespCorrectas() {
		 Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			stmt = c.createStatement();
			System.out.println(" mi  conexion esta cerrada "+ c.isClosed());
			Iterator it = treeMap.keySet().iterator();
			while (it.hasNext()) {
				Integer key = (Integer) it.next();
				String insert = " INSERT INTO ExamenUsuarioRespCorrectas(RespCor) VALUES ('" + treeMap.get(key) + "')";
				stmt= c.createStatement();
				stmt.executeUpdate(insert);
				System.out.println("Clave: " + key + " -> Valor: " + treeMap.get(key));
			}
			//System.out.println("Insertado con exito");
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ERROR en la inserccion respuesta correcta");
			e.printStackTrace();
		}

	}
	
	public static void eligeArchivoRespuestas() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));

		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// System.out.println(chooser.getCurrentDirectory());
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			/*
			 * System.out.println("getCurrentDirectory(): " +
			 * chooser.getCurrentDirectory());
			 */
			// System.out.println("getSelectedFile() : "+
			// chooser.getSelectedFile());
			directorioResp = chooser.getSelectedFile().getAbsolutePath();
			CrearExamenUsuario.txtFdirArchivoResp.setText(directorioResp);
			String directorio = chooser.getCurrentDirectory().toString();
			ConexionBBDDExamenUsuario.archivoDeTextoRespuesta = directorioResp;
			 archivoDeTextoResp = directorio + "\\copiaRespuestas.txt";
			// System.out.println(directorio);
		} else {
			System.out.println("No Selection ");
		}
	}

	public static void EliminarTablaRespUsuario() {
		
			Connection conn = null;
			   Statement stmt = null;
			   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("org.sqlite.JDBC");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to a selected database...");
			      conn = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			    
			      
			      //STEP 4: Execute a query
			     
			      stmt = conn.createStatement();
			      
			      String sql = "DROP TABLE ExamenUsuarioRespCorrectas  ";
			 
			      stmt.executeUpdate(sql);
			     
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            conn.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			 

		}
			
	public static  String  contarRespuestasInsertados(){
		String numeroRegistro = "";
		ResultSet Rset = null;
		 Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			 stmt = c.createStatement();

			Rset = stmt.executeQuery("SELECT count(*) from ExamenUsuarioRespCorrectas");
			numeroRegistro = Rset.getString(1);
			contRegistroResp = Integer.parseInt(numeroRegistro);
			System.out.println("registro resipuestas"+contRegistroResp);
		c.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return numeroRegistro;
	}
	
	public static void copiarArchivoRespuestas(String origenArchivo, String destinoArchivo) {

		final Logger LOGGER = Logger.getAnonymousLogger();
		try {
			Path origenPath = Paths.get(origenArchivo);
			Path destinoPath = Paths.get(destinoArchivo);
			// sobreescribir el fichero de destino si existe y lo copia
			Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
			//System.out.println("Todo ha ido bien copia realizado " + destinoArchivo);
			PrintWriter pr = new PrintWriter(destinoArchivo);

			  for (Entry<Integer, String> entry : treeMap.entrySet()) {
				  pr.println(entry.getKey() + "  " + entry.getValue());
					pr.flush();
		        } 
				
			
			pr.close();
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
	}
	

	/*Fin metodo respuestas correctas*/

	static void selectBD(){
		Connection c = null;
		   Statement stmt = null;
		   try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
		      
		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         int age  = rs.getInt("age");
		         String  address = rs.getString("address");
		         float salary = rs.getFloat("salary");
		         
		         System.out.println( "ID = " + id );
		         System.out.println( "NAME = " + name );
		         System.out.println( "AGE = " + age );
		         System.out.println( "ADDRESS = " + address );
		         System.out.println( "SALARY = " + salary );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
		   System.out.println("Operation done successfully");
		  }
	/********************fin pruebas************************************/
		
	// Selecciona siguiente pregunta de la BD
	public static String siguientePregunta(int cont) {
		
		 Connection c = null;
		String primerReg = "SELECT Pregunta FROM PreguntasExamenUsuario  where ID=? ";
		PreparedStatement st=null;
		ResultSet rs=null;
		String pregunta = null;
		try {
			  Class.forName("org.sqlite.JDBC");
		         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");			
			st =c.prepareStatement(primerReg);
			st.setInt(1, cont);
			rs = st.executeQuery();
			
			while (rs.next()) {
				pregunta = rs.getString(1);
				System.out.println(pregunta);			
			}
			
			c.close();
		} catch (SQLException e) {
			System.out.println("ERROR en la ejecucion del SELECT pregunta");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pregunta;
		
	}


	// Ingresar en un ArrayList el id de la preguntas falladas
	public void ingresarEnArrayList(int contador) {

		idPregunta.add(contador);

		// System.out.println(idPregunta.get(i));
		// idPregunta.add(contador);
		/*
		 * for (int j = 0; i < idPregunta.size(); j++) {
		 * 
		 * System.out.println(idPregunta.get(j)); }
		 */
	}

	// Seleciona siguiente respuesta a de la BD
	public static String sigRespA(int cont) {
		 Connection c = null;
		String primerRegRespA = "SELECT RespA FROM PreguntasExamenUsuario  where ID=? ";
		PreparedStatement st;
		String respA = null;
		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			st =c.prepareStatement(primerRegRespA);
			st.setInt(1, cont);
			rs = st.executeQuery();
			while (rs.next()) {
				respA = rs.getString(1);
			}
			c.close();
		} catch (SQLException e) {
			System.out.println("ERROR en la ejecucion del SELECT respA");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respA;
	}

	// Seleciona siguiente respuesta b de la BD
	public static String sigRespB(int cont) {
		 Connection c = null;
		String primerRegRespB = "SELECT RespB FROM PreguntasExamenUsuario  where ID=? ";
		PreparedStatement st;
		String respB = null;
		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			st = c.prepareStatement(primerRegRespB);
			st.setInt(1, cont);
			rs = st.executeQuery();
			while (rs.next()) {
				respB = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("ERROR en la ejecucion del SELECT respB");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respB;
	}

	// Seleciona siguiente respuesta c de la BD
	public static String sigRespC(int cont) {
		 Connection c = null;
		String primerRegRespC = "SELECT respC FROM PreguntasExamenUsuario  where ID=? ";
		PreparedStatement st;
		String respC = null;
		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			st = c.prepareStatement(primerRegRespC);
			st.setInt(1, cont);
			rs = st.executeQuery();
			while (rs.next()) {
				respC = rs.getString(1);
			}
			c.close();
		} catch (SQLException e) {
			System.out.println("ERROR en la ejecucion del SELECT respC");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respC;
	}

	// Seleciona siguiente respuesta d de la BD
	public static String sigRespD(int cont) {
		Connection c = null;
		String primerRegRespD = "SELECT RespD  FROM PreguntasExamenUsuario  where ID=? ";
		PreparedStatement st;
		String respD = null;
		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			st = c.prepareStatement(primerRegRespD);
			st.setInt(1, cont);
			rs = st.executeQuery();
			while (rs.next()) {
				respD = rs.getString(1);
			}
			c.close();
		} catch (SQLException e) {
			System.out.println("ERROR en la ejecucion del SELECT respD");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respD;
	}
	
	public String RespCor(int contador) {
		Connection c = null;
		String letra = "";
		String respCor = "";
		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			PreparedStatement prepSt;
			prepSt = c.prepareStatement("SELECT RespCor  FROM ExamenUsuarioRespCorrectas WHERE  ID = ? ");
			prepSt.setInt(1, contador);
			rs = prepSt.executeQuery();
			while (rs.next()) {
				letra = rs.getString(1).trim();
			}
			System.out.println(respCor);
			if (letra.equals("A")) {
				prepSt = c.prepareStatement("SELECT RespA  FROM PreguntasExamenUsuario   WHERE ID= ? ");
				prepSt.setInt(1, contador);
				rs = prepSt.executeQuery();
				while (rs.next()) {
					respCor = rs.getString(1);
				}
			} else if (letra.equals("B")) {
				prepSt = c.prepareStatement("SELECT RespB  FROM PreguntasExamenUsuario  WHERE ID = ? ");
				prepSt.setInt(1, contador);
				rs = prepSt.executeQuery();
				while (rs.next()) {
					respCor = rs.getString(1);
				}
			} else if (letra.equals("C")) {
				prepSt = c.prepareStatement("SELECT RespC  FROM PreguntasExamenUsuario   WHERE ID = ? ");
				prepSt.setInt(1, contador);
				rs = prepSt.executeQuery();
				while (rs.next()) {
					respCor = rs.getString(1);
				}
			} else if (letra.equals("D")) {
				prepSt =c.prepareStatement("SELECT RespD  FROM PreguntasExamenUsuario   WHERE ID = ? ");
				prepSt.setInt(1, contador);
				rs = prepSt.executeQuery();
				while (rs.next()) {
					respCor = rs.getString(1);
				}

			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return respCor;

	}

	// Obtener lerta correcta de BD Examen1RespCorrecta para comparar con
	// radiobutton seleccionado
	public static String comprobarResp(int contador) {
		Connection c = null;
		PreparedStatement prepSt;
		String letraCorecta = "";

		try {
			 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db");
			prepSt = c.prepareStatement("SELECT RespCor  FROM ExamenUsuarioRespCorrectas  where ID = ?");
			prepSt.setInt(1, contador);
			rs = prepSt.executeQuery();
			while (rs.next()) {
				letraCorecta = rs.getString(1).trim();
			} // final while
			c.close();
		} 		catch (Exception e) {
			System.out.println("ERROR en el select letra correcta");
		} // final catch
		return letraCorecta;
	}

	// leer de un archivo .txt todoas las letras correctas y
	// Insertar en la BD todas la lertas correctas del examen
	
	public static void mostrarTablasDB(){
		Connection c;
		ResultSet r; 
		try{
			Class.forName("org.sqlite.JDBC"); 
			c = DriverManager.getConnection("jdbc:sqlite:ExamenUsuario.db"); 
			DatabaseMetaData metaData = c.getMetaData(); 
			// Imprime tablas 
			System.out.println("Tablas:"); 
			r = metaData.getTables(null, null, null,null); 
			while(r.next()) { 
			System.out.println(r.getString("TABLE_NAME")); 
			} 
			c.close(); 
		}catch (Exception e) {
			// TODO: handle exception
		}
			
			}
	
}
