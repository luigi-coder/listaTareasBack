package pe.todotic.tareas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pe.todotic.tareas.model.Tarea;
import pe.todotic.tareas.repo.TareaRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tareas")
public class TareaController {

    // Injection
    @Autowired
    private TareaRepository tareaRepository;

    // Todas las tareas de la base de datos
    @GetMapping("")
    List<Tarea> index(){
        return tareaRepository.findAll();
    }

    // Una nueva tarea
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Tarea create(@RequestBody Tarea tarea){
        return tareaRepository.save(tarea);
    }

    // Actualizar las tareas
    @PutMapping("{id}")
    Tarea update(@PathVariable String id, @RequestBody Tarea tarea){
        Tarea tareaFromDb = tareaRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        tareaFromDb.setNombre(tarea.getNombre());
        tareaFromDb.setCompletado(tarea.isCompletado());

        // Actualizamos la tarea en la db
        return tareaRepository.save(tareaFromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void delete(@PathVariable String id){

        Tarea tarea = tareaRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        tareaRepository.delete(tarea);
    }

}
