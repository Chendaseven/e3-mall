<html>
<head>
	<title>freemarkerTest:</title>
</head>
<body>
	学生信息：
	<label>学号：</label>${stu.id}<br>
	<label>姓名：</label>${stu.name}<br>
	<label>年龄：</label>${stu.age}<br>
	取集合中的数据：
	<table border=1>
		<tr>
			<td>学号</td><td>姓名</td><td>年龄</td>
		</tr>
	<#list studentList as student>
	<#if student_index % 2 == 0>
	<tr bgcolor="blue">
	<#else>
	<tr bgcolor="red">
	</#if>
		<td>${student_index}</td><td>${student.id}</td><td>${student.name}</td><td>${student.age}</td>
		</tr>
	</#list>
	</table>
	<br>
	当前日期：${date?date}<br>
	当前时间：${date?time}<br>
	当前日期和时间：${date?datetime}<br>
	自定义日期格式：${date?string("yyyyMM/dd HH:mm:ss")}<br>
	<br>
	null值的处理有两种形式，使用！，当myval为值为空时，输出！之后的默认值，当myval不为空时，直接输出myval的值
	<br>
	null值的处理：${myval!"myval为null"}
	<br>
	或者使用if else来处理null值
	<br>
	<#if myval??>
		myval不为空
	<#else>
		myval为空
	</#if>
	<br>
	引用模板测试：<br>
	此时需要将hello.ftl中的字段值加在dataModel中put进来<br>
	<#include "hello.ftl">
	
</body>
</html>