package com.all4drive.config

class DatabaseConfig {
    object dbconfig {
        val url = "jdbc:mysql://localhost:3306/tfshop?useUnicode=true&serverTimezone=UTC&useSSL=false"
        val user = "root"
        val driver = "com.mysql.cj.jdbc.Driver"
        val password = "86818682"
    }
}