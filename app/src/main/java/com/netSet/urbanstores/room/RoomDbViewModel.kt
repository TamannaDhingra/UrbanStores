package com.netSet.urbanstores.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netSet.urbanstores.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomDbViewModel(val mainActivity: MainActivity) : ViewModel() {

    val showAllProduct:LiveData<List<CartEntity>>
    lateinit var repository:RoomDbRepo

    init {
        val cartDao=CartDatabase.getDatabase(mainActivity).CartDao()
         repository= RoomDbRepo(cartDao)
         showAllProduct=repository.showAllProducts
    }

       fun insertProduct(cartEntity: CartEntity){
       viewModelScope.launch(Dispatchers.IO) {
          repository.insertProducts(cartEntity)
    }
    }

    fun deleteProduct(cartEntity: CartEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteProducts(cartEntity)
        }
    }

    fun deleteAllProduct(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllProducts()
        }
    }
}