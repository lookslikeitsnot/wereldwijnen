package be.vdab.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import be.vdab.entities.Wijn;

@Embeddable
public class Bestelbonlijn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wijnid")
	private Wijn wijn;
	private int aantal;
	private BigDecimal prijs;
	
	public Bestelbonlijn() {}
	
	public Bestelbonlijn(Wijn wijn, int aantal) {
		this.wijn = wijn;
		this.aantal = aantal;
		this.prijs = wijn.getPrijs().multiply(BigDecimal.valueOf(aantal));
	}

	public Wijn getWijn() {
		return wijn;
	}

	public int getAantal() {
		return aantal;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wijn == null) ? 0 : wijn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Bestelbonlijn))
			return false;
		Bestelbonlijn other = (Bestelbonlijn) obj;
		if (wijn == null) {
			if (other.wijn != null)
				return false;
		} else if (!wijn.equals(other.wijn))
			return false;
		return true;
	}
}
