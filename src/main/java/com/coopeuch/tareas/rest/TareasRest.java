/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.rest;

import com.coopeuch.tareas.model.Tarea;
import com.coopeuch.tareas.model.response.Response;
import com.coopeuch.tareas.model.response.TareaResponse;
import com.coopeuch.tareas.model.response.TareasResponse;
import com.coopeuch.tareas.repository.TareaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Cristhiam Reina
 */
@Api(tags = "Tarea")
@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class TareasRest {
    
    @Autowired
    private TareaRepository repository;
    
    @ApiOperation("Guarda en base de datos una tarea")
    @ApiResponses(value = {
                    @ApiResponse(code = 400, message = "Los campos 'descripcion, fechaCreacion, vigente' no se encontraron en la peticion"),
                    @ApiResponse(code = 200, message = "La tarea se guardo correctamente") })
    @RequestMapping(method = RequestMethod.POST, value = "/tarea", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> tareaSave(@RequestBody Tarea tarea){
        Response response = new Response();
        String messageError = this.tareaValid(tarea, false);
        
        if(messageError.equalsIgnoreCase("")){
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("La tarea se guardo correctamente");
            repository.insert(tarea);
        } else {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(messageError);
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ApiOperation("Actualiza en base de datos una tarea")
    @ApiResponses(value = {
                    @ApiResponse(code = 400, message = "Los campos 'idTarea, descripcion, fechaCreacion, vigente' no se encontraron en la peticion"),
                    @ApiResponse(code = 404, message = "La tarea no se encontro"),
                    @ApiResponse(code = 200, message = "La tarea se actualizo correctamente")})
    @RequestMapping(method = RequestMethod.PUT, value = "/tarea", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> tareaUpdate(@RequestBody Tarea tarea){
        Response response = new Response();
        String messageError = this.tareaValid(tarea, true);
        
        if(!messageError.equalsIgnoreCase("")){
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(messageError);
        } else if (repository.selectOne(tarea.getIdTarea()).getIdTarea() == null){
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage("La tarea no se encontro");
        } else {
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("La tarea se actualizo correctamente");
            repository.update(tarea);
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation("Elimina en base de datos una tarea")
    @ApiResponses(value = {
                    @ApiResponse(code = 404, message = "La tarea no se encontro"),
                    @ApiResponse(code = 200, message = "La tarea se elimino correctamente")})
    @RequestMapping(method = RequestMethod.DELETE, value = "/tarea/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> tareaDelete(
            @ApiParam(name="id", value = "Identificador de la tarea.", required = true) 
            @PathVariable("id") Integer id){
        Response response = new Response();
        
        if (repository.selectOne(id).getIdTarea() == null){
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage("La tarea no se encontro");
        } else {
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("La tarea se elimino correctamente");
            repository.delete(id);
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation("Consulta las tareas que se encuentran en base de datos")
    @ApiResponses(value = {
                    @ApiResponse(code = 200, message = "Se consultaron las tareas correctamente")})
    @RequestMapping(method = RequestMethod.GET, value = "/tarea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TareasResponse> tareaGet(){
        TareasResponse tareasResponse = new TareasResponse();
        tareasResponse.setHttpStatus(HttpStatus.OK);
        tareasResponse.setResponseMessage("Se consultaron las tareas correctamente");
        tareasResponse.setTareas(repository.select());
        return new ResponseEntity<>(tareasResponse, tareasResponse.getHttpStatus());
    }
    
    @ApiOperation("Consulta una tarea que se encuentran en base de datos")
    @ApiResponses(value = {
                    @ApiResponse(code = 404, message = "La tarea no se encontro"),
                    @ApiResponse(code = 200, message = "Se consulto la tarea correctamente")})
    @RequestMapping(method = RequestMethod.GET, value = "/tarea/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TareaResponse> tareaGetOne(@PathVariable("id") Integer id){
        TareaResponse tareaResponse = new TareaResponse();
        
        Tarea tarea = repository.selectOne(id);
        
        if(tarea.getIdTarea() == null){
            tareaResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            tareaResponse.setResponseMessage("La tarea no se encontro");
        } else {
            tareaResponse.setHttpStatus(HttpStatus.OK);
            tareaResponse.setResponseMessage("Se consulto la tarea correctamente");
            tareaResponse.setTarea(tarea);
        }
        
        return new ResponseEntity<>(tareaResponse, tareaResponse.getHttpStatus());
    }
    
    public String tareaValid(Tarea tarea, Boolean update){
        StringBuilder messageValid = new StringBuilder();
        messageValid.append( (!update) ? "" : ( (tarea.getIdTarea() != null) ? "" : "idTarea" ) );
        messageValid.append(
                (tarea.getDescripcion() != null && !tarea.getDescripcion().equalsIgnoreCase("")) 
                        ? "" :  (messageValid.toString().equalsIgnoreCase("") ? "" : ", ").concat("descripcion"));
        messageValid.append((tarea.getFechaCreacion() != null) ? "" : (messageValid.toString().equalsIgnoreCase("") ? "" : ", ").concat("fechaCreacion"));
        messageValid.append((tarea.getVigente() != null) ? "" : (messageValid.toString().equalsIgnoreCase("") ? "" : ", ").concat("vigente"));
        return (messageValid.toString().equalsIgnoreCase("")) ? "" : "Los campos '".concat(messageValid.toString()).concat("' no se encontraron en la peticion");
    }
    
}
