```java
   package com.zkteco;

   import java.util.List;

   public class TestZKTECO {
       public static void main(String[] args) {
           // 测试 U160
           testDevice("U160", "192.168.1.201", 4370);
           // 测试 xFace600（替换为实际 IP）
           testDevice("xFace600", "192.168.1.202", 4370); // 请提供 xFace600 的 IP
       }

       private static void testDevice(String deviceType, String ip, int port) {
           System.out.println("测试设备: " + deviceType + " (" + ip + ":" + port + ")");
           ZKTECO zk = new ZKTECO(ip, port);
           try {
               // 测试连接
               if (zk.connect()) {
                   System.out.println("连接成功: " + ip);
               } else {
                   System.out.println("连接失败: " + ip);
                   return;
               }

               // 测试设置时间
               try {
                   zk.setTime(System.currentTimeMillis());
                   System.out.println("设置时间成功: " + ip);
               } catch (Exception e) {
                   System.out.println("设置时间失败: " + e.getMessage());
               }

               // 测试禁用/启用设备
               try {
                   zk.disableDevice();
                   System.out.println("禁用设备成功: " + ip);
                   Thread.sleep(1000); // 等待1秒
                   zk.enableDevice();
                   System.out.println("启用设备成功: " + ip);
               } catch (Exception e) {
                   System.out.println("设备控制失败: " + e.getMessage());
               }

               // 测试获取打卡数据
               try {
                   List<ZKTECO.AttendanceRecord> records = zk.getAttendance();
                   System.out.println("获取打卡记录: " + records.size() + " 条");
                   for (ZKTECO.AttendanceRecord record : records) {
                       System.out.println("用户ID: " + record.getUserId() +
                               ", 时间: " + record.getTime() +
                               ", 验证方式: " + record.getVerifyMode() +
                               ", 进出模式: " + record.getInOutMode());
                   }
               } catch (Exception e) {
                   System.out.println("获取打卡记录失败: " + e.getMessage());
               }

               // 测试获取用户信息
               try {
                   List<ZKTECO.User> users = zk.getUsers();
                   System.out.println("获取用户: " + users.size() + " 个");
                   for (ZKTECO.User user : users) {
                       System.out.println("用户ID: " + user.getUserId() +
                               ", 姓名: " + user.getName());
                   }
               } catch (Exception e) {
                   System.out.println("获取用户信息失败: " + e.getMessage());
               }

           } catch (Exception e) {
               System.out.println("测试失败: " + e.getMessage());
           } finally {
               zk.disconnect();
               System.out.println("断开连接: " + ip);
           }
       }
   }
   ```