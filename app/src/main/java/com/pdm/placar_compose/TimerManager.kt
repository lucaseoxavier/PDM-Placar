package com.pdm.placar_compose

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState

class TimerManager(
    private val secondsState: MutableState<Int>,
    private val isTimerRunningState: MutableState<Boolean>,
) {
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var timeLimit = 2700 // 45 min
    private var extraTime = 0
    private var isSecondHalf = false

    init {
        // Initialize secondsState with the current value from the TimerManager
        secondsState.value = getTimerValue()
    }

    fun startTimer() {
        if (getTimerValue() < timeLimit + extraTime) {
            isTimerRunningState.value = true
            timerRunnable = object : Runnable {
                override fun run() {
                    secondsState.value++
                    if (getTimerValue() >= timeLimit + extraTime) {
                        if (!isSecondHalf) {
                            isSecondHalf = true
                            timeLimit *= 2
                            secondsState.value -= extraTime
                            extraTime = 0
                        }
                        stopTimer()
                    } else {
                        handler.postDelayed(this, 1000)
                    }
                }
            }
            timerRunnable?.let { handler.post(it) }
        }
    }

    fun stopTimer() {
        isTimerRunningState.value = false
        timerRunnable?.let {
            handler.removeCallbacks(it)
        }
        timerRunnable = null
    }

    fun resetTimer() {
        isTimerRunningState.value = false
        timerRunnable?.let {
            handler.removeCallbacks(it)
        }
        timerRunnable = null
        secondsState.value = 0
        if (isSecondHalf) timeLimit /= 2
        isSecondHalf = false
    }

    fun getTimerValue(): Int {
        return secondsState.value
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunningState.value
    }

    fun isSecondHalf(): Boolean {
        return isSecondHalf
    }

    fun setupExtraTime(seconds: Int) {
        extraTime = seconds
    }

    // New method to format time (replace this with your own formatting logic)
    fun getFormattedTime(): String {
        val minutes = getTimerValue() / 60
        val seconds = getTimerValue() % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
