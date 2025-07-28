package edus.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute {
    @JsonProperty("attribute_name")
    private String attributeName;

    @JsonProperty("attribute_value")
    private String attributeValue;

    @JsonProperty("attribute_name_id")
    private Integer attributeNameId;

    @JsonProperty("attribute_value_id")
    private Integer attributeValueId;

    // Default constructor
    public Attribute() {}

    // Getters and Setters
    public String getAttributeName() { return attributeName; }
    public void setAttributeName(String attributeName) { this.attributeName = attributeName; }

    public String getAttributeValue() { return attributeValue; }
    public void setAttributeValue(String attributeValue) { this.attributeValue = attributeValue; }

    public Integer getAttributeNameId() { return attributeNameId; }
    public void setAttributeNameId(Integer attributeNameId) { this.attributeNameId = attributeNameId; }

    public Integer getAttributeValueId() { return attributeValueId; }
    public void setAttributeValueId(Integer attributeValueId) { this.attributeValueId = attributeValueId; }
}
