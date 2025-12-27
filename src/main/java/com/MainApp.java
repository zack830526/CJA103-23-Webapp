package com;

import com.dao.CategoryDAO;
import com.entity.Category;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO(); // 建立 DAO
        Scanner sc = new Scanner(System.in); // 建立 Scanner 用來讀取 Console 輸入

        while (true) {
            System.out.println("\n===== Category CRUD ====="); // 主選單
            System.out.println("1. 新增"); // 選項 1: 新增
            System.out.println("2. 修改"); // 選項 2: 修改
            System.out.println("3. 查單筆"); // 選項 3: 查單筆
            System.out.println("4. 查全部"); // 選項 4: 查全部
            System.out.println("5. 刪除"); // 選項 5: 刪除
            System.out.println("0. 離開"); // 選項 0: 離開
            System.out.print("請選擇操作: "); // 提示使用者輸入
            int choice = sc.nextInt(); // 讀取使用者選擇
            sc.nextLine(); // 清掉換行符號

            switch (choice) {
                case 1: // 新增
                    System.out.print("名稱: "); // 提示輸入名稱
                    String name = sc.nextLine();
                    if (name.isEmpty()) { System.out.println("名稱不可為空"); break; } // 名稱不可為空
                    System.out.print("描述: "); // 提示輸入描述
                    String desc = sc.nextLine();
                    Category cat = new Category(); // 建立 Category 物件
                    cat.setName(name); // 設定名稱
                    cat.setDescription(desc); // 設定描述
                    dao.save(cat); // 呼叫 DAO 新增
                    break;

                case 2: // 修改
                    System.out.print("輸入要修改的 ID: "); // 輸入要修改的 ID
                    int updateId = sc.nextInt(); sc.nextLine();
                    Category updateCat = dao.findById(updateId); // 查詢資料
                    if (updateCat == null) break; // 查無資料則跳出
                    System.out.print("新名稱: "); // 輸入新名稱
                    updateCat.setName(sc.nextLine());
                    System.out.print("新描述: "); // 輸入新描述
                    updateCat.setDescription(sc.nextLine());
                    dao.update(updateCat); // 呼叫 DAO 修改
                    break;

                case 3: // 查單筆
                    System.out.print("輸入要查詢的 ID: "); // 輸入 ID
                    int queryId = sc.nextInt(); sc.nextLine();
                    Category qCat = dao.findById(queryId); // 查詢
                    if (qCat != null) // 如果存在就印出
                        System.out.println("ID=" + qCat.getCategoryNo() + " 名稱=" + qCat.getName() + " 描述=" + qCat.getDescription());
                    break;

                case 4: // 查全部
                    List<Category> list = dao.findAll(); // 查全部
                    if (list.isEmpty()) System.out.println("目前無資料"); // 無資料提示
                    else for (Category c : list) // 有資料逐筆印出
                        System.out.println(c.getCategoryNo() + " / " + c.getName() + " / " + c.getDescription());
                    break;

                case 5: // 刪除
                    System.out.print("輸入要刪除的 ID: "); // 輸入 ID
                    dao.delete(sc.nextInt()); sc.nextLine(); // 呼叫 DAO 刪除
                    break;

                case 0: // 離開
                    System.out.println("Bye!"); System.exit(0); // 結束程式
                    break;

                default:
                    System.out.println("請輸入正確選項"); // 選項錯誤提示
            }
        }
    }
}
