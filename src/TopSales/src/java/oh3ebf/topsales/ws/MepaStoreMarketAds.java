/**
 * Software: Top Sales
 *
 * Module: Mepa Store market ads client class
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
package oh3ebf.topsales.ws;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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
    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://mepa-store-api.herokuapp.com/";

    public MepaStoreMarketAds() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("marketads");
    }

    /**
     * @return response object (instance of responseType class)
     */
    public String getAllMarketAds() throws ClientErrorException {
        return webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
    }

    /**
     * @param requestEntity request data@return response object (instance of responseType class)
     * @return 
     */
    public String addMarketAds(String requestEntity) throws ClientErrorException {
        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), String.class);
    }
    
    public void close() {
        client.close();
    }
    
}
