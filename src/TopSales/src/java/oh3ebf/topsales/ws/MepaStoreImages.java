/**
 * Software: Top Sales
 *
 * Module: Mepa Store image client class
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

import java.io.File;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import oh3ebf.topsales.exceptions.RequestFailedException;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 * Jersey REST client generated for REST resource:mepa-store [images]<br>
 * USAGE:
 * <pre>
 *        MepaStoreImages client = new MepaStoreImages();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author operator
 */
public class MepaStoreImages {

    private final WebTarget webTarget;
    private final Client client;

    private static final String BASE_URI = "http://mepa-store-api.herokuapp.com/";

    public MepaStoreImages() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(MultiPartFeature.class);
        client = javax.ws.rs.client.ClientBuilder.newClient().register(MultiPartFeature.class);
        webTarget = client.target(BASE_URI).path("images");

    }

    /**
     * @return response object (instance of responseType class)
     */
    public String getAllImages() throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    /**
     * @param f
     * @return response object (instance of responseType class)
     * @throws oh3ebf.topsales.exceptions.RequestFailedException
     */
    public String addImage(File f) throws ClientErrorException, RequestFailedException {

        Invocation.Builder invocationBuilder = null;
        Response response = null;

        FileDataBodyPart fileDataBodyPart = null;
        FormDataMultiPart formDataMultiPart = null;

        int responseCode;
        String responseMessageFromServer = null;
        String responseString = null;

        try {
            // set file upload values
            fileDataBodyPart = new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            formDataMultiPart = new FormDataMultiPart();
            formDataMultiPart.bodyPart(fileDataBodyPart);

            // invoke service
            invocationBuilder = webTarget.request();
            response = invocationBuilder.post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA));

            // get response code
            responseCode = response.getStatus();
            //System.out.println("Response code: " + responseCode);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + responseCode);
            }

            // get response message
            responseMessageFromServer = response.getStatusInfo().getReasonPhrase();
            //System.out.println("ResponseMessageFromServer: " + responseMessageFromServer);

            // get response string
            responseString = response.readEntity(String.class);

        } catch (Exception ex) {
            throw new RequestFailedException("Image upload request failed", ex);
        } finally {
            // release resources, if any
            if (fileDataBodyPart != null) {
                fileDataBodyPart.cleanup();
            }

            if (formDataMultiPart != null) {
                formDataMultiPart.cleanup();
            }

            if (response != null) {
                response.close();
            }

            if (client != null) {
                client.close();
            }
        }

        return responseString;
    }

    /**
     * Function close web target
     *
     */
    public void close() {
        client.close();
    }

}
