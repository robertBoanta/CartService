package edus.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Picture {
    @JsonProperty("picture_id")
    private String pictureId;

    @JsonProperty("picture_url")
    private String pictureUrl;

    // Default constructor
    public Picture() {}

    // Getters and Setters
    public String getPictureId() { return pictureId; }
    public void setPictureId(String pictureId) { this.pictureId = pictureId; }

    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }
}
