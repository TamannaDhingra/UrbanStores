package com.netSet.urbanstores.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CartDao {

    /**
     * ignore to add same user multiple times
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(cartEntity: CartEntity?)

   @Query("Select * from products_table order by id ASC")
   fun showAllProduct():LiveData<List<CartEntity>>

   @Delete
   fun deleteProduct(cartEntity: CartEntity?)

   @Query("delete from products_table")
   fun deleteAllProducts()

}


/*
interface UserDao {
    @Insert
    fun registerUser(userEntity: UsrEntity?)

    @Query("Select * from users where user_email= (:useremail) and password=(:password)")
    fun login(useremail: String?, password: String?): UsrEntity?

    @Query("Select * from users where user_email like :useremail1 ")
    fun checkUserexist(useremail1: String?): UsrEntity?

    @Query("SELECT * FROM users")
    fun getuserlist(): List<UsrEntity?>?

    @Delete
    fun deleteUser(userEntity: UsrEntity?)

    @Update
    fun updateUser(userEntity: UsrEntity?)

    @Query("select * from users where id like :idx")
    fun getId(idx: Int): UsrEntity?

    @get:Query("Select * from users Order by userName asc")
    val liveuserlist: LiveData<List<Any?>?>?
}
*/
