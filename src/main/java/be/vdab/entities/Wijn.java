package be.vdab.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wijnen")
public class Wijn {

	@Id
	@GeneratedValue
	long id;

	private int jaar;
	private int beoordeling;
	private BigDecimal prijs;
	private long inBestelling;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "soortid")
	private Soort soort;

	public static final String FIND_BY_MULTIPLE_IDS = "Wijn.findByMultipleIds";

	
	public long getId() {
		return id;
	}

	public int getJaar() {
		return jaar;
	}

	public int getBeoordeling() {
		return beoordeling;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public long getInBestelling() {
		return inBestelling;
	}

	public Soort getSoort() {
		return soort;
	}

	public void addBestelling(long aantal) {
		inBestelling += aantal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jaar;
		result = prime * result + ((prijs == null) ? 0 : prijs.hashCode());
		result = prime * result + ((soort == null) ? 0 : soort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Wijn)) {
			return false;
		}
		Wijn wijn = (Wijn) obj;

		return (this.jaar == wijn.getJaar()) && (this.prijs.equals(wijn.getPrijs()))
				&& (this.soort.equals(wijn.getSoort()));
	}
}