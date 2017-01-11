package at.ac.imp.palantir.util;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import at.ac.imp.palantir.model.ExpressionValue;
import at.ac.imp.palantir.model.ExternalRNASeqDatapoint;
import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.ScreenGene;

public class LazyScreenGeneDataModel extends LazyDataModel<GenericGene> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManager em;
	
	private int essentialomeId;

	public LazyScreenGeneDataModel(EntityManager em, int essentialomeId) {
		this.em = em;
		this.essentialomeId = essentialomeId;
	}

	@Override
	public List<GenericGene> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			System.out.println(filter.getKey());
			System.out.println(filter.getValue());
		}
		
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<ScreenGene> query = cb.createQuery(ScreenGene.class);
//		Root<ScreenGene> root = query.from(ScreenGene.class);
//		root.fetch("datapoints", JoinType.INNER);
//		query.select(root).distinct(true);
//		
//		genes = em.createQuery(query).getResultList();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		Root<GenericGene> datapoint = cqCount.from(GenericGene.class);

		Predicate p = getFilterCondition(cb, datapoint, filters);
		
//		System.out.println("Result id: " + this.essentialomeId);
//		Path<?> idFilter = datapoint;
//		Path<Integer> idQuery = idFilter.get("entry").get("id");
//		p = cb.and(p, cb.equal(idQuery, this.essentialomeId));

		cqCount.select(cb.count(datapoint)).where(p);

		int count = em.createQuery(cqCount).getSingleResult().intValue();

		CriteriaQuery<GenericGene> cq = cb.createQuery(GenericGene.class);
		datapoint = cq.from(GenericGene.class);

		cq.select(datapoint).where(p);

		this.setRowCount(count);

		if (sortField == null) {
			sortField = "geneSymbol";
		}
		
		System.out.println("sortField = " + sortField);
		
		Path<?> sort = datapoint;
		String[] fields = sortField.split("\\.");
		for (String field : fields) {
			sort = sort.get(field);
		}

		sortField = subGeneRelation(sortField);
		if (sortOrder == SortOrder.ASCENDING) {
			cq.orderBy(cb.asc(sort));
		} else if (sortOrder == SortOrder.DESCENDING) {
			cq.orderBy(cb.desc(sort));
		}
		
		List<GenericGene> result = em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();

		return result;
	}

	private String subGeneRelation(String field) {
		if (field.startsWith("gene.")) {
			return field.replaceAll("^gene\\.", "");
		}
		return field;
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
	public Predicate getFilterCondition(CriteriaBuilder cb, Root<GenericGene> myObj, Map<String, Object> filters) {
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
