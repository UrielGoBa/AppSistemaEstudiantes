package ug.estudiantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ug.estudiantes.modelo.Estudiante;
import ug.estudiantes.servicio.EstudianteServicio;

import java.util.Scanner;
import java.util.List;

@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicio estudianteServicio;
	private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);
	public static String nl = System.lineSeparator();


	public static void main(String[] args) {
		logger.info(nl + "Iniciando la aplicacion... " + nl);

		//Levanta la fabrica de Spring con toda la inyeccion de dependencias vvv

		SpringApplication.run(EstudiantesApplication.class, args);

		logger.info(nl + "Aplicación finalizada... " + nl);
	}


	@Override
	public void run(String... args) throws Exception {

		logger.info(nl + "Ejecutando metodo run de spring..." + nl);
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			mostrarMenu();
			salir = ejecutarOpciones(consola);
			logger.info(nl);
		}//fin while

	}

	private void mostrarMenu() {
		logger.info(nl);
		logger.info("""
					***Sistema de Estudiantes***
					1. Listar Estudiante
					2. Buscar Estudiante
					3. Agregar Estudiante
					4. Modificar Estudiate
					5. Eliminar Estudiante
					6. Salir
					Elige una opcion:
				""");
	}

	private boolean ejecutarOpciones(Scanner consola) {
		var opcion = Integer.parseInt(consola.nextLine());
		var salir = false;
		switch (opcion) {
			case 1 -> {//Listar estudiantes
				logger.info(nl + "Listado de Estudiantes: " + nl);
				List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
				estudiantes.forEach((estudiante -> logger.info(estudiante.toString() + nl)));
			}
			case 2 -> {//Buscar Estudiante por Id
				logger.info("Introduce el id del estudiante a buscar: ");
				var idEstudiante =  Integer.parseInt(consola.nextLine());
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
				if(estudiante != null){
					logger.info(nl + "Estudiante encontrado: " + estudiante.toString() + nl);
				}else{
					logger.info(nl + "Estudiante no encontrado" + nl);
				};
			}
			case 3 ->{//Agregar estudiante
				logger.info("Agregar estudiante nuevo: " + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info(nl + "Apellido:");
				var apellido = consola.nextLine();
				logger.info(nl + "Telefono: ");
				var telefono = consola.nextLine();
				logger.info(nl + "Email: ");
				var email = consola.nextLine();
				var estudiante = new Estudiante();
				estudiante.setNombre(nombre);
				estudiante.setApellido(apellido);
				estudiante.setTelefono(telefono);
				estudiante.setEmail(email);
				estudianteServicio.guardarEstudiante(estudiante);
				logger.info(nl + "Estudiante agregado");
			}
			case 4 ->{//Modificar Estudiante

				logger.info("Modificar Estudiante" + nl);

				logger.info("Ingrese el Id Estudiante: ");
				var idEstudiante = Integer.parseInt(consola.nextLine());
				//Buscamos el estudiante a modificar
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
				if(estudiante !=null){
					logger.info(nl + "Nombre: ");
					var nombre = consola.nextLine();
					logger.info(nl + "Apellido:");
					var apellido = consola.nextLine();
					logger.info(nl + "Telefono: ");
					var telefono = consola.nextLine();
					logger.info(nl + "Email: ");
					var email = consola.nextLine();
					estudiante.setNombre(nombre);
					estudiante.setApellido(apellido);
					estudiante.setTelefono(telefono);
					estudiante.setEmail(email);
					estudianteServicio.guardarEstudiante(estudiante);
					logger.info(nl + "Estudiante Modificado" + estudiante.toString());
				}else{
					logger.info(nl + "Estudiante NO encontrado...");
				}
			}
			case 5 ->{//Eliminar Estudiante
				logger.info("Eliminar Estudiante: " + nl);
				logger.info("Id Estudiante a elimnar: ");
				var idEstudiante = Integer.parseInt(consola.nextLine());
				//Buscar el id a eliminar
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
				if(estudiante!=null){
					estudianteServicio.eliminarEstudiante(estudiante);
					logger.info("Estudiante ELIMINADO...");
				}else{
					logger.info("Estudiante NO encontrado...");
				}

			}
			case 6 ->{//Salir
				logger.info("Hasta pronto! " + nl + nl + nl);
				salir = true;
			}
			default -> {
				logger.info("OPCION NO RECONOCIDA..." + nl + nl + nl);
			}
		}//fin switch
		return salir;
	}
}

