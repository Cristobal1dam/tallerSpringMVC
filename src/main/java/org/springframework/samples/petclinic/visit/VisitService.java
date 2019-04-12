package org.springframework.samples.petclinic.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Bill;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
	@Autowired
	VisitRepository visitRepository;
	
	public Visit findById(int id) {
		return visitRepository.findOne(id);
	}
	
	public List<Visit> findAll(){
		return visitRepository.findAll();
	}
	
	public Visit save(Visit entity) {
		return visitRepository.save(entity);
	}
	
	public void deleteById(Integer id) {
		visitRepository.delete(id);
	}
	
	public Visit edit(Visit entity) {
		return visitRepository.save(entity);
	}
	
	public List<Visit> findByBillNotNull(){
		return visitRepository.getVisitByBillNotNull();
	}
	
	public List<Visit> findByBillNull(){
		return visitRepository.getVisitByBillNull();
	}


}
