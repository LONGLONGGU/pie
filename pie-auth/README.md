#### 生成密钥库
- 在命令提示符下输入以下命令
```java
keytool -genkey -alias pie -keyalg RSA -keypass pie123456 -keystore pie.jks -storepass pie123456
```
- 对应的参数解析说明
```java
-genkey 生成密钥

-alias 别名

-keyalg 密钥算法

-keypass 密钥口令

-keystore 生成密钥库的存储路径和名称

-storepass 密钥库口令
```
- 输入命令后，直接敲回车，直到最一肯，输入Y
```java
keytool -genkey -alias pie -keyalg RSA -keypass pie123456 -keystore pie.jks -storepass pie123456
您的名字与姓氏是什么?
  [Unknown]:
您的组织单位名称是什么?
  [Unknown]:
您的组织名称是什么?
  [Unknown]:
您所在的城市或区域名称是什么?
  [Unknown]:
您所在的省/市/自治区名称是什么?
  [Unknown]:
该单位的双字母国家/地区代码是什么?
  [Unknown]:
CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown是否正确?
  [否]:  y

```


E:\dd-project\pie\pie-auth\src\main\resources>
