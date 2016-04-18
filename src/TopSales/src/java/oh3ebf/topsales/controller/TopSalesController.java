/**
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
 *
 */
package oh3ebf.topsales.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import oh3ebf.topsales.ws.SalesItem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.ClientErrorException;
import oh3ebf.topsales.exceptions.JsonParseFailedException;
import oh3ebf.topsales.exceptions.RequestFailedException;
import oh3ebf.topsales.service.MepaStoreService;
import oh3ebf.topsales.ws.ImageResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class TopSalesController implements Serializable {

    @EJB
    private MepaStoreService px;
    private static final long serialVersionUID = 1L;
    private SalesItem selectedItem;
    private SalesItem ItemToAdd;
    private UploadedFile file;
    private String fileName = "";
    private static final String STORAGE_PATH = "/tmp/";

    /**
     * Creates a new instance of TopSalesController
     */
    public TopSalesController() {
    }

    @PostConstruct
    public void init() {
        this.selectedItem = new SalesItem();
        this.ItemToAdd = new SalesItem();
    }

    /**
     * Function implements access to all sales items
     *
     * @return
     */
    public List<SalesItem> getSalesItems() {
        return px.getMarketAds();
    }

    /**
     * Function implements access to selected sales item
     *
     * @return
     */
    public SalesItem getSelectedItem() {
        return this.selectedItem;
    }

    /**
     * Function sets selected sales item
     *
     * @param selectedItem
     */
    public void setSelectedItem(SalesItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the ItemToAdd
     */
    public SalesItem getItemToAdd() {
        return this.ItemToAdd;
    }

    /**
     * @param ItemToAdd the ItemToAdd to set
     */
    public void setItemToAdd(SalesItem ItemToAdd) {
        this.ItemToAdd = ItemToAdd;
    }

    /**
     * Function handles dialog closing from cross
     *
     * @param event
     */
    public void dialogClose(CloseEvent event) {
        // clear variables after cancel
        this.fileName = "";
        this.ItemToAdd = new SalesItem();
    }
   
    /**
     * function adds new sales item to database
     *
     */
    public void addSalesItem() {

        ImageResponse img = null;

        if (!this.fileName.isEmpty()) {
            try {
                // save image
                img = px.addImage(new File(STORAGE_PATH + this.fileName));

                // setup urls
                ItemToAdd.setImageUrl(img.getImageUrl());
                ItemToAdd.setThumbnailUrl(img.getThumbnailUrl());
            } catch (JsonParseFailedException ex) {
                showMessage(FacesMessage.SEVERITY_ERROR, null, "Tietosisältö virhe! ", "Tallennuspyyntö vastauksen tiedot virheellisiä.");
            } catch (RequestFailedException ex) {
                showMessage(FacesMessage.SEVERITY_ERROR, null, "Kuvan tallennus virhe! ", "Tiedoston " + this.fileName + " tallentaminen epäonnistui.");
            }
        }

        try {
            px.addMarketAds(ItemToAdd);
        } catch (ClientErrorException ex) {
            showMessage(FacesMessage.SEVERITY_ERROR, null, "Ilmoituksen tallennus virhe! ", "Ilmoituksen tallennus epäonnistui.");
        }

        // remove temporary image file
        deleteFile();

        // close dialog
        RequestContext.getCurrentInstance().execute("PF('newSalesItemDialog').hide();");

        // clear variables after cancel
        this.fileName = "";
        this.ItemToAdd = new SalesItem();
    }

    /**
     * 
     * @return file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * Function sets file
     * @param file 
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * Function implements event listener for file upload events
     *
     * @param event
     */
    public void fileUploadListener(FileUploadEvent event) {
        // get uploaded file from the FileUploadEvent
        this.fileName = event.getFile().getFileName();

        try {
            // copy file to temporary location
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException ex) {
            showMessage(FacesMessage.SEVERITY_ERROR, null, "Lataus epäonnistui! ", this.fileName + " tallennus epäonnistui.");
        }

        showMessage(FacesMessage.SEVERITY_INFO, null, "Lataus onnistui! ", this.fileName + " tallennettu.");
    }

    /**
     * Function implements local file copy
     *
     * @param fileName of copied file
     * @param in input stream copy from
     */
    public void copyFile(String fileName, InputStream in) {

        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(STORAGE_PATH + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            // close streams
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, null, "Tiedoston kopiointi virhe! ", this.fileName + " kopiointi epäonnistui.");
        }
    }

    /**
     * Function handles file deletion
     *
     * @return result of operation
     */
    private boolean deleteFile() {
        if (!this.fileName.isEmpty()) {
            try {
                // temporary file to open
                File f = new File(STORAGE_PATH + fileName);

                // delete file
                if (f.delete()) {
                    return true;
                } else {
                    return false;
                }

            } catch (SecurityException ex) {
                showMessage(FacesMessage.SEVERITY_ERROR, null, "Tiedoston poisto virhe! ", this.fileName + " poistaminen epäonnistui.");
            }
        }

        return false;
    }

    /**
     * Function is used to launch notifications to user
     *
     * @param id for parameter of growl or messages component
     * @param header of message
     * @param msg message content
     */
    private void showMessage(FacesMessage.Severity s, String id, String header, String msg) {
        // get context
        FacesContext context = FacesContext.getCurrentInstance();

        // add message
        context.addMessage(id, new FacesMessage(s, header, msg));

        // update rendering component
        RequestContext.getCurrentInstance().update("growl");
    }
}
