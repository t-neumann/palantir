package at.ac.imp.palantir.util;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.Gene;

public class LazyGeneDataModel extends LazyDataModel<Gene> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManager em;
	
	private int referenceId;

	public LazyGeneDataModel(EntityManager em, int referenceId) {
		this.em = em;
		this.referenceId = referenceId;
	}
	
	@Override
	public Gene getRowData(String rowKey) {
		
		@SuppressWarnings("unchecked")
		List<Gene> list = (List<Gene>)getWrappedData();
		
		Gene result = null;
		for (Gene gene : list) {
			if (gene.getId() == Integer.parseInt(rowKey)) {
				result = gene;
			}
		}
		return result;
	}

	@Override
	public Object getRowKey(Gene object) {
		System.out.println("Gene id " + object.getId());
		return object != null ? object.getId() : null;
	}

	@Override
	public List<Gene> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			System.out.println(filter.getKey());
			System.out.println(filter.getValue());
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		Root<Gene> gene = cqCount.from(Gene.class);
		// Join<ExpressionValue, Gene> expressionGene =
		// expressionValue.join(expressionValueMetaModel.getSingularAttribute("gene",
		// Gene.class));

		// Predicate p = getFilterCondition(cb, expressionGene, filters);

		Predicate p = getFilterCondition(cb, gene, filters);
		
		Path<?> idFilter = gene;
		Path<Integer> idQuery = idFilter.get("reference").get("id");
		p = cb.and(p, cb.equal(idQuery, this.referenceId));

		cqCount.select(cb.count(gene)).where(p);

		int count = em.createQuery(cqCount).getSingleResult().intValue();

		CriteriaQuery<Gene> cq = cb.createQuery(Gene.class);
		gene = cq.from(Gene.class);
		//expressionGene = expressionValue.join(expressionValueMetaModel.getSingularAttribute("gene", Gene.class));

		cq.select(gene).where(p);

		this.setRowCount(count);

		if (sortField == null) {
			sortField = "geneSymbol";
		}
		
		if (sortOrder == SortOrder.ASCENDING) {
			cq.orderBy(cb.asc(gene.get(sortField)));
		} else if (sortOrder == SortOrder.DESCENDING) {
			cq.orderBy(cb.desc(gene.get(sortField)));
		}
		
		List<Gene> result = em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();

		return result;
	}

	public int count(Map<String, Object> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ExpressionValue> myObj = cq.from(ExpressionValue.class);
		// cq.where(experimentFacade.getFilterCondition(cb, myObj, filters));
		cq.select(cb.count(myObj));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	@SuppressWarnings("unchecked")
	public Predicate getFilterCondition(CriteriaBuilder cb, Root<Gene> myObj, Map<String, Object> filters) {
		Predicate filterCondition = cb.conjunction();
		String wildCard = "%";
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			String value = wildCard + filter.getValue() + wildCard;
			if (!filter.getValue().equals("")) {
				Path<?> p = myObj;
				String[] fields = filter.getKey().split("\\.");
				for (String field : fields) {
					p = p.get(field);
				}
				filterCondition = cb.and(filterCondition, cb.like((Path<String>)p, value));
			}
		}
		return filterCondition;
	}

}
