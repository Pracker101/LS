<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculator</title>
</head>
<body>
    <h2>Simple Calculator</h2>
    <form action="CalculatorClientServlet" method="post">
        <input type="number" name="num1" step="any" required>
        <select name="operation">
            <option value="add">+</option>
            <option value="subtract">-</option>
            <option value="multiply">×</option>
            <option value="divide">÷</option>
        </select>
        <input type="number" name="num2" step="any" required>
        <input type="submit" value="Calculate">
    </form>
</body>
</html>
