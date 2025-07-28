package edus.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Product {
    @JsonProperty("productId")
    private UUID productId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("fullDescription")
    private String fullDescription;

    @JsonProperty("minimumQuantity")
    private Integer minimumQuantity;

    @JsonProperty("manufacturerId")
    private String manufacturerId;

    @JsonProperty("manufacturerName")
    private String manufacturerName;

    @JsonProperty("categoryLevel1Id")
    private String categoryLevel1Id;

    @JsonProperty("categoryLevel1Name")
    private String categoryLevel1Name;

    @JsonProperty("categoryLevel2Id")
    private String categoryLevel2Id;

    @JsonProperty("categoryLevel2Name")
    private String categoryLevel2Name;

    @JsonProperty("categoryLevel3Id")
    private String categoryLevel3Id;

    @JsonProperty("categoryLevel3Name")
    private String categoryLevel3Name;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("reservedStock")
    private Integer reservedStock;

    @JsonProperty("supplierStock")
    private Integer supplierStock;

    @JsonProperty("supplierStockDeliveryDate")
    private String supplierStockDeliveryDate;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("promoPrice")
    private BigDecimal promoPrice;

    @JsonProperty("greenStamp")
    private BigDecimal greenStamp;

    @JsonProperty("vatPercent")
    private BigDecimal vatPercent;

    @JsonProperty("isSpecialPrice")
    private Boolean isSpecialPrice;

    @JsonProperty("length")
    private BigDecimal length;

    @JsonProperty("width")
    private BigDecimal width;

    @JsonProperty("height")
    private BigDecimal height;

    @JsonProperty("lengthUnitOfMeasure")
    private String lengthUnitOfMeasure;

    @JsonProperty("weight")
    private BigDecimal weight;

    @JsonProperty("weightUnitOfMeasure")
    private String weightUnitOfMeasure;

    @JsonProperty("isNew")
    private Boolean isNew;

    @JsonProperty("isEol")
    private Boolean isEol;

    @JsonProperty("isOnDemand")
    private Boolean isOnDemand;

    @JsonProperty("onDemandDeliveryTime")
    private String onDemandDeliveryTime;

    @JsonProperty("hasResealed")
    private Boolean hasResealed;

    @JsonProperty("originalProductId")
    private String originalProductId;

    @JsonProperty("warranty")
    private Integer warranty;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("version")
    private String version;

    @JsonProperty("hasPriceRequest")
    private Boolean hasPriceRequest;

    @JsonProperty("attributes")
    private List<Attribute> attributes;

    @JsonProperty("pictures")
    private List<Picture> pictures;

    // Default constructor
    public Product() {}

    // Getters and Setters
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getEan() { return ean; }
    public void setEan(String ean) { this.ean = ean; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public Integer getMinimumQuantity() { return minimumQuantity; }
    public void setMinimumQuantity(Integer minimumQuantity) { this.minimumQuantity = minimumQuantity; }

    public String getManufacturerId() { return manufacturerId; }
    public void setManufacturerId(String manufacturerId) { this.manufacturerId = manufacturerId; }

    public String getManufacturerName() { return manufacturerName; }
    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }

    public String getCategoryLevel1Id() { return categoryLevel1Id; }
    public void setCategoryLevel1Id(String categoryLevel1Id) { this.categoryLevel1Id = categoryLevel1Id; }

    public String getCategoryLevel1Name() { return categoryLevel1Name; }
    public void setCategoryLevel1Name(String categoryLevel1Name) { this.categoryLevel1Name = categoryLevel1Name; }

    public String getCategoryLevel2Id() { return categoryLevel2Id; }
    public void setCategoryLevel2Id(String categoryLevel2Id) { this.categoryLevel2Id = categoryLevel2Id; }

    public String getCategoryLevel2Name() { return categoryLevel2Name; }
    public void setCategoryLevel2Name(String categoryLevel2Name) { this.categoryLevel2Name = categoryLevel2Name; }

    public String getCategoryLevel3Id() { return categoryLevel3Id; }
    public void setCategoryLevel3Id(String categoryLevel3Id) { this.categoryLevel3Id = categoryLevel3Id; }

    public String getCategoryLevel3Name() { return categoryLevel3Name; }
    public void setCategoryLevel3Name(String categoryLevel3Name) { this.categoryLevel3Name = categoryLevel3Name; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getReservedStock() { return reservedStock; }
    public void setReservedStock(Integer reservedStock) { this.reservedStock = reservedStock; }

    public Integer getSupplierStock() { return supplierStock; }
    public void setSupplierStock(Integer supplierStock) { this.supplierStock = supplierStock; }

    public String getSupplierStockDeliveryDate() { return supplierStockDeliveryDate; }
    public void setSupplierStockDeliveryDate(String supplierStockDeliveryDate) { this.supplierStockDeliveryDate = supplierStockDeliveryDate; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getPromoPrice() { return promoPrice; }
    public void setPromoPrice(BigDecimal promoPrice) { this.promoPrice = promoPrice; }

    public BigDecimal getGreenStamp() { return greenStamp; }
    public void setGreenStamp(BigDecimal greenStamp) { this.greenStamp = greenStamp; }

    public BigDecimal getVatPercent() { return vatPercent; }
    public void setVatPercent(BigDecimal vatPercent) { this.vatPercent = vatPercent; }

    public Boolean getIsSpecialPrice() { return isSpecialPrice; }
    public void setIsSpecialPrice(Boolean isSpecialPrice) { this.isSpecialPrice = isSpecialPrice; }

    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }

    public BigDecimal getWidth() { return width; }
    public void setWidth(BigDecimal width) { this.width = width; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public String getLengthUnitOfMeasure() { return lengthUnitOfMeasure; }
    public void setLengthUnitOfMeasure(String lengthUnitOfMeasure) { this.lengthUnitOfMeasure = lengthUnitOfMeasure; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public String getWeightUnitOfMeasure() { return weightUnitOfMeasure; }
    public void setWeightUnitOfMeasure(String weightUnitOfMeasure) { this.weightUnitOfMeasure = weightUnitOfMeasure; }

    public Boolean getIsNew() { return isNew; }
    public void setIsNew(Boolean isNew) { this.isNew = isNew; }

    public Boolean getIsEol() { return isEol; }
    public void setIsEol(Boolean isEol) { this.isEol = isEol; }

    public Boolean getIsOnDemand() { return isOnDemand; }
    public void setIsOnDemand(Boolean isOnDemand) { this.isOnDemand = isOnDemand; }

    public String getOnDemandDeliveryTime() { return onDemandDeliveryTime; }
    public void setOnDemandDeliveryTime(String onDemandDeliveryTime) { this.onDemandDeliveryTime = onDemandDeliveryTime; }

    public Boolean getHasResealed() { return hasResealed; }
    public void setHasResealed(Boolean hasResealed) { this.hasResealed = hasResealed; }

    public String getOriginalProductId() { return originalProductId; }
    public void setOriginalProductId(String originalProductId) { this.originalProductId = originalProductId; }

    public Integer getWarranty() { return warranty; }
    public void setWarranty(Integer warranty) { this.warranty = warranty; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public Boolean getHasPriceRequest() { return hasPriceRequest; }
    public void setHasPriceRequest(Boolean hasPriceRequest) { this.hasPriceRequest = hasPriceRequest; }

    public List<Attribute> getAttributes() { return attributes; }
    public void setAttributes(List<Attribute> attributes) { this.attributes = attributes; }

    public List<Picture> getPictures() { return pictures; }
    public void setPictures(List<Picture> pictures) { this.pictures = pictures; }
}
