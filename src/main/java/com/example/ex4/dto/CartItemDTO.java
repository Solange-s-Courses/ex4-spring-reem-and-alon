package com.example.ex4.dto;

public class CartItemDTO {
    private Long pkgId;
    private String subPkgName;

    public Long getPkgId() { return pkgId; }
    public void setPkgId(Long pkgId) { this.pkgId = pkgId; }

    public String getSubPkgName() { return subPkgName; }
    public void subPackageId(String subPkgName) { this.subPkgName = subPkgName;}
}
