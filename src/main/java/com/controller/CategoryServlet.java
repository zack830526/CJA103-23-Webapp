package com.controller;

// 匯入 DAO 與實體類別
import com.dao.CategoryDAO;
import com.entity.Category;

// 使用 Jakarta Servlet API
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

// 設定這個 Servlet 的 URL 映射為 /category
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    // 建立 DAO 實例，用來操作資料庫
    private CategoryDAO dao = new CategoryDAO();

    // 處理 GET 請求 (通常用於查詢、顯示表單或刪除)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 從 request 取得 action 參數，例如 "new", "edit", "delete"
        String action = request.getParameter("action");
        if (action == null) action = "list"; // 如果沒有傳 action，就預設為 list

        try {
            switch (action) {
                case "new": // 顯示新增分類表單
                    request.getRequestDispatcher("/category-form.jsp").forward(request, response);
                    break;
                case "edit": // 顯示修改分類表單
                case "view": // 顯示分類詳細資訊（可共用表單）
                    int id = Integer.parseInt(request.getParameter("id")); // 取得分類編號
                    Category cat = dao.findById(id); // 用 DAO 查詢資料
                    if(cat == null) { // 如果找不到該分類
                        request.setAttribute("errorMsg", "查無此分類！");
                    }
                    request.setAttribute("category", cat); // 把查到的分類放進 request
                    request.getRequestDispatcher("/category-form.jsp").forward(request, response); // 導向表單頁
                    break;
                case "delete": // 刪除分類
                    int delId = Integer.parseInt(request.getParameter("id")); // 取得要刪除的分類編號
                    dao.delete(delId); // 呼叫 DAO 刪除
                    response.sendRedirect(request.getContextPath() + "/category"); // 刪除後回到列表頁
                    break;
                default: // list 顯示所有分類
                    List<Category> list = dao.findAll(); // 取得所有分類
                    request.setAttribute("categories", list); // 放進 request
                    request.getRequestDispatcher("/category-list.jsp").forward(request, response); // 導向列表頁
            }
        } catch (Exception e) { // 錯誤處理
            request.setAttribute("errorMsg", "操作失敗: " + e.getMessage()); // 設定錯誤訊息
            request.getRequestDispatcher("/category-list.jsp").forward(request, response); // 導向列表頁顯示錯誤
        }
    }

    // 處理 POST 請求 (通常用於新增或修改)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 取得表單傳過來的資料
        String idStr = request.getParameter("id"); // 分類編號 (若有，表示修改)
        String name = request.getParameter("name"); // 分類名稱
        String desc = request.getParameter("description"); // 分類描述

        // 驗證名稱不可為空
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMsg", "名稱不可為空"); // 設定錯誤訊息
            request.getRequestDispatcher("/category-form.jsp").forward(request, response); // 回到表單頁
            return; // 停止後續程式
        }

        try {
            // 建立 Category 物件
            Category cat = new Category();
            cat.setName(name); // 設定名稱
            cat.setDescription(desc); // 設定描述

            if (idStr == null || idStr.isEmpty()) { // 如果沒有 id，表示新增
                dao.save(cat); // 呼叫 DAO 新增
            } else { // 否則表示修改
                cat.setCategoryNo(Integer.parseInt(idStr)); // 設定分類編號
                dao.update(cat); // 呼叫 DAO 更新
            }

            response.sendRedirect(request.getContextPath() + "/category"); // 操作完成後回到列表頁
        } catch (Exception e) { // 錯誤處理
            request.setAttribute("errorMsg", "操作失敗: " + e.getMessage()); // 設定錯誤訊息
            request.getRequestDispatcher("/category-form.jsp").forward(request, response); // 回到表單頁顯示錯誤
        }
    }
}
