package be.vdab.services;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Soort;
import be.vdab.repositories.SoortRepository;

public class SoortService {
	private final SoortRepository soortRepository = new SoortRepository();
	public List<Soort> findByLand(long landId){
		return soortRepository.findByLand(landId);
	}
	
	public Optional<Soort> find(long id){
		return soortRepository.find(id);
	}
}
