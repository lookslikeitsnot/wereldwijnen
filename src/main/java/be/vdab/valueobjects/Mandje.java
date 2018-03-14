package be.vdab.valueobjects;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import be.vdab.entities.Wijn;
import be.vdab.services.WijnService;

public class Mandje {
	// private Set<Bestelbonlijn> mandje;
	// public Mandje() {
	// mandje = new LinkedHashSet<>();
	// }
	//
	// public Set<Bestelbonlijn> getMandje() {
	// return mandje;
	// }
	//
	// public void add(Bestelbonlijn bestelbonlijn) {
	// if(mandje.contains(bestelbonlijn)) {
	// mandje.remove(bestelbonlijn);
	// }
	// mandje.add(bestelbonlijn);
	// }
	//
	// public BigDecimal getPrijs() {
	// return mandje.stream().map(lijn->lijn.getPrijs()).reduce(BigDecimal.ZERO,
	// BigDecimal::add);
	// }
	//
	// public boolean isEmpty() {
	// return mandje.isEmpty();
	// }

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
//		BigDecimal prijs = BigDecimal.ZERO;
//		for (Map.Entry<Long, Integer> artikelEnAantal : mandje.entrySet()) {
//			Optional<Wijn> optioneleWijn = wijnService.find(artikelEnAantal.getKey());
//			if (optioneleWijn.isPresent()) {
//				prijs = prijs
//						.add(optioneleWijn.get().getPrijs().multiply(BigDecimal.valueOf(artikelEnAantal.getValue())));
//			}
//		}
//		return prijs;
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
