package be.vdab.entities;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "soorten")
public class Soort {
	
	@Id
	@GeneratedValue
	long id;
	
	private String naam;

	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "landid")
	private Land land;
	
	@OneToMany(mappedBy = "soort")
	@OrderBy("jaar")
	private Set<Wijn> wijnen;
	
	
	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Land getLand() {
		return land;
	}

	@Override
	public int hashCode() {
		return naam.hashCode() + (int)land.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Soort)){
			return false;
		}
		Soort soort = (Soort) obj;
		
		return this.naam.equals(soort.getNaam()) && (this.land.getId() == land.getId());
	}

	public Set<Wijn> getWijnen() {
		return Collections.unmodifiableSet(wijnen);
	}
}