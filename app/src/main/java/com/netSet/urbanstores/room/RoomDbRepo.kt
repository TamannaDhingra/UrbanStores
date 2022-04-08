package com.netSet.urbanstores.room

import androidx.lifecycle.LiveData

class RoomDbRepo(val cartDao: CartDao) {

    val showAllProducts:LiveData<List<CartEntity>> = cartDao.showAllProduct()

     fun insertProducts(cartEntity: CartEntity){
    cartDao.insertProduct(cartEntity)
    }

    fun deleteProducts(cartEntity: CartEntity){
        cartDao.deleteProduct(cartEntity)
    }

    fun deleteAllProducts(){
        cartDao.deleteAllProducts()
    }
}