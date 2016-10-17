package be.vdab.entities;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "landen")
public class Land {

	@Id
	@GeneratedValue
	long id;

	private String naam;

	@OneToMany(mappedBy = "land")
	@OrderBy("naam")
	private Set<Soort> soorten;
	
	
	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Set<Soort> getSoorten() {
		return Collections.unmodifiableSet(soorten);
	}

}