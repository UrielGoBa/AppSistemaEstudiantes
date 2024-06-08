package ug.estudiantes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.estudiantes.modelo.Estudiante;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {
}