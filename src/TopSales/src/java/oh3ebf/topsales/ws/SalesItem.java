/**
 * Software: Top Sales
 *
 * Module: Sales item class
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

import java.io.Serializable;

public class SalesItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title = "";
    private String description = "";
    private int priceCents = 0;
    private String imageUrl = "";
    private String thumbnailUrl = "";
    private String email = "";
    private String phone = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.toLowerCase().equals("null") || imageUrl.isEmpty()) {
            this.imageUrl = "none";
        } else {
            this.imageUrl = imageUrl;
        }
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        if (thumbnailUrl == null || thumbnailUrl.toLowerCase().equals("null") || thumbnailUrl.isEmpty()) {
            this.thumbnailUrl = "none";
        } else {
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
