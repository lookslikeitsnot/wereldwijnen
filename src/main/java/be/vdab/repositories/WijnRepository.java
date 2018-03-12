package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Wijn;

public class WijnRepository extends AbstractRepository {
	public List<Wijn> findBySoort(long soortId){
		return getEntityManager()
				.createNamedQuery("Wijn.findBySoort", Wijn.class)
				.setParameter("soortId", soortId)
				.getResultList();
	}
	
	public Optional<Wijn> find(long wijnId){
		return Optional.ofNullable(getEntityManager().find(Wijn.class, wijnId));
	}
	
	public void nieuweBestelling(long id, int aantal) {
		getEntityManager()
			.createNamedQuery("Wijn.updateBestelling")
			.setParameter("id", id)
			.setParameter("aantal", aantal)
			.executeUpdate();
	}
}
