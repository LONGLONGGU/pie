package com.framework.pie.admin.model;

public class SysRole {
    private String id;

    private Boolean deleted;

    private Integer odr;

    private String code;

    private String description;

    private Boolean iseditor;

    private Boolean isorgrole;

    private String name;

    private String orgId;

    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getOdr() {
        return odr;
    }

    public void setOdr(Integer odr) {
        this.odr = odr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIseditor() {
        return iseditor;
    }

    public void setIseditor(Boolean iseditor) {
        this.iseditor = iseditor;
    }

    public Boolean getIsorgrole() {
        return isorgrole;
    }

    public void setIsorgrole(Boolean isorgrole) {
        this.isorgrole = isorgrole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }
}