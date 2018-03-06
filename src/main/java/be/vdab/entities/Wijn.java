package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "wijnen")
public class Wijn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "soortid")
	private Soort soort;
	private short jaar;
	private short beoordeling;
	private BigDecimal prijs;
	private int inBestelling;
	@Version
	private int versie;
}
