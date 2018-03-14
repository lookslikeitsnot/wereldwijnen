package be.vdab.valueobjects;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import be.vdab.services.WijnService;

public class Mandje {
	private Map<Long, Integer> mandje;

	public Mandje() {
		mandje = new LinkedHashMap<>();
	}

	public Map<Long, Integer> getMandje() {
		return mandje;
	}

	public void add(long wijnId, int aantal) {
		mandje.put(wijnId, aantal);
	}

	public BigDecimal getPrijs(WijnService wijnService) {
		return toBestelbonlijnen(wijnService).stream().map(lijn->lijn.getPrijs()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Set<Bestelbonlijn> toBestelbonlijnen(WijnService wijnService) {
		Set<Bestelbonlijn> bestelbonlijnen = new LinkedHashSet<>();
		mandje.forEach((wijnId, aantal) -> wijnService.find(wijnId)
				.ifPresent(wijn -> bestelbonlijnen.add(new Bestelbonlijn(wijn, aantal))));
		return bestelbonlijnen;
	}

	public boolean isEmpty() {
		return mandje.isEmpty();
	}
}
