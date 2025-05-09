<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculation Result</title>
</head>
<body>
    <h2>Calculation Result</h2>
    <p><%= request.getAttribute("num1") %> 
       <%= request.getAttribute("operation").equals("add") ? "+" : 
           request.getAttribute("operation").equals("subtract") ? "-" : 
           request.getAttribute("operation").equals("multiply") ? "×" : "÷" %> 
       <%= request.getAttribute("num2") %> = <%= request.getAttribute("result") %>
    </p>
    <a href="index.jsp">Back to Calculator</a>
</body>
</html>
