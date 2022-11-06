package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private var firstArg: String = ""
    private var secondArg: String = ""

    private var operator: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun _round(value: Double): Double {

        return (round(value * 1000000) / 1000000)
    }

    fun clearValuesAndWriteOnDisplay(str: String) {
        calculatorDisplay.text = str
        operator = ""
        secondArg = ""
        firstArg = ""
    }

    fun runOperation() {
        val f = firstArg.toDouble()
        val s = secondArg.toDouble()
        var result = 0.0

        when (operator) {
            "+" -> result = f + s
            "-" -> result = f - s
            "x" -> result = f * s
            "/" -> {
                if (s == 0.0) {
                    clearValuesAndWriteOnDisplay("Err")
                    return
                } else {
                    result = f / s
                }

            }
            "x^y" -> result =f.pow(s)
            "%" ->result= (f * s)/100.0
            else -> result=0.0
        }

        firstArg = _round(result).toString()
        operator = ""
        secondArg = ""
        calculatorDisplay.text = firstArg

    }

    fun printOnDisplay(value: String?) {
        calculatorDisplay.text = value
    }

    fun numberAction(button: View) {
        if (button !is Button || firstArg.length > 10) return
        if (operator.isEmpty()) {
            firstArg += button.text.toString()
            printOnDisplay(firstArg)
        } else {
            secondArg += button.text.toString()
            printOnDisplay(secondArg)
        }

    }

    fun operatorAction(button: View) {
        if (button !is Button || firstArg.isEmpty()) return
        if (operator.isNotEmpty() && secondArg.isNotEmpty()) {
            runOperation()
        } else {
            printOnDisplay("")
        }
        operator = button.text.toString()
    }

    fun allClearAction(button: View) {
        clearValuesAndWriteOnDisplay("")
    }

    fun equalsAction(button: View) {
        if (button !is Button) return
        if (firstArg.isEmpty() || operator.isEmpty() || secondArg.isEmpty()) return
        runOperation()

    }

    fun onePerimeterFunction(condition: (Double) -> Boolean, function: (Double) -> Double) {
        if (operator.isEmpty()) {
            if (firstArg.isEmpty()) return
            val parsed = firstArg.toDouble()
            if (condition(parsed)) {
                clearValuesAndWriteOnDisplay("Err")
                return
            }
            firstArg = _round(function(parsed)).toString()
            calculatorDisplay.text = firstArg
        } else {
            if (secondArg.isEmpty()) return
            val parsed = secondArg.toDouble()
            if (condition(parsed)) {
                clearValuesAndWriteOnDisplay("Err")
                return
            }
            secondArg = _round(function(parsed)).toString()
            calculatorDisplay.text = secondArg
        }
    }

    fun lessThanZero(v: Double): Boolean {
        return v < 0
    }

    fun lessOrEqualZero(v: Double): Boolean {
        return v <= 0
    }

    fun _log(v: Double): Double {
        return log(v, 10.0)
    }

    fun sqrtAction(button: View) {
        if (button !is Button) return
        onePerimeterFunction(::lessThanZero, ::sqrt)
    }

    fun logAction(button: View) {
        if (button !is Button) return
        onePerimeterFunction(::lessOrEqualZero, ::_log)
    }

    fun negationAction(button: View) {
        if (button !is Button) return
        if (operator.isEmpty()) {
            if (firstArg.isEmpty()) return
            firstArg = (firstArg.toDouble() * (-1)).toString()
            calculatorDisplay.text = firstArg
        } else {
            if (secondArg.isEmpty()) return
            secondArg = (secondArg.toDouble() * (-1)).toString()
            calculatorDisplay.text = secondArg
        }
    }
}