package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="landen")
public class Land implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String naam;
	@Version
	private int versie;
}
