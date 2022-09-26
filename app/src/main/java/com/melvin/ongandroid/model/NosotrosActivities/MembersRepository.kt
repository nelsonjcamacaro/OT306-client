package com.melvin.ongandroid.model.NosotrosActivities

import com.melvin.ongandroid.model.InicioActivitys.ResponseListener

class MembersRepository(private val membersRemoteDataSource: MembersRemoteDataSource){
    suspend fun getMembers(listener: ResponseListener<Members>){
        this.membersRemoteDataSource.getMembers(listener)

    }
}