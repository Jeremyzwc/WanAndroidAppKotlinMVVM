package com.qisan.wanandroid.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScanRecordDao {

    //插入多个数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(scanRecordList: MutableList<ScanRecordEnity>)

    //插入单个数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scanRecordEnity: ScanRecordEnity)

    //获取所有数据
    @Query("SELECT * FROM ${TableNameConstant.SCAN_RECORD_TABLE}")
    fun queryAll(): MutableList<ScanRecordEnity>

    //根据id获取一个数据
    @Query("SELECT * FROM ${TableNameConstant.SCAN_RECORD_TABLE} WHERE id = :id")
    fun getScanRecordById(id: Int): ScanRecordEnity?

    //删除表中所有数据
    @Query("DELETE FROM ${TableNameConstant.SCAN_RECORD_TABLE}")
    suspend fun deleteAll()

    //通过id修改数据
    @Query("UPDATE ${TableNameConstant.SCAN_RECORD_TABLE} SET link=:link WHERE id=:id")
    suspend fun updateData(id: Long, link: String)

    //根据Id删除数据
    @Query("DELETE FROM ${TableNameConstant.SCAN_RECORD_TABLE}  WHERE id=:id")
    suspend fun deleteById(id: Long)

    //根据属性值删除数据
    @Query("DELETE FROM ${TableNameConstant.SCAN_RECORD_TABLE}  WHERE link=:link")
    suspend fun deleteByName(link: String)

}