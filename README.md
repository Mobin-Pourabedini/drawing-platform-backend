# Louie Backend

این پروژه یک بک‌اند ساده برای یک اپلیکیشن ری‌اکتی است که امکانات زیر را فراهم می‌کند:
- احراز هویت و لاگین کاربران
- امکان نقاشی کشیدن در سمت کلاینت
- ذخیره و بازیابی نقاشی‌های کاربر در سرور
- امکان export کردن نقاشی‌ها

پروژه با استفاده از Spring Boot پیاده‌سازی شده و از JWT برای احراز هویت استفاده می‌کند.

---

## قابلیت‌ها

- لاگین با JWT (انقضای توکن: ۷ روز)
- ذخیره‌ی داده‌ی نقاشی‌ها (canvas_data) در قالب سریالیزه
- export نقاشی‌ها از سرور

---


## کاربران آزمایشی

برای تست می‌توانید از چهار کاربر زیر استفاده کنید:

| نام کاربری | رمز عبور     |
|-----------|-------------|
| artist1   | password123 |
| artist2   | password123 |
| painter   | password123 |
| designer  | password123 |

---

## پیکربندی دیتابیس

در فایل `application.properties` تنظیمات اتصال به دیتابیس PostgreSQL به این صورت است:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/louie_db
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=password
```

> **نکته:** در حال حاضر مقدار `jwt_secret` نیز به صورت نما‌یشی در همین فایل قرار دارد. در محیط عملیاتی باید آن را ایمن‌سازی کنید.

---

## مدل‌های داده

1. User
    - username
    - password
    - ارتباط یک‌به‌یک با Painting

2. Painting
    - id
    - owner (اطلاعات کاربر)
    - canvas_data (سریالیزه‌ی خطوط و اشکال نقاشی)

---

## APIها

1. POST `/api/auth/login`
    - ورودی: `{ "username": "...", "password": "..." }`
    - خروجی: `{ "token": "JWT_TOKEN_HERE" }`

2. POST `/api/painting/my`
    - توضیح: دریافت لیست نقاشی‌های کاربر
    - هدر: `Authorization: Bearer JWT_TOKEN_HERE`

3. POST `/api/painting/save`
    - توضیح: ذخیره یا بروزرسانی یک نقاشی
    - هدر: `Authorization: Bearer JWT_TOKEN_HERE`
    - ورودی: `{ "canvas_data": "SERIALIZED_DATA" }`

> برای تست سریع APIها می‌توانید از فایل [Postman Collection](./louie.postman_collection.json) این پروژه استفاده کنید.

---


در این پروژه بخشی از پیاده‌سازی‌ها با کمک هوش مصنوعی (AI) انجام شده است. در ادامه موارد استفاده و همچنین جاهایی که از پیشنهادات AI صرف‌نظر کردیم آورده شده است.

1. بخش‌هایی که با کمک هوش مصنوعی پیاده شدند
    - ایجاد و کانفیگ JWT Filter  
      با هدایت هوش مصنوعی، فیلتر احراز هویت JWT (`jwtFilter`) پیاده‌سازی شد تا درخواست‌ها را بررسی و توکن‌ها را اعتبارسنجی کند.
    - پیاده‌سازی SecurityConfig  
      ورژن جدید Spring Boot برخی از متدها و کلاس‌های امنیتی قبلی را deprecated کرده بود. با کمک AI، تنظیمات امنیتی (SecurityConfig) بازنویسی شدند تا با قابلیت‌های فعلی فریم‌ورک مطابقت داشته باشد.
    - تنظیمات احراز هویت و مسیرهای مجاز  
      مسیریابی و تعریف سطوح دسترسی برای اندپوینت‌ها (مثل `/api/auth/login` و `/api/painting/**`) با راهنمایی هوش مصنوعی انجام شد تا از بهترین شیوه‌ها (best practices) بهره ببرد.

2. مواردی که از پیشنهادات هوش مصنوعی صرف‌نظر شد
    - استفاده از Lombok به جای متدهای getter/setter دستی  
      در حالی که AI پیشنهاد کرد تمامی مدل‌ها (`User` و `Painting`) با متدهای getter و setter رایج پیاده شوند، ما برای خوانایی و کاهش boilerplate از Lombok (@Data) استفاده کردیم.
    - استفاده از Constructor Injection به جای @Autowired  
      هوش مصنوعی در برخی کامپوننت‌ها استفاده از annotation `@Autowired` را پیشنهاد داد، اما ما به دلیل شفافیت بیشتر و تست‌پذیری بالاتر، روش constructor injection را انتخاب کردیم.

---
