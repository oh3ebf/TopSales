/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh3ebf.topsales.ws;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:mepa-store [marketads]<br>
 * USAGE:
 * <pre>
 *        MepaStoreMarketAds client = new MepaStoreMarketAds();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author operator
 */
public class MepaStoreMarketAds {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://mepa-store-api.herokuapp.com/";

    public MepaStoreMarketAds() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("marketads");
    }

    /**
     * @param responseType Class representing the response
     * @return response object (instance of responseType class)
     */
    public <T> T getAllMarketAds(Class<T> responseType) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
