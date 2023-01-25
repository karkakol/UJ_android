package com.example.a8zad
import com.example.a8zad.utils.EmailValidator


import org.junit.Assert.*
import org.junit.Test


class EmailValidateTests {

    @Test
    fun correctEmail1() {
        assertEquals(true, EmailValidator.validate("test@test.pl"))
    }

    @Test
    fun correctEmail2() {
        assertEquals(true, EmailValidator.validate("very.”(),:;<>[]”.VERY.”very@\\\\ \"very”.unusual@strange.example.com"))
    }

    @Test
    fun correctEmail3() {
        assertEquals(true, EmailValidator.validate("very.unusual.”@”.unusual.com@example.com"))
    }

    @Test
    fun emptyEmail() {
        assertEquals(false, EmailValidator.validate(""))
    }

    @Test
    fun incorrectEmail1() {
        assertEquals(false, EmailValidator.validate("1124"))
    }
    @Test
    fun incorrectEmail2() {
        assertEquals(false, EmailValidator.validate("Abc..123@example.com"))
    }
    @Test
    fun incorrectEmail3() {
        assertEquals(false, EmailValidator.validate("email@example..com"))
    }
    @Test
    fun incorrectEmail4() {
        assertEquals(false, EmailValidator.validate("email@111.222.333.44444"))
    }
    @Test
    fun incorrectEmail5() {
        assertEquals(false, EmailValidator.validate("email@example.web"))
    }
    @Test
    fun incorrectEmail6() {
        assertEquals(false, EmailValidator.validate("email@-example.com"))
    }



}