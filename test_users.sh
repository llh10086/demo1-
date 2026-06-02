#!/bin/bash

echo "========================================="
echo "用户查询功能测试脚本"
echo "========================================="
echo ""

echo "1. 检查应用是否在运行..."
if curl -s http://localhost:8080/login > /dev/null; then
    echo "✓ 应用正在运行"
else
    echo "✗ 应用未运行，请先启动应用: ./gradlew bootRun"
    exit 1
fi

echo ""
echo "2. 测试登录页面..."
if curl -s http://localhost:8080/login | grep -q "登录"; then
    echo "✓ 登录页面可访问"
else
    echo "✗ 登录页面访问失败"
fi

echo ""
echo "3. 测试用户列表页面（需要登录）..."
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/users)
if [ "$RESPONSE" = "302" ]; then
    echo "✓ 用户列表页面需要登录（返回302重定向）"
else
    echo "✗ 用户列表页面访问异常（返回 $RESPONSE）"
fi

echo ""
echo "========================================="
echo "测试完成！"
echo "========================================="
echo ""
echo "使用说明："
echo "1. 访问 http://localhost:8080/login"
echo "2. 使用账号登录：用户名=张三，密码=123456"
echo "3. 登录后点击'查看所有用户'按钮"
echo "4. 或直接访问 http://localhost:8080/users"
echo ""
