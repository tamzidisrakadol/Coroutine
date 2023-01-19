package com.example.androidcourutine.utils

import org.junit.Assert
import org.junit.Test

class StringTest {

    @Test
    fun testStringReverse(){
        val sut = Helper()
        val result = sut.reverseString("abc")
        Assert.assertEquals("cba",result)
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun testStringReverseIfNull(){
        val sut = Helper()
        val result = sut.reverseString(null)

    }
}