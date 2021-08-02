package com.herscher.swiftlydemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.herscher.swiftlydemo.specials.SpecialsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SpecialsListFragment.newInstance())
                .commit()
        }
    }
}