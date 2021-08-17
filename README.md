# apple-ads-java-demo
Apple Ads Campaign Management API, OAuth, Report API examples

## 邀请 API 用户

![Invite User](assets/invite-api-user.jpg?raw=true "Invite User")

## 上传 Public Key

![Upload a Public Key](assets/upload-public-key.jpg?raw=true "Upload a Public Key")

## 私钥转格式

```bash

openssl ecparam -genkey -name prime256v1 -noout -out private-key.pem

openssl pkcs8 -topk8 -inform pem -in private-key.pem -outform pem -nocrypt -out private-key-new.pem

```