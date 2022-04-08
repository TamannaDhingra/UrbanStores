package com.netSet.urbanstores.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.netSet.urbanstores.activities.MainActivity
import com.netSet.urbanstores.room.RoomDbViewModel

class MainViewModelFactory(val mainActivity: MainActivity) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainActivity) as T
        }
        else if (modelClass.isAssignableFrom(RoomDbViewModel::class.java)) {
            return RoomDbViewModel(mainActivity) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}