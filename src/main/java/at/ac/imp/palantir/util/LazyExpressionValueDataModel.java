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

public class LazyExpressionValueDataModel extends LazyDataModel<ExpressionValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManager em;
	
	private int resultId;

	public LazyExpressionValueDataModel(EntityManager em, int resultId) {
		this.em = em;
		this.resultId = resultId;
	}

	@Override
	public List<ExpressionValue> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			System.out.println(filter.getKey());
			System.out.println(filter.getValue());
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Metamodel m = em.getMetamodel();
		//ManagedType<ExpressionValue> expressionValueMetaModel = m.managedType(ExpressionValue.class);

		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		Root<ExpressionValue> expressionValue = cqCount.from(ExpressionValue.class);
		// Join<ExpressionValue, Gene> expressionGene =
		// expressionValue.join(expressionValueMetaModel.getSingularAttribute("gene",
		// Gene.class));

		// Predicate p = getFilterCondition(cb, expressionGene, filters);

		Predicate p = getFilterCondition(cb, expressionValue, filters);
		
		System.out.println("Result id: " + this.resultId);
		Path<?> idFilter = expressionValue;
		Path<Integer> idQuery = idFilter.get("result").get("id");
		//p = cb.and(p, cb.equal(idQuery, this.resultId));

		cqCount.select(cb.count(expressionValue)).where(p);

		int count = em.createQuery(cqCount).getSingleResult().intValue();

		CriteriaQuery<ExpressionValue> cq = cb.createQuery(ExpressionValue.class);
		expressionValue = cq.from(ExpressionValue.class);
		//expressionGene = expressionValue.join(expressionValueMetaModel.getSingularAttribute("gene", Gene.class));

		cq.select(expressionValue).where(p);

		List<ExpressionValue> result = em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();

		this.setRowCount(count);

		if (sortField == null) {
			sortField = "gene.geneSymbol";
		}
		
		Path<?> sort = expressionValue;
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

		return result;
		// cq.select(cb.count(myObj));
		// return em.createQuery(cq).getSingleResult().intValue();
		// lazyModel.setRowCount(experimentFacade.count(filters));
		// return experimentFacade.getResultList(first, pageSize, sortField,
		// sortOrder, filters);
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

	public Predicate getFilterCondition(CriteriaBuilder cb, Join<ExpressionValue, Gene> myObj,
			Map<String, Object> filters) {
		Predicate filterCondition = cb.conjunction();
		String wildCard = "%";
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			String value = wildCard + filter.getValue() + wildCard;
			if (!filter.getValue().equals("")) {
				String key = subGeneRelation(filter.getKey());

				javax.persistence.criteria.Path<String> path = myObj.get(key);
				filterCondition = cb.and(filterCondition, cb.like(path, value));
			}
		}
		return filterCondition;
	}

	@SuppressWarnings("unchecked")
	public Predicate getFilterCondition(CriteriaBuilder cb, Root<ExpressionValue> myObj, Map<String, Object> filters) {
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
				//javax.persistence.criteria.Path<String> path = myObj.get(key);
				filterCondition = cb.and(filterCondition, cb.like((Path<String>)p, value));
			}
		}
		return filterCondition;
	}

}
