package com.project.travelmedrivers.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.travelmedrivers.entities.Travel

@Dao
interface TravelDao {
    @Query("select * from travels")
    fun getAll(): LiveData<List<Travel?>?>?

    @Query("select * from travels where id=:id")
    fun get(id: String?): LiveData<Travel?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(travel: Travel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(travels: List<Travel?>?)

    @Update
    fun update(travel: Travel?)

    @Delete
    fun delete(vararg travels: Travel?)

    @Query("delete from travels")
    fun clear()
}