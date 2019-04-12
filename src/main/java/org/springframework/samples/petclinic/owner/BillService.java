package org.springframework.samples.petclinic.owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
	
	@Autowired
	BillRepository billRepository;
	
	public Bill findById(int id) {
		return billRepository.findOne(id);
	}
	
	public List<Bill> findAll(){
		return billRepository.findAll();
	}
	
	public Bill save(Bill entity) {
		return billRepository.save(entity);
	}
	
	public void deleteById(Integer id) {
		billRepository.delete(id);
	}
	
	public Bill edit(Bill entity) {
		return billRepository.save(entity);
	}
	
	public List<Bill> findByVisitNotNull(){
		return billRepository.getBillByVisitNotNull();
	}
	
	public List<Bill> findByVisitNull(){
		return billRepository.getBillByVisitNull();
	}
	
	

}
