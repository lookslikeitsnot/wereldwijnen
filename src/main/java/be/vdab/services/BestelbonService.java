package be.vdab.services;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import be.vdab.entities.Bestelbon;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.repositories.BestelbonRepository;
import be.vdab.repositories.WijnRepository;
import be.vdab.valueobjects.Bestelbonlijn;

public class BestelbonService extends AbstractService {
	private final BestelbonRepository bestelbonRepository = new BestelbonRepository();
	private final WijnRepository wijnRepository = new WijnRepository();

	public void create(Bestelbon bestelbon) {
		beginTransaction();
		try {
			bestelbonRepository.create(bestelbon);
			for (Bestelbonlijn bestelbonlijn : bestelbon.getBestelbonlijnen()) {
				wijnRepository.nieuweBestelling(bestelbonlijn.getWijn().getId(), bestelbonlijn.getAantal());
			}
			commit();
		} catch (RollbackException ex) {
			if (ex.getCause() instanceof OptimisticLockException) {
				throw new RecordAangepastException();
			}
		} catch (PersistenceException ex) {
			rollback();
			throw ex;
		}
	}
}
