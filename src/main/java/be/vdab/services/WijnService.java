package be.vdab.services;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Wijn;
import be.vdab.repositories.WijnRepository;

public class WijnService extends AbstractService {
	private final WijnRepository wijnRepository = new WijnRepository();
	
	public List<Wijn> findBySoort(long soortId){
		return wijnRepository.findBySoort(soortId);
	}
	
	public Optional<Wijn> find(long wijnId){
		return wijnRepository.find(wijnId);
	}
}
