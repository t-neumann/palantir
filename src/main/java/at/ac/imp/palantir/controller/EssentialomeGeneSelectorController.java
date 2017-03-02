package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import at.ac.imp.palantir.model.GenericGene;
import at.ac.imp.palantir.model.ScreenGene;

@Named("EssentialomeGeneSelectorController")
@ViewScoped
public class EssentialomeGeneSelectorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9124720632488947088L;

	private List<ScreenGene> genes;

	private List<ScreenGene> pickedGenes = new ArrayList<ScreenGene>();

	private ScreenGene selectedGene;

	private ScreenGene pickedGene;

	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		TypedQuery<ScreenGene> query = em.createQuery("SELECT g FROM ScreenGene g ", ScreenGene.class);
		genes = query.getResultList();

	}

	public List<ScreenGene> getGenes() {
		return genes;
	}

	public void setGenes(List<ScreenGene> genes) {
		this.genes = genes;
	}

	public ScreenGene getSelectedGene() {
		return selectedGene;
	}

	public void setSelectedGene(ScreenGene selectedGene) {
		this.selectedGene = selectedGene;
	}

	public List<ScreenGene> getPickedGenes() {
		return pickedGenes;
	}

	public void setPickedGenes(List<ScreenGene> pickedGenes) {
		this.pickedGenes = pickedGenes;
	}

	public ScreenGene getPickedGene() {
		return pickedGene;
	}

	public void setPickedGene(ScreenGene pickedGene) {
		this.pickedGene = pickedGene;
	}

	public void onChooseSelect(SelectEvent event) {
		if (!pickedGenes.contains(this.selectedGene)) {
			pickedGenes.add(this.selectedGene);
		}
	}

	public void onPickSelect(SelectEvent event) {
		pickedGenes.remove(this.pickedGene);
	}

	public String queryGenes() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		List<String> geneIds = new ArrayList<String>();

		for (ScreenGene gene : pickedGenes) {
			geneIds.add(((Integer) gene.getId()).toString());
		}

		flash.put("geneIds", String.join(",", geneIds));

		return "essentialomeGeneBrowser";
	}
}
