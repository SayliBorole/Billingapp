package com.example.tomatotrial


import androidx.lifecycle.ViewModel


class MainViewModel :ViewModel() {
    var tot= BilledItems()
    fun maintainer(tot:BilledItems):BilledItems{
        return tot
    }
}