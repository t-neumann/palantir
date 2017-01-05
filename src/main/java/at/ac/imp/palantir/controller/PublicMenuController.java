package at.ac.imp.palantir.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

import at.ac.imp.palantir.exceptions.DatabaseException;
import at.ac.imp.palantir.facades.ExperimentFacade;
import at.ac.imp.palantir.facades.PublicDataFacade;
import at.ac.imp.palantir.model.ExternalRNASeqEntry;
import at.ac.imp.palantir.model.ExternalRNASeqResource;

@Named("PublicMenuController")
@ViewScoped
public class PublicMenuController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8488062938597542583L;
	
	private MenuModel model;
	
	@EJB
	private PublicDataFacade publicDataFacade;
	
	@PostConstruct
	public void init() {
		
		model = new DefaultMenuModel();
		
		//First submenu
        DefaultSubMenu externalRNASeqSubMenu = new DefaultSubMenu("RNASeq");
        externalRNASeqSubMenu.setIcon("fa fa-connectdevelop");
        
        try {
			List<ExternalRNASeqResource> externalRNASeqResources = publicDataFacade.getAvailableRNASeqResources();
			
			for (ExternalRNASeqResource resource : externalRNASeqResources) {
				DefaultSubMenu resourceSubMenu = new DefaultSubMenu(resource.getName());
				for (ExternalRNASeqEntry entry : resource.getEntries()) {
					DefaultMenuItem entryItem = new DefaultMenuItem(entry.getName());
					entryItem.setCommand("#{PublicMenuController.externalRNASeqRedirect}");
					entryItem.setParam("resourceId", resource.getId());
					entryItem.setParam("entryId", entry.getId());
					entryItem.setUpdate(":contentForm");
					resourceSubMenu.addElement(entryItem);
				}
				externalRNASeqSubMenu.addElement(resourceSubMenu);
				
			}
	         
	        model.addElement(externalRNASeqSubMenu);
	         
	        //Second submenu
	        DefaultSubMenu essentialomeSubmenu = new DefaultSubMenu("Essentialomes");
	        essentialomeSubmenu.setIcon("fa fa-barcode");
	 
	        DefaultMenuItem item = new DefaultMenuItem("K562");
	        essentialomeSubmenu.addElement(item);
	         
	        item = new DefaultMenuItem("Molm13");
	        essentialomeSubmenu.addElement(item);
	         
	        item = new DefaultMenuItem("MV4-11");
	        essentialomeSubmenu.addElement(item);
	 
	        model.addElement(essentialomeSubmenu);	
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public String externalRNASeqRedirect(ActionEvent event) {
		MenuItem menuItem = ((MenuActionEvent) event).getMenuItem();
		int resourceId = Integer.parseInt(menuItem.getParams().get("resourceId").get(0));
		int entryId = Integer.parseInt(menuItem.getParams().get("entryId").get(0));
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("resourceId", resourceId);
		flash.put("entryId", entryId);
		System.out.println("New entry in flash: " + entryId);
		return "externalRNASeq";
	}
}
