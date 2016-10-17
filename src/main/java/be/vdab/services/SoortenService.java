package be.vdab.services;

import be.vdab.dao.SoortenDAO;
import be.vdab.entities.Soort;

public class SoortenService {
	private final SoortenDAO soortenDAO = new SoortenDAO();

	public Soort findSoortById(long id) {
		return soortenDAO.read(id);
	}
}