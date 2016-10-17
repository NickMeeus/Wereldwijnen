package be.vdab.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

import be.vdab.entities.Wijn;

public class WijnenDAO extends AbstractDAO{
	
	public Wijn read(long id){ 
		return getEntityManager().find(Wijn.class, id);
	}

	
	public List<Wijn> findByMultipleIds(Set<Long> ids){
		return getEntityManager().createNamedQuery(Wijn.FIND_BY_MULTIPLE_IDS, Wijn.class)
				.setParameter("ids", ids)
				.getResultList();
	}
	
	public List<Wijn> findByMultipleIdsWithLock(Set<Long> ids){
		return getEntityManager().createNamedQuery(Wijn.FIND_BY_MULTIPLE_IDS, Wijn.class)
				.setLockMode(LockModeType.PESSIMISTIC_READ)
				.setParameter("ids", ids)
				.getResultList();
	} 
}