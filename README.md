# apple-ads-java-demo

Apple Ads 广告管理 API 的 Java 代码示例，包括 OAuth 实施参考。Apple Ads Campaign Management API, OAuth, Report API examples.

## 重点注意

通过官方文档中的说明生成的私钥，需转格式，代码中使用 private-key-new.pem

```bash

openssl pkcs8 -topk8 -inform pem -in private-key.pem -outform pem -nocrypt -out private-key-new.pem

```

## 邀请 API 用户

![Invite User](assets/invite-api-user.jpg?raw=true "Invite User")

## 上传 Public Key

![Upload a Public Key](assets/upload-public-key.jpg?raw=true "Upload a Public Key")
