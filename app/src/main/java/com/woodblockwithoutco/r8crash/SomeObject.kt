package com.woodblockwithoutco.r8crash

import androidx.annotation.Keep

object SomeObject {

    @Keep
    data class Inner(
        val someField: String
    )


}
@Keep
data class Outer(
    val someField: String
)