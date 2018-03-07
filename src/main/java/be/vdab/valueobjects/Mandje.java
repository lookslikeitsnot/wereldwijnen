package be.vdab.valueobjects;

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
		mandje.add(bestelbonlijn);
	}
}
