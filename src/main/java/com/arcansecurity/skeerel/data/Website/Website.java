package com.arcansecurity.skeerel.data.Website;

import com.arcansecurity.skeerel.util.json.JSONArray;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Website {

    private UUID id;

    private String name;

    private String image;

    private String url;

    private Status status;

    private List<String> domains;

    public Website(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("Website cannot be parsed due to incorrect data");
        }

        id = UUID.fromString(json.optString("id", null));
        name = json.optString("name", null);
        image = json.optString("image", null);
        url = json.optString("url", null);
        status = Status.fromString(json.optString("status", null));
        domains = new ArrayList<>();

        JSONArray jsonDomains = json.optJSONArray("domains");
        if (null != jsonDomains) {
            for (int i = 0; i < jsonDomains.length(); ++i) {
                String domain = jsonDomains.optString(i, null);
                if (domain != null) {
                    domains.add(domain);
                }
            }
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getDomains() {
        return new ArrayList<>(domains);
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    @Override
    public String toString() {
        return "Website{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", domains=" + domains +
                '}';
    }
}
