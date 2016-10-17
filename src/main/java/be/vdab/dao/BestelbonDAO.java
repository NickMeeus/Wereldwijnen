package be.vdab.dao;

import be.vdab.entities.Bestelbon;

public class BestelbonDAO extends AbstractDAO {

	public void maakNieuweBestelBon(Bestelbon bestelBon) {
		getEntityManager().persist(bestelBon);

	}
}