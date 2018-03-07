package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Land;

public class LandRepository extends AbstractRepository {
	public List<Land> findAll() {
		return getEntityManager().createNamedQuery("Land.findAll", Land.class).getResultList();
	}
	
	public Optional<Land> find(long id){
		return Optional.ofNullable(getEntityManager().find(Land.class, id));
	}
}
