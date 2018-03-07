package be.vdab.services;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Land;
import be.vdab.repositories.LandRepository;

public class LandService extends AbstractService {
	private final LandRepository landRepository = new LandRepository();

	public List<Land> findAll() {
		return landRepository.findAll();
	}

	public Optional<Land> find(long id) {
		return landRepository.find(id);
	}
}
