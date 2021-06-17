package com.woodblockwithoutco.r8crash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clazz = SomeObject::class
        Log.d("r8Bug", "Class: $clazz")

        val outerMetadata = SomeObject::class.java.annotations.firstOrNull { it is Metadata }
        Log.d("r8Bug", "Outer metadata present: ${outerMetadata != null}")

        val innerMetadata = SomeObject.Inner::class.java.annotations.firstOrNull { it is Metadata }
        Log.d("r8Bug", "Inner metadata present: ${innerMetadata != null}")

        val primaryConstructorInner = SomeObject.Inner::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor: $primaryConstructorInner")

        val primaryConstructorOuter = Outer::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor: $primaryConstructorOuter")
    }

}