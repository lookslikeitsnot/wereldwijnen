package be.vdab.valueobjects;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public class Mandje {
	private Set<Bestelbonlijn> mandje;
	public Mandje() {
		mandje = new LinkedHashSet<>();
	}

	public Set<Bestelbonlijn> getMandje() {
		return mandje;
	}
	
	public void add(Bestelbonlijn bestelbonlijn) {
		if(mandje.contains(bestelbonlijn)) {
			mandje.remove(bestelbonlijn);
		}
		mandje.add(bestelbonlijn);
	}
	
	public BigDecimal getPrijs() {
		return mandje.stream().map(lijn->lijn.getPrijs()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public boolean isEmpty() {
		return mandje.isEmpty();
	}
}
