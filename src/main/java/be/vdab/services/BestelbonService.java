package be.vdab.services;

import javax.persistence.PersistenceException;

import be.vdab.entities.Bestelbon;
import be.vdab.repositories.BestelbonRepository;

public class BestelbonService extends AbstractService {
	private final BestelbonRepository bestelbonRepository = new BestelbonRepository();
	public void create(Bestelbon bestelbon) {
		beginTransaction();
		try {
			bestelbonRepository.create(bestelbon);
			commit();
		} catch (PersistenceException ex) {
			rollback();
			throw ex;
		}
	}
}
