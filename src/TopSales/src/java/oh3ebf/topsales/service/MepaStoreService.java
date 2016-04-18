/**
 * Software: Top Sales
 *
 * Module: Mepa Store service EJB class
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
package oh3ebf.topsales.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import oh3ebf.topsales.exceptions.JsonParseFailedException;
import oh3ebf.topsales.exceptions.RequestFailedException;
import oh3ebf.topsales.ws.ImageResponse;
import oh3ebf.topsales.ws.MepaStoreImages;
import oh3ebf.topsales.ws.SalesItem;
import oh3ebf.topsales.ws.MepaStoreMarketAds;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

@Stateless
public class MepaStoreService {

    private MepaStoreMarketAds sales;
    private MepaStoreImages images;
    private final List<SalesItem> salesItemList = new ArrayList<>();

    @PostConstruct
    public void init() {
        sales = new MepaStoreMarketAds();
        images = new MepaStoreImages();
        revalidateData();
    }

    /**
     * Function reloads data from REST API
     *
     */
    public void revalidateData() {
        // get newest data
        String response = sales.getAllMarketAds();

        // clear old result set
        salesItemList.clear();

        try {
            JSONArray adsArray = new JSONArray(response);

            // parse ads from JSON presentation
            for (int i = 0; i < adsArray.length(); i++) {
                SalesItem s = new SalesItem();

                JSONObject ads = (JSONObject) adsArray.get(i);

                s.setId(ads.getString("id"));
                s.setTitle(ads.optString("title", "Otsikkotieto puuttuu."));
                s.setDescription(ads.optString("description", "Tuotekuvaus puuttuu."));
                s.setEmail(ads.optString("email", "Sähköpostiosoite puuttu."));
                s.setPhone(ads.optString("phone", "Puhelinnumero puuttuu."));
                s.setPriceCents(ads.optInt("priceCents", 0));
                s.setThumbnailUrl(ads.optString("thumbnailUrl", "Tuotteen ikonikuva puuttuu."));
                s.setImageUrl(ads.optString("imageUrl", "Tuotteen kuva puuttuu."));

                salesItemList.add(s);
            }

        } catch (JSONException ex) {
            //throw new JsonParseFailedException("failed to parse sales item from json", ex);
        }
    }

    /**
     * Function returns list of market ads
     *
     * @return
     */
    public List<SalesItem> getMarketAds() {
        return salesItemList;
    }

    /**
     * Function stores sales item to backend system
     *
     * @param s item to store
     */
    public void addMarketAds(SalesItem s) {
        JSONObject json = new JSONObject(s);

        String response = sales.addMarketAds(json.toString());
        revalidateData();
    }

    /**
     * Function stores image to backend system
     *
     * @param imageFile to store
     * @return result of save operation
     * @throws JsonParseFailedException
     * @throws oh3ebf.topsales.exceptions.RequestFailedException
     */
    public ImageResponse addImage(File imageFile) throws JsonParseFailedException, RequestFailedException {
        String response = images.addImage(imageFile);

        try {
            JSONObject json = new JSONObject(response);

            // parse image response data from JSON presentation
            ImageResponse img = new ImageResponse();

            img.setSuccess(json.getBoolean("success"));
            img.setImageUrl(json.optString("imageUrl"));
            img.setThumbnailUrl(json.optString("thumbnailUrl"));
            img.setError(json.optString("error"));

            return img;
        } catch (JSONException ex) {
            throw new JsonParseFailedException("failed to parse image response", ex);
        } 
    }
}
