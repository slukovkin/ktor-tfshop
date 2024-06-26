package com.all4drive.config

object ExampleDatabaseConfig {
    // Ссылка на базу данных
    const val url = "jdbc:mysql://localhost:3306/db?useUnicode=true&serverTimezone=UTC&useSSL=false"

    // Имя пользователя базы данных
    const val user = "name"

    // Драйвер для поключения к базе данных
    const val driver = "com.mysql.cj.jdbc.Driver"

    // Пароль пользоваля базы данных
    const val password = "password"
}