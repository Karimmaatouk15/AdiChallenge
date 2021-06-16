package com.example.adichallenge


fun Float?.formatCurrency(): String {
    return if (this != null && this > 0) {
        String.format("$%.2f", this)
    } else if (this != null && this < 0) {
        String.format("-$%.2f", -this)
    } else {
        String.format("$%.2f", 0f)
    }
}