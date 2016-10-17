package be.vdab.valueobjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.vdab.entities.Bestelbon;
import be.vdab.entities.Wijn;
import be.vdab.enums.Bestelwijze;
import be.vdab.services.WijnenService;

public class Mandje implements Serializable {
	private static final long serialVersionUID = 1L;

	Map<Long, Integer> mandje;

	public Mandje() {
		mandje = new HashMap<>();
	}

	public Mandje(long wijnId, int aantalFlessen) {
		mandje = new HashMap<>();
		mandje.put(wijnId, aantalFlessen);
	}

	public int getAantalFlessenAlInMandje(long wijnId) {
		return mandje.containsKey(wijnId) ? mandje.get(wijnId) : 0;
	}

	public boolean isLeeg() {
		return mandje.isEmpty();
	}

	public void addBestelLijn(long wijnId, int aantalFlessen) {
		mandje.put(wijnId, aantalFlessen);
	}

	public void removeLijn(long wijnId) {
		mandje.remove(wijnId);
	}

	public Set<BestelbonLijn> vanMandjeNaarBestelbonLijnen() {
		Set<BestelbonLijn> bestelbonlijnen = new HashSet<>();
		WijnenService wijnenService = new WijnenService();

		List<Wijn> wijnen = wijnenService.findByMultipleIds(mandje.keySet());

		for (Wijn wijn : wijnen) {
			bestelbonlijnen.add(new BestelbonLijn(wijn, mandje.get(wijn.getId())));
		}

		return bestelbonlijnen;
	}

	public Set<BestelbonLijn> vanMandjeNaarBestelbonLijnenMetLock() {
		Set<BestelbonLijn> bestelbonlijnen = new HashSet<>();
		WijnenService wijnenService = new WijnenService();

		List<Wijn> wijnen = wijnenService.findByMultipleIdsWithLock(mandje.keySet());

		for (Wijn wijn : wijnen) {
			bestelbonlijnen.add(new BestelbonLijn(wijn, mandje.get(wijn.getId())));
		}

		return bestelbonlijnen;
	}

	public Bestelbon vanMandjeNaarBestelBonZonderGegevens() {
		return new Bestelbon(vanMandjeNaarBestelbonLijnen());
	}

	public Bestelbon vanMandjeNaarBestelbonMetGegevens(String naam, String straat, String huisNr, String postCode,
			String gemeente, Bestelwijze bestelwijze) {
		return new Bestelbon(naam, new Adres(straat, huisNr, postCode, gemeente), bestelwijze);
	}
}