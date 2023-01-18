package com.example.androidcourutine.utils

import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class HelperTest {

    //arrange
    lateinit var helper: Helper


    @Before
    fun setUp() {
        //reduce repeat code
        helper =Helper()
        println("Before test case")
    }

    @After
    fun tearDown() {
        println("After test case")
    }

    @Test
    fun isPalindrome() {

        //Act
        val result=helper.isPalindrome("hello")
        //assert
        assertEquals(false,result)
    }


    @Test
    fun isPalindrome_Level() {

        //arrange
        val helper = Helper()
        //Act
        val result=helper.isPalindrome("level")
        //assert
        assertEquals(true,result)
    }

    @Test
    fun isPalindrome_Level_another() {

        //arrange
        val helper = Helper()
        //Act
        val result=helper.isPalindrome("")
        //assert
        assertEquals(true,result)
    }
}