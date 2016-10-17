package be.vdab.entities;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import be.vdab.enums.Bestelwijze;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.BestelbonLijn;

@Entity
@Table(name = "bestelbonnen")
public class Bestelbon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	private String naam;

	@Temporal(TemporalType.DATE)
	Date besteld;

	@Embedded
	private Adres adres;

	private Bestelwijze bestelwijze;

	@ElementCollection
	@CollectionTable(name = "bestelbonlijnen", joinColumns = @JoinColumn(name = "bonid"))
	private Set<BestelbonLijn> bestelbonLijnen;

	
	public Bestelbon(String naam, Adres adres, Bestelwijze bestelwijze) {
		this.naam = naam;
		this.adres = adres;
		this.bestelwijze = bestelwijze;
		besteld = new Date();
	}

	public Bestelbon(Set<BestelbonLijn> bestelbonLijnen) {
		this.bestelbonLijnen = bestelbonLijnen;
		besteld = new Date();
	}

	public Bestelbon() {
	}

	
	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Adres getAdres() {
		return adres;
	}

	public Bestelwijze getBestelwijze() {
		return bestelwijze;
	}

	public Set<BestelbonLijn> getBestelbonLijnen() {
		return Collections.unmodifiableSet(bestelbonLijnen);
	}

	public BigDecimal getTotaalTeBetalen() {
		BigDecimal totaal = BigDecimal.ZERO;
		for (BestelbonLijn bestelbonLijn : bestelbonLijnen) {
			totaal = totaal.add(bestelbonLijn.getTotaalLijn());
		}
		return totaal;
	}

	public boolean isKlaar() {
		return (naam != null) && !naam.isEmpty() && (adres != null) && adres.isKlaar() && (bestelwijze != null)
				&& bestelbonLijnen != null && !bestelbonLijnen.isEmpty();
	}

	public void setBestelbonLijnen(Set<BestelbonLijn> bestelbonLijnen) {
		this.bestelbonLijnen = bestelbonLijnen;
	}
}