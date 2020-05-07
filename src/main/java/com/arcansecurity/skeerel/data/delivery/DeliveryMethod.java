package com.arcansecurity.skeerel.data.delivery;

import com.arcansecurity.skeerel.util.json.JSONObject;

public class DeliveryMethod {

    private String id;

    private Type type;

    private boolean primary;

    private String name;

    private String deliveryTextContent;

    private Long price;

    private PickUpPoints pickUpPoints;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryTextContent(String deliveryTextContent) {
        this.deliveryTextContent = deliveryTextContent;
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

    public void setPickUpPoints(PickUpPoints pickUpPoints) {
        this.pickUpPoints = pickUpPoints;
    }

    public JSONObject toJson() {
        if (id == null || type == null || name == null || deliveryTextContent == null || price == null) {
            throw new IllegalArgumentException("Not all mandatory fields are set");
        }

        if (type == Type.HOME && pickUpPoints != null) {
            throw new IllegalArgumentException("Home delivery cannot have pick up points");
        }

        if (type != Type.HOME && (pickUpPoints == null || pickUpPoints.isEmpty())) {
            throw new IllegalArgumentException(type.toString() + " must have pick up points");
        }

        JSONObject json = new JSONObject();
        json.put("id", id)
                .put("type", type.toString())
                .put("primary", primary)
                .put("name", name)
                .put("delivery_text_content", deliveryTextContent)
                .put("price", price);

        if (pickUpPoints != null) {
            json.put("pick_up_points", pickUpPoints.toJson());
        }

        return json;
    }

    public String toString() {
        return toJson().toString();
    }
}
