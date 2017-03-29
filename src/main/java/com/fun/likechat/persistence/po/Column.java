package com.fun.likechat.persistence.po;

import java.util.Date;

public class Column {
    
    private Integer id;
    private Integer parentId;
    private String  displayName;
    private String  identifying;
    private String  visitUrl;
    private String  icon;
    private String  description;
    private String  contentType;
    private Integer  displayOrder;
    private String  contentRegulation;
    private Integer  state;
    private String  creator;
    private Date  createTime;
    private Date  upateTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getIdentifying() {
        return identifying;
    }
    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }
    public String getVisitUrl() {
        return visitUrl;
    }
    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    public String getContentRegulation() {
        return contentRegulation;
    }
    public void setContentRegulation(String contentRegulation) {
        this.contentRegulation = contentRegulation;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpateTime() {
        return upateTime;
    }
    public void setUpateTime(Date upateTime) {
        this.upateTime = upateTime;
    }
}
