/**
 * *********************************************************
 * Software: Top Sales
 *
 * Module: TopSales controller class
 *
 * Version: 0.1
 *
 * Licence: GPL2
 *
 * Owner: Kim Kristo
 *
 * Date creation : 22.9.2015
 *
 **********************************************************
 */

package oh3ebf.topsales.controller;


import oh3ebf.topsales.ws.SalesItem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import oh3ebf.topsales.service.MepaStoreService;

@ManagedBean
@ViewScoped
public class TopSalesController implements Serializable {

    @EJB
    private MepaStoreService px;
    private static final long serialVersionUID = 1L;
    private SalesItem selectedItem;


    /**
     * Creates a new instance of TopSalesController
     */
    public TopSalesController() {
    }

    
    @PostConstruct
    public void init() {
        this.selectedItem = new SalesItem();
    }

    
    public List<SalesItem> getSalesItems() {
        return px.getMarketAds();        
    }
    
    public SalesItem getSelectedItem() {
        return this.selectedItem;
    }

    public void setSelectedItem(SalesItem selectedItem) {
        this.selectedItem = selectedItem;
        System.out.print(selectedItem.getTitle());
    }
}
