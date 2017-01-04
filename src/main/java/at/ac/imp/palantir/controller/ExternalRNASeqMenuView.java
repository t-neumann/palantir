package at.ac.imp.palantir.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named("ExternalRNASeqMenuView")
@ViewScoped
public class ExternalRNASeqMenuView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8488062938597542583L;
	
	private MenuModel model;
	
	@PostConstruct
	public void init() {
		
		model = new DefaultMenuModel();
		
		//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("RNASeq");
        firstSubmenu.setIcon("fa fa-connectdevelop");
         
        DefaultMenuItem item = new DefaultMenuItem("Genentech");
        item.setUrl("http://www.primefaces.org");
        firstSubmenu.addElement(item);
        
        item = new DefaultMenuItem("ccle");
        item.setUrl("http://www.primefaces.org");
        firstSubmenu.addElement(item);
         
        model.addElement(firstSubmenu);
         
        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Essentialomes");
        secondSubmenu.setIcon("fa fa-barcode");
 
        item = new DefaultMenuItem("K562");
        secondSubmenu.addElement(item);
         
        item = new DefaultMenuItem("Molm13");
        secondSubmenu.addElement(item);
         
        item = new DefaultMenuItem("MV4-11");
        secondSubmenu.addElement(item);
 
        model.addElement(secondSubmenu);	
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
}
