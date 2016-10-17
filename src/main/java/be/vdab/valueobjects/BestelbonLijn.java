package be.vdab.valueobjects;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.vdab.entities.Wijn;

@Embeddable
public class BestelbonLijn {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wijnid")
	private Wijn wijn;

	private long aantal;

	
	public BestelbonLijn() {
	}

	public BestelbonLijn(Wijn wijn, long aantal) {
		this.wijn = wijn;
		this.aantal = aantal;
		wijn.addBestelling(aantal);
	}

	
	public Wijn getWijn() {
		return wijn;
	}

	public long getAantal() {
		return aantal;
	}

	public BigDecimal getTotaalLijn() {
		return wijn.getPrijs().multiply(BigDecimal.valueOf(aantal));
	}

	
	@Override
	public int hashCode() {
		return (int) wijn.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BestelbonLijn)) {
			return false;
		}
		BestelbonLijn bestelbonLijn = (BestelbonLijn) obj;

		return this.wijn.getId() == bestelbonLijn.getWijn().getId();
	}
}