/**
 * *********************************************************
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
 **********************************************************
 */
package oh3ebf.topsales.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import oh3ebf.topsales.ws.SalesItem;
import oh3ebf.topsales.ws.MepaStoreMarketAds;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Stateless
public class MepaStoreService {

    private MepaStoreMarketAds sales;
    private final List<SalesItem> salesItemList = new ArrayList<>();

    @PostConstruct
    public void init() {
        sales = new MepaStoreMarketAds();
        revalidateData();
    }

    /**
     * Function reloads data from REST Api
     *
     */
    public void revalidateData() {
        // get newest data
        String response = sales.getAllMarketAds(String.class);

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

        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
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
}
