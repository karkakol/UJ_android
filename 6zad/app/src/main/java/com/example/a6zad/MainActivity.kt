package com.example.a6zad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a6zad.ui.main.MainFragment
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Realm.init(this)
        val realmName: String = "My Project"
        val config = RealmConfiguration.Builder().name(realmName).schemaVersion(1).build()
        val backgroundThreadRealm : Realm = Realm.getInstance(config)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}