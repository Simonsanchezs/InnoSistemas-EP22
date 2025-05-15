    package com.example.InnoSistemas.controller;

    import com.example.InnoSistemas.entity.Equipo;
    import com.example.InnoSistemas.service.EquipoService;
    import org.springframework.graphql.data.method.annotation.Argument;
    import org.springframework.graphql.data.method.annotation.MutationMapping;
    import org.springframework.graphql.data.method.annotation.QueryMapping;
    import org.springframework.stereotype.Controller;

    import java.util.List;

    @Controller
    public class EquipoController {

        private final EquipoService equipoService;

        public EquipoController(EquipoService equipoService) {
            this.equipoService = equipoService;
        }

        @QueryMapping
        public List<Equipo> equipos() {
            return equipoService.findAll();
        }

        @QueryMapping
        public Equipo equipo(@Argument int id) {
            return equipoService.findById(id);
        }

        @QueryMapping
        public List<Equipo> equiposPorCurso(@Argument int cursoId) {
            return equipoService.findByCursoId(cursoId);
        }

        @MutationMapping
        public Equipo crearEquipo(@Argument Equipo input, @Argument int cursoId) {
            Equipo equipo = new Equipo();
            equipo.setNombre(input.getNombre());
            return equipoService.save(equipo, cursoId);
        }

        @MutationMapping
        public Equipo actualizarEquipo(@Argument int id, @Argument Equipo input) {
            Equipo equipo = new Equipo();
            equipo.setNombre(input.getNombre());
            return equipoService.update(id, equipo);
        }

        @MutationMapping
        public Boolean eliminarEquipo(@Argument int id) {
            equipoService.deleteById(id);
            return true;
        }
    }
