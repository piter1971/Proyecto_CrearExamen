# Proyecto_CrearExamen
                                    Objetivo
Este es un proyecto presonal , el programa esta pensado para Cargar a una base de datos un  examen en PDF y ejecutar el examen
Se necesita un PDF con las preguntas y las opciones de respuestas tipo test y  otro PDF con las respuestas correctas
El programa indicara al usuario si ha contestado bien o no  con una opcion de recuperacion de las preguntas falladas.
Hay dos modos de crear un examen :  1) Examen cargado por el programador se accede desde un menu en el panel principal
                                    2) Examen cargado por el usuario reemplazara el ultimo examen cargado por el usuario.
                                    
                                    Engranaje del proyecto
PanelPrincipal.java :
La ventana principal del programa, con un barra menu con submenu: 
Examenes  - 1) ir al examen creado por el programador
          - 2) ir al examen creado por el usuario 
                                                                                                      
Crear Examenes -1) ventana para que el usuario pueda cargar un examen.

CrearExamenUsuario.java :
                          Aqui el usuario puede cargar a la base de datos su examen. el examen debe estar en un archivo .txt de bloc de notas Pasos a seguir.
                          1) Elegir el archivo.txt  donde estan las preguntas  opciones de respuestas con un JFileChooser.
                          2) lo mismo que punto 1 pero para las respuestas correctas.
                          3) Pinchar sobre el boton cargarDB.
                          El programa leera los archivos lo editara para preperar lo para la base de datos .
                          Identificando  la pregunta , respuesta opcion A , B , C , D de cada linea. 
                          Comprueba que cada pregunta empiece por un numero y  las respuestas  por A ,B , C , D.
                          si todo ha ido bien lo cargara a la base de datos cada parte en su columna
                          Lo mismo con las respuesta correctas: numero y letra de a respuesta correcta.
                          Si algo no ha ido bien el programa informara al usuario y creara una copia de cada archivo en el mismo directorio
                          con el  texto editado para que el usuario revise el texto y un informe  con la liena donde esta el error y el tipo 
                          de error. 
                          Mediante una clase interna que herreda de SwingWorker en un segundo hilo se hace uso de un 
                          En un  ProgresBar que  indicara al usuario el progreso de la operacion un conjunto de 8 funciones 
                          que se iran llamando en su orden correspondiente en un textArea se informa que proceso ha finalizado.
                          Si todo ha ido bien  se informa al cliente que todo ha ido bien  y se muestra el total de preguntas que contiene 
                          la tabla de preguntas y la tabla de respuestas correctas que deben ser iguales.
       
ExamenUsuario.java:
                  ventana donde el usuario pueda hacer el examen que ha cargado en CrearExamenUsuario.java.Este examen cambiara cada
                  vez que el usuario halla cargado un examen nuevo en la base de datos.
                  para contestar tiene 4 radio buttons, si falla aparecera en la parte inferior la pregunta y la respuesta fallada
                  Tambien se actiara el boton Examen recuperacion apartir del primer fallo donde el usuario puede volver a contestar
                  las preguntas falladas. Tambien esta la opcion de elegir una pregunta desde donde partir.
               
ExamenUsuarioRecuperacion.java :
                                  Apartir de que el usuario haya fallado una pregunta se activara la opcion de ir a esta ventana.
                                  Todas las preguntas falladas seran guardadas hasta que el usuario pinche sobre el boton recuperacion
                                  entonces podra volver a contestar a las preguntas falladas.
                                  
ayuda.java :
            una ventana con un texto  de informacion sobre como realizar el examen se accede desde un menu en el examen.
            
Examen1.java : 
              Examen creado por el programador. Este es un examen fijo si el programador quiere cargar otro examen debe crear
              otro submenu.
            
            
ExamenRecuperacion.java : 
                          Igual que ExamenUsuarioRecuperacion.java 
                          
ConexionBBDDExamen1.java: 
                          Aqui estan todas las funciones para el programador   -conectar a la DB
                                                                              - Crear tabla
                                                                              - eliminar tabla
                                                                              - cargar tabla
                                                                              - editar archivo.txt
                                                                              - hacer copia de archivo
                                                                                 etc etc
ConexionBBDDExamenUsuario.java : 
                                  Lo mismo que ConexionBBDDExamen1.java:  con la diferencia del JFileChooser
                                  
 Creado con Ide Eclipse   Base de datos sqlite  con el archivo dentro del proyecto : Proyecto_CrearExamen-master\ExamenUsuario.db   
 
                                  
                                  
                                                                              
