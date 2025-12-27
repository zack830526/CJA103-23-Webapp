package com.dao;

// 匯入 Category 實體
import com.entity.Category;
// 匯入 Hibernate Session 與 Transaction
import org.hibernate.Session;
import org.hibernate.Transaction;
// 匯入自訂 Hibernate 工具類
import com.util.HibernateUtil;

import java.util.List;

// CategoryDAO：專門用來操作 Category 表格的資料
public class CategoryDAO {

    // 新增分類資料
    public void save(Category category) {
        Transaction tx = null; // 宣告交易變數
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // 開啟 Hibernate session
            tx = session.beginTransaction(); // 開始交易
            session.persist(category); // 新增資料到資料庫
            tx.commit(); // 提交交易
        } catch (Exception e) { // 錯誤處理
            if (tx != null) tx.rollback(); // 發生錯誤就回滾交易
            throw e; // 把例外丟給上層
        }
    }

    // 更新分類資料
    public void update(Category category) {
        Transaction tx = null; // 宣告交易變數
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // 開啟 Hibernate session
            tx = session.beginTransaction(); // 開始交易
            session.merge(category); // 更新資料，如果不存在則新增
            tx.commit(); // 提交交易
        } catch (Exception e) { // 錯誤處理
            if (tx != null) tx.rollback(); // 發生錯誤就回滾
            throw e; // 丟出例外
        }
    }

    // 刪除分類資料
    public void delete(int id) {
        Transaction tx = null; // 宣告交易變數
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // 開啟 Hibernate session
            tx = session.beginTransaction(); // 開始交易
            Category cat = session.get(Category.class, id); // 先查詢要刪除的資料
            if(cat != null) session.remove(cat); // 如果存在，就刪除
            tx.commit(); // 提交交易
        } catch (Exception e) { // 錯誤處理
            if (tx != null) tx.rollback(); // 發生錯誤就回滾
            throw e; // 丟出例外
        }
    }

    // 根據 ID 查詢分類
    public Category findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // 開啟 Hibernate session
            return session.get(Category.class, id); // 查詢單筆資料
        }
    }

    // 查詢所有分類
    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // 開啟 Hibernate session
            return session.createQuery("from Category", Category.class).list(); // 查詢全部資料並回傳 List
        }
    }
}
