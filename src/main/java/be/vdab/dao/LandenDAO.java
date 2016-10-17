package be.vdab.dao;

import java.util.List;

import be.vdab.entities.Land;

public class LandenDAO extends AbstractDAO {

	public List<Land> findLanden() {
		return getEntityManager().createNamedQuery("Land.findAll", Land.class).getResultList();
	}

	public Land read(long id) {
		return getEntityManager().find(Land.class, id);
	}
}