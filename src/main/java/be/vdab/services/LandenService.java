package be.vdab.services;

import java.util.List;

import be.vdab.dao.LandenDAO;
import be.vdab.entities.Land;

public class LandenService {
	private final LandenDAO landenDAO = new LandenDAO();

	public List<Land> findLanden() {
		return landenDAO.findLanden();
	}

	public Land findLandById(long id) {
		return landenDAO.read(id);
	}
}
