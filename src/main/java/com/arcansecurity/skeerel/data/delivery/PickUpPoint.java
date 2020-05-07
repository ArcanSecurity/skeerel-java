package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.data.address.Country;
import com.arcansecurity.skeerel.util.json.JSONObject;

public class PickUpPoint {

    private String id;

    private String name;

    private boolean primary;

    private String address;

    private String zipCode;

    private String city;

    private Country country;

    private String deliveryTextContent;

    private Color deliveryTextColor;

    private Long price;

    private Color priceTextColor;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setDeliveryTextContent(String deliveryTextContent) {
        this.deliveryTextContent = deliveryTextContent;
    }

    public void setDeliveryTextColor(Color deliveryTextColor) {
        this.deliveryTextColor = deliveryTextColor;
    }

    public void setPrice(Integer price) {
        if (price == null) {
            this.price = null;
            return ;
        }

        setPrice(Long.valueOf(price));
    }

    public void setPrice(Long price) {
        if (price == null) {
            this.price = null;
            return ;
        }

        if (price < 0) {
            throw new IllegalArgumentException("Cannot set a price lower than 0");
        }

        this.price = price;
    }

    public void setPriceTextColor(Color priceTextColor) {
        this.priceTextColor = priceTextColor;
    }

    public JSONObject toJson() {
        if (id == null || name == null || address == null || zipCode == null || city == null || country == null) {
            throw new IllegalArgumentException("Not all mandatory fields are set");
        }

        JSONObject json = new JSONObject();
        json.put("id", id)
                .put("name", name)
                .put("primary", primary)
                .put("address", address)
                .put("zip_code", zipCode)
                .put("city", city)
                .put("country", country.getAlpha2());

        if (deliveryTextContent != null) {
            json.put("delivery_text_content", deliveryTextContent);
            if (deliveryTextColor != null) {
                json.put("delivery_text_color", deliveryTextColor.toString().toLowerCase());
            }
        }

        if (price != null && price > 0) {
            json.put("price", price);
            if (priceTextColor != null) {
                json.put("price_text_color", deliveryTextColor.toString().toLowerCase());
            }
        }

        return json;
    }

    public String toString() {
        return toJson().toString();
    }
}
