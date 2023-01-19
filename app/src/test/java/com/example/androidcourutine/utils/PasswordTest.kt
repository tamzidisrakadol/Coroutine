package com.example.androidcourutine.utils


import org.junit.Assert
import org.junit.Test

class PasswordTest {

    @Test
    fun validatePassword_InputBlank(){
        val sut = Helper()
        val result = sut.validatePassword("   ")
        Assert.assertEquals("password should be must required",result)
    }


    @Test
    fun validatePassword_moreThanSixDigit(){
        val sut = Helper()
        val result = sut.validatePassword("abff")
        Assert.assertEquals("password length should be greater than 6",result)
    }


    @Test
    fun validatePassword_moreThan15Digit(){
        val sut = Helper()
        val result = sut.validatePassword("ajsbdkjbaskdh16432163434sdfsadf")
        Assert.assertEquals("Password length should be not more than 15",result)
    }
}