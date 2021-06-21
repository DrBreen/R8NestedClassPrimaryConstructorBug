package com.woodblockwithoutco.r8crash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clazz = SomeObject::class
        Log.d("r8Bug", "Class: $clazz")

        val outerMetadata = SomeObject::class.java.annotations.firstOrNull { it is Metadata } as? Metadata
        Log.d("r8Bug", "Outer metadata present: ${outerMetadata != null}")

        val innerMetadata = SomeObject.Inner::class.java.annotations.firstOrNull { it is Metadata } as? Metadata
        Log.d("r8Bug", "Inner metadata present: ${innerMetadata != null}")

        val primaryConstructorInner = SomeObject.Inner::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor inner: $primaryConstructorInner")

        val primaryConstructorOuter = Outer::class.primaryConstructor
        Log.d("r8Bug", "Primary constructor outer: $primaryConstructorOuter")

        if (innerMetadata != null) {
            val protoInnerClass = JvmProtoBufUtil.readClassDataFrom(innerMetadata.data1, innerMetadata.data2).second
            Log.d("r8Bug", "Constructor flags: ${protoInnerClass.constructorList.first().flags}")

            val primaryConstructor = protoInnerClass.constructorList.firstOrNull { !Flags.IS_SECONDARY.get(it.flags) }
            Log.d("r8Bug", "Proto primary constructor: $primaryConstructor")

            val constructor = SomeObject.Inner::class.constructors.first()
            Log.d("r8Bug", "Inner constructor: ${constructor.javaClass}")

            val kFunctionImplClazz = Class.forName("kotlin.reflect.jvm.internal.KFunctionImpl")
            val constructorDescriptorMethod = kFunctionImplClazz.methods.first { it.name.contains("getDescriptor") }

            val descriptor = constructorDescriptorMethod.invoke(constructor)
            Log.d("r8Bug", "Descriptor: $descriptor, descriptor class: ${descriptor.javaClass}")

            val isPrimaryMethod = descriptor.javaClass.methods.first { it.name.contains("isPrimary") }
            val isPrimary = isPrimaryMethod.invoke(descriptor)
            Log.d("r8Bug", "Is descriptor primary: $isPrimary")

        }
    }

}