package com.dao;

import com.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CategoryDAO {

    // 建立 SessionFactory (Hibernate 連線工廠)
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    // 新增資料
    public void save(Category category) {
        Session session = factory.openSession(); // 開啟 Session
        try {
            session.beginTransaction(); // 開始交易
            session.persist(category); // 儲存 Category
            session.getTransaction().commit(); // 提交交易
            System.out.println("新增成功: " + category.getName()); // 顯示新增成功訊息
        } catch (Exception e) {
            session.getTransaction().rollback(); // 失敗則回滾
            System.out.println("新增失敗: " + e.getMessage()); // 顯示錯誤訊息
        } finally {
            session.close(); // 關閉 Session
        }
    }

    // 修改資料
    public void update(Category category) {
        Session session = factory.openSession();
        try {
            session.beginTransaction(); // 開始交易
            session.merge(category); // 更新 Category
            session.getTransaction().commit(); // 提交交易
            System.out.println("修改成功: " + category.getCategoryNo()); // 顯示修改成功訊息
        } catch (Exception e) {
            session.getTransaction().rollback(); // 失敗回滾
            System.out.println("修改失敗: " + e.getMessage()); // 顯示錯誤訊息
        } finally {
            session.close(); // 關閉 Session
        }
    }

    // 依 ID 查詢單筆資料
    public Category findById(int id) {
        Session session = factory.openSession(); // 開啟 Session
        Category category = null;
        try {
            category = session.get(Category.class, id); // 取得資料
            if (category == null) System.out.println("查無資料 (ID=" + id + ")"); // 查無資料提示
        } finally {
            session.close(); // 關閉 Session
        }
        return category;
    }

    // 查詢所有資料
    public List<Category> findAll() {
        Session session = factory.openSession();
        List<Category> list = session.createQuery("FROM Category", Category.class).list(); // HQL 查詢所有
        session.close(); // 關閉 Session
        return list;
    }

    // 刪除資料
    public void delete(int id) {
        Session session = factory.openSession();
        try {
            session.beginTransaction(); // 開始交易
            Category category = session.get(Category.class, id); // 先查詢資料
            if (category != null) {
                session.remove(category); // 刪除資料
                session.getTransaction().commit(); // 提交交易
                System.out.println("刪除成功: ID=" + id); // 顯示刪除成功
            } else {
                System.out.println("刪除失敗: 查無此資料 (ID=" + id + ")"); // 查無資料提示
            }
        } catch (Exception e) {
            session.getTransaction().rollback(); // 失敗回滾
            System.out.println("刪除失敗: " + e.getMessage()); // 顯示錯誤訊息
        } finally {
            session.close(); // 關閉 Session
        }
    }
}
