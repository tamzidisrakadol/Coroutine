package com.example.androidcourutine.utils

class Helper {

    fun isPalindrome(input:String):Boolean{
        var i=0
        var j=input.length-1
        var result = true


        while (i<j){
            if (input[i]!=input[j]){
                result=false
                break
            }
            i++
            j--
        }
        return result
    }


    fun validatePassword(input: String)=when{
        input.isBlank()-> {
            "password should be must required"
        }
        input.length < 6->{
            "password length should be greater than 6"
        }
        input.length >15 -> {
            "Password length should be not more than 15"
        }
        else->{
            "Valid"
        }
    }


    fun reverseString(input: String?):String{
        if (input==null){
            throw java.lang.IllegalArgumentException("Input String is required")
        }
        var chars = input.toCharArray()
        var i=0
        var j = chars.size-1
        while (i<j){
            val temp = chars[i]
            chars[i] = chars[j]
            chars[j]=temp
            i++
            j--
        }
        return chars.joinToString("")
    }
}