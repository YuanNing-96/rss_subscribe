package org.example.mirai.plugin

import org.junit.Assert
import org.junit.Test

class ListReverseTest {

    @Test
    fun listAsReversedTest(){
        var a = listOf<Int>(1,2,3)
        var b = a.asReversed()

        Assert.assertEquals(b, listOf(3,2,1))
    }
}