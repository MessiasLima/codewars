package io.github.messiaslima.codewars.config

import android.app.Application
import io.github.messiaslima.codewars.repository.common.database.CodewarsDatabase

class CodewarsApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        CodewarsDatabase.initDatabase(this)
    }
}