package com.entity;

import jakarta.persistence.*;

@Entity // 這個 class 對應資料庫的 table
@Table(name = "category") // 對應資料表名稱
public class Category {

    @Id // 主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動遞增
    @Column(name = "category_no") // 對應資料表欄位
    private Integer categoryNo;

    @Column(name = "name", nullable = false) // 對應欄位 name，不可為空
    private String name;

    @Column(name = "description") // 對應欄位 description
    private String description;

    // Getter & Setter
    public Integer getCategoryNo() { return categoryNo; } // 取得 categoryNo
    public void setCategoryNo(Integer categoryNo) { this.categoryNo = categoryNo; } // 設定 categoryNo

    public String getName() { return name; } // 取得名稱
    public void setName(String name) { this.name = name; } // 設定名稱

    public String getDescription() { return description; } // 取得描述
    public void setDescription(String description) { this.description = description; } // 設定描述
}
