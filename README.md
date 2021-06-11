# What is this?
This repository contains sample project that reproduces a bug that causes Kotlin primary constructor to be null at runtime after R8 obfuscation is applied.

# How to reproduce the bug?
Run the project and watch logcat output with `r8Bug` tag.
To verify that bug is not happening in older R8 versions, change `classpath "com.android.tools:r8:2.2.64"` to `classpath "com.android.tools:r8:2.2.60"` in `build.gradle`.