package be.vdab.services;

import java.util.List;
import java.util.Set;

import be.vdab.dao.WijnenDAO;
import be.vdab.entities.Wijn;

public class WijnenService {
	private final WijnenDAO wijnenDAO = new WijnenDAO();

	public Wijn findWijnById(long id) {
		return wijnenDAO.read(id);
	}

	public List<Wijn> findByMultipleIds(Set<Long> ids) {
		return wijnenDAO.findByMultipleIds(ids);
	}

	public List<Wijn> findByMultipleIdsWithLock(Set<Long> ids) {
		return wijnenDAO.findByMultipleIdsWithLock(ids);
	}
}
