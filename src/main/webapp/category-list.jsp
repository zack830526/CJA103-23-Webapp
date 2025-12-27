<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 設定 JSP 頁面的內容類型為 UTF-8，語言為 Java -->

<%@ page import="java.util.List" %>
<!-- 匯入 List 類別 -->

<%@ page import="com.entity.Category" %>
<!-- 匯入 Category 類別 -->

<%
    // 從 request 取得 categories 列表，這是 Servlet 設定的屬性
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    // 從 request 取得錯誤訊息
    String errorMsg = (String) request.getAttribute("errorMsg");
%>

<html>
<head>
    <title>分類列表</title> <!-- HTML 標題 -->
</head>
<body>
<h2>分類列表</h2> <!-- 主標題 -->

<% if(errorMsg != null) { %>
    <p style="color:red;"><%= errorMsg %></p>
    <!-- 如果有錯誤訊息，顯示在頁面上，文字顏色為紅色 -->
<% } %>

<a href="<%=request.getContextPath()%>/category?action=new">新增分類</a>
<!-- 超連結，點擊跳到新增分類表單 -->

<br><br>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>編號</th>
        <th>名稱</th>
        <th>描述</th>
        <th>操作</th>
    </tr>
<% 
    // 如果 categories 不為 null 且有資料
    if(categories != null && !categories.isEmpty()) {
       for(Category cat : categories) { %>
    <tr>
        <td><%= cat.getCategoryNo() %></td> <!-- 顯示分類編號 -->
        <td><%= cat.getName() %></td> <!-- 顯示分類名稱 -->
        <td><%= cat.getDescription() %></td> <!-- 顯示分類描述 -->
        <td>
            <!-- 修改連結，帶上 action=edit 和 id -->
            <a href="<%=request.getContextPath()%>/category?action=edit&id=<%=cat.getCategoryNo()%>">修改</a> |
            <!-- 查看連結，帶上 action=view 和 id -->
            <a href="<%=request.getContextPath()%>/category?action=view&id=<%=cat.getCategoryNo()%>">查看</a> |
            <!-- 刪除連結，帶上 action=delete 和 id，並加 JS 確認刪除 -->
            <a href="<%=request.getContextPath()%>/category?action=delete&id=<%=cat.getCategoryNo()%>" 
               onclick="return confirm('確定要刪除嗎？');">刪除</a>
        </td>
    </tr>
<%     }
   } else { %>
    <tr>
        <td colspan="4">目前沒有資料</td> 
        <!-- 如果列表為空，顯示提示訊息 -->
    </tr>
<% } %>
</table>

</body>
</html>
