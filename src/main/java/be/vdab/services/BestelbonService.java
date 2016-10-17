package be.vdab.services;

import be.vdab.dao.BestelbonDAO;
import be.vdab.entities.Bestelbon;
import be.vdab.valueobjects.Mandje;

public class BestelbonService {
	private final BestelbonDAO bestelbonDAO = new BestelbonDAO();

	public void maakNieuweBestelbonVanMandje(Bestelbon bestelbon, Mandje mandje) {
		bestelbonDAO.beginTransaction();
		bestelbon.setBestelbonLijnen(mandje.vanMandjeNaarBestelbonLijnenMetLock());
		
		if (bestelbon.isKlaar()) {
			bestelbonDAO.maakNieuweBestelBon(bestelbon);
		}
		bestelbonDAO.commit();
	}
}
