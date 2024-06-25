package com.all4drive.config

class ExampleConfig {
    class DatabaseConfig {
        object dbconfig {
            // Ссылка на базу данных
            val url = "jdbc:mysql://localhost:3306/db?useUnicode=true&serverTimezone=UTC&useSSL=false"

            // Имя пользователя базы данных
            val user = "name"

            // Драйвер для поключения к базе данных
            val driver = "com.mysql.cj.jdbc.Driver"

            // Пароль пользоваля базы данных
            val password = "password"
        }
    }
}