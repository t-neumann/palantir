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

@Named("PublicGeneSelectorController")
@ViewScoped
public class PublicGeneSelectorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9124720632488947088L;
	
	private List<GenericGene> genes;
	
	private List<GenericGene> pickedGenes = new ArrayList<GenericGene>();
	
	private GenericGene selectedGene;
	
	private GenericGene pickedGene;
	
	@PersistenceContext(unitName = "palantir-db")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		
		TypedQuery<GenericGene> query = em.createQuery("SELECT g FROM GenericGene g", GenericGene.class);
		genes = query.getResultList();
		
	}

	public List<GenericGene> getGenes() {
		return genes;
	}

	public void setGenes(List<GenericGene> genes) {
		this.genes = genes;
	}

	public GenericGene getSelectedGene() {
		return selectedGene;
	}

	public void setSelectedGene(GenericGene selectedGene) {
		this.selectedGene = selectedGene;
	}

	public List<GenericGene> getPickedGenes() {
		return pickedGenes;
	}

	public void setPickedGenes(List<GenericGene> pickedGenes) {
		this.pickedGenes = pickedGenes;
	}

	public GenericGene getPickedGene() {
		return pickedGene;
	}

	public void setPickedGene(GenericGene pickedGene) {
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
		
		for (GenericGene gene : pickedGenes) {
			geneIds.add(((Integer)gene.getId()).toString());
		}
		
		flash.put("geneIds", String.join(",", geneIds));
		
        return "publicGeneBrowser";
    }
}
