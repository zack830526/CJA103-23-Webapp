package com.util;

// 匯入 Category 實體
import com.entity.Category;
// 匯入 Hibernate SessionFactory
import org.hibernate.SessionFactory;
// 匯入 Hibernate 設定類別
import org.hibernate.cfg.Configuration;

// Hibernate 工具類，用來建立 SessionFactory
public class HibernateUtil {

    // 單例模式(SessionFactory 只建立一次)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // 建立 SessionFactory 的方法
    private static SessionFactory buildSessionFactory() {
        try {
            // 建立 Hibernate Configuration 物件
            Configuration cfg = new Configuration();
            // 載入 hibernate.cfg.xml 設定檔
            cfg.configure("hibernate.cfg.xml");
            // 將 Category 實體加入 Hibernate 管理
            cfg.addAnnotatedClass(Category.class);
            // 建立並回傳 SessionFactory
            return cfg.buildSessionFactory();
        } catch (Throwable ex) { // 捕捉初始化錯誤
            // 印出錯誤訊息
            System.err.println("Initial SessionFactory creation failed." + ex);
            // 丟出初始化錯誤，程式會停止
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 提供外部取得 SessionFactory 的方法
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
