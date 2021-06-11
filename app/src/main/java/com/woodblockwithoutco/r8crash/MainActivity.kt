package com.woodblockwithoutco.r8crash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.reflect.full.primaryConstructor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clazz = SomeObject::class
        Log.d("r8Bug", "Class: $clazz")

        val primaryConstructorInner = SomeObject.Inner::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor: $primaryConstructorInner")

        val primaryConstructorOuter = Outer::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor: $primaryConstructorOuter")
    }

}