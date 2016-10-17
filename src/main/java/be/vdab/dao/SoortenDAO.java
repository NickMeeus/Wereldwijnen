package be.vdab.dao;

import be.vdab.entities.Soort;

public class SoortenDAO extends AbstractDAO {
	public Soort read(long id) {
		return getEntityManager().find(Soort.class, id);
	}
}