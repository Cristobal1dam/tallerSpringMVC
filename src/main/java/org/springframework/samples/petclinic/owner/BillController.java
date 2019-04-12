package org.springframework.samples.petclinic.owner;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bills")
public class BillController {
	
	@Autowired
	BillService billService;
	
	@Autowired
	VisitService visitService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> getAll(){
		List<Bill> response = billService.findAll();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> findOne(@PathVariable Integer id){
		Bill response = billService.findById(id);
		
		//Comprobamos que el registro a devolver existe
		if(response == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La factura no existe");
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> save(@RequestBody @Valid Bill bill){
		
		//Comprobamos que el registro a guardar no tiene id
		if(bill == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("La factura que trata de añadir no existe");
		}else
		{
		
		//Comprobamos que el registro a guardar no es null
		if(bill.getId() != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(null);
		}else  {		
		billService.save(bill);		
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(bill);
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable Integer id){
		//Comprobamos que existe el registro a eliminar buscando por Id
		Bill entrada = billService.findById(id);
		//Si no encontramos registro devolvemos error
		if (entrada==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La factura a borrar no existe");
		}else {			
			billService.deleteById(id);			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> edit(@RequestBody @Valid Bill bill){
		
		//Comprobamos que existe el registro a actualizar buscando por Id
		Bill entrada = billService.findById(bill.getId());
		//Si no encontramos registro devolvemos error
		if (entrada==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La factura a editar no existe");
		}else {			
			Bill response = billService.save(entrada);			
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);			
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, params = {"filter"})
	public ResponseEntity<Object> findByPaid(@RequestParam("filter") String filtro){
		
		//Devolvemos los registros pagados si el filtro coincide
		if(filtro.equalsIgnoreCase("pagada")) {
			List<Bill> response = billService.findByVisitNotNull();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		}
		//Devolvemos los registros no pagados si el filtro coincide
		if(filtro.equalsIgnoreCase("no_pagada")) {
			List<Bill> response = billService.findByVisitNull();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		//Devolvemos un error si pasan cualquier otro parametro	
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("Los parametros no son validos");
		}
	}
	
	@RequestMapping(value = "/{idBill}/visit/{idVisit}", method = RequestMethod.GET)
	public ResponseEntity<Object> visitDetail(@PathVariable("idBill") Integer idBill, @PathVariable("idVisit") Integer idVisit){
		Bill bill = billService.findById(idBill);
		
		//Comprobamos que la factura existe
		if(bill == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La factura no existe");
		}
		
		Visit visit = visitService.findById(idVisit);
		
		//Comprobamos que la visita existe
		if(visit == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La visita no existe");
		}
		
		//Comprobamos que la visita de la factura coincide con la visita a buscar
		if(!bill.getVisit().equals(visit)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("La visita a buscar no coincide con la visita de la factura");
		}else {
			
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(visit);
		}
		
		
	}
	
	@RequestMapping(value = "/{idBill}/visit/{idVisit}", method = RequestMethod.PUT)
	public ResponseEntity<Object> addVisitToBill(@PathVariable("idBill") Integer idBill, @PathVariable("idVisit") Integer idVisit){
		Bill bill = billService.findById(idBill);
		
		//Comprobamos que la factura existe
		if(bill == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La factura no existe");
		}
		
		Visit visit = visitService.findById(idVisit);
		
		//Comprobamos que la visita existe
		if(visit == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("La visita no existe");
		}
		
		//Comprobamos si la visita de la factura coincide con la visita a asignar
		if(bill.getVisit() != null) {
		if(bill.getVisit().equals(visit)) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("La visita ya esta asignada a esta factura");
		}
		
		//Asignamos la visita a la factura y la actualizamos
		bill.setVisit(visit);
		
		Bill billUpdated = billService.save(bill);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(billUpdated);
		
		
	}
	
	
	
	@RequestMapping(value = "/visits",method = RequestMethod.GET, params = {"filter"})
	public ResponseEntity<Object> findVisitByPaid(@RequestParam("filter") String filtro){
		
		//Devolvemos los registros pagados si el filtro coincide
		if(filtro.equalsIgnoreCase("pagada")) {
			List<Visit> response = visitService.findByBillNotNull();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		}
		//Devolvemos los registros no pagados si el filtro coincide
		if(filtro.equalsIgnoreCase("no_pagada")) {
			List<Visit> response = visitService.findByBillNull();
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		//Devolvemos un error si pasan cualquier otro parametro	
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("Los parametros no son validos");
		}
	}
	

}
