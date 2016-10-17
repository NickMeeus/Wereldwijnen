package be.vdab.valueobjects;

import javax.persistence.Embeddable;

@Embeddable
public class Adres {

	private String straat;
	private String huisnr;
	private String postCode;
	private String gemeente;

	public Adres() {
	}

	public Adres(String straat, String huisnr, String postCode, String gemeente) {
		this.straat = straat;
		this.huisnr = huisnr;
		this.postCode = postCode;
		this.gemeente = gemeente;
	}

	public String getStraat() {
		return straat;
	}

	public String getHuisnr() {
		return huisnr;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public boolean isKlaar() {
		return (straat != null) && (huisnr != null) && (postCode != null) && (gemeente != null) && !straat.isEmpty()
				&& !huisnr.isEmpty() && !postCode.isEmpty() && !gemeente.isEmpty();
	}
}