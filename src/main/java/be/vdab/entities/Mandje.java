package be.vdab.entities;

import java.util.Set;

import be.vdab.valueobjects.Bestelbonlijn;

public class Mandje {
	private Set<Bestelbonlijn> mandje;

	public Set<Bestelbonlijn> getMandje() {
		return mandje;
	}
	
	public void add(Bestelbonlijn bestelbonlijn) {
		mandje.add(bestelbonlijn);
	}
}
