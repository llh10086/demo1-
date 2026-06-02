-- 清理测试用户数据的SQL脚本
-- 用于解决邮箱唯一约束冲突问题

USE test_db;

-- 查看当前所有用户
SELECT id, username, email, role FROM users;

-- 如果需要清空所有测试数据，取消下面这行的注释
-- DELETE FROM users WHERE username IN ('张三', '李四', '王五');

-- 如果只需要修复重复邮箱问题，可以执行以下操作：
-- 1. 删除重复的用户（保留ID最小的）
DELETE u1 FROM users u1
INNER JOIN users u2 
WHERE u1.id > u2.id 
AND u1.email = u2.email 
AND u1.email IS NOT NULL;

-- 2. 或者手动删除特定用户
-- DELETE FROM users WHERE username = '李四';

-- 再次查看清理后的用户
SELECT id, username, email, role FROM users;
