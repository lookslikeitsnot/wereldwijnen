package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="landen")
public class Land implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	
	
}
