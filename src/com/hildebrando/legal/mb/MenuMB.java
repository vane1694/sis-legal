package com.hildebrando.legal.mb;

import javax.faces.bean.ManagedBean;

import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;


@ManagedBean
public class MenuMB {

    private DefaultMenuModel menuPrincipal = null;

    public DefaultMenuModel getMenuPrincipal() {
        if (menuPrincipal == null) {
            Submenu subMenu1 = new Submenu();
            subMenu1.setLabel("CUS01: Registro de Expediente ");
            subMenu1.setId("_idmenu1");

            menuPrincipal = new DefaultMenuModel();
            menuPrincipal.addSubmenu(subMenu1);

        }
        return menuPrincipal;
    }

    public void setMenuPrincipal(DefaultMenuModel menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
    }

}
