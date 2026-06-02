# 登录注册功能使用说明

## 已创建的文件结构

### 后端代码
- `entity/User.java` - 用户实体类
- `repository/UserRepository.java` - 用户数据访问接口
- `service/UserService.java` - 用户业务逻辑层
- `config/SecurityConfig.java` - Spring Security 安全配置
- `controller/AuthController.java` - 认证控制器

### 前端页面
- `templates/login.html` - 登录页面
- `templates/register.html` - 注册页面
- `templates/home.html` - 首页（登录后）

### 配置文件
- `application.properties` - 已更新完整配置
- `build.gradle` - 已添加 Thymeleaf 依赖

## 启动应用

在终端中执行：
```bash
cd /Users/chen/Documents/spring/demo1
./gradlew bootRun
```

或者在 IDE 中直接运行 `Demo1Application.java`

## 访问应用

1. **登录页面**: http://localhost:8080/login
2. **注册页面**: http://localhost:8080/register
3. **首页**: http://localhost:8080/home (需要登录)

## 功能说明

### 1. 用户注册
- 访问登录页面，点击"立即注册"
- 输入用户名、密码和确认密码
- 注册成功后自动跳转到登录页面

### 2. 用户登录
- 输入用户名和密码
- 登录成功后进入首页
- 登录失败会显示错误提示

### 3. 退出登录
- 在首页点击右上角"退出登录"按钮
- 退出后返回登录页面

## 特性

✅ 密码 BCrypt 加密存储
✅ Spring Security 安全保护
✅ 表单验证（密码确认、重复用户名检测）
✅ 友好的错误提示
✅ 美观的渐变紫色主题设计
✅ 响应式布局
✅ Session 管理

## 数据库

确保 MySQL 服务正在运行，并已创建数据库：
```sql
CREATE DATABASE test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

应用启动后会自动创建 `users` 表。
