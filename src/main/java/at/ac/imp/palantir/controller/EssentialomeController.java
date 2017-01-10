package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.model.Essentialome;
import at.ac.imp.palantir.model.EssentialomeDatapoint;
import at.ac.imp.palantir.model.EssentialomeEntry;
import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.ScreenGene;

@Named("EssentialomeController")
@ViewScoped
public class EssentialomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578116705244021926L;

	private Collection<ScreenGene> genes;
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		System.out.println(flash.size());
		for (String key : flash.keySet()) {
			System.out.println(key);
			System.out.println(flash.get(key));
		}
		int essentialomeId = (Integer) flash.get("essentialomeId");
				
		try {
			
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<ScreenGene>criteriaQuery = criteriaBuilder.createQuery(ScreenGene.class);
			Metamodel metamodel = em.getMetamodel();
			EntityType<ScreenGene> screenGeneType = metamodel.entity(ScreenGene.class);
			EntityType<Essentialome> essentialomeType = metamodel.entity(Essentialome.class);
			
			Root<ScreenGene> root = criteriaQuery.from(screenGeneType);
			Join<ScreenGene, Essentialome> join = root.join(screenGeneType.getSingularAttribute("essentialome",Essentialome.class), JoinType.INNER);
			
			ParameterExpression<Integer> parameterExpression=criteriaBuilder.parameter(Integer.class);
			criteriaQuery.where(criteriaBuilder.equal(join.get(essentialomeType.getSingularAttribute("id", Integer.class)), parameterExpression));

			TypedQuery<ScreenGene> typedQuery = em.createQuery(criteriaQuery).setParameter(parameterExpression, essentialomeId);
			this.genes = typedQuery.getResultList();
			
			//this.genes = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<ScreenGene> getGenes() {
		return genes;
	}

	public void setGenes(Collection<ScreenGene> genes) {
		this.genes = genes;
	}
}
