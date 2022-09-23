package com.melvin.ongandroid.model.InicioActivitys

class ActivityRepository(private val remoteDataSource: ActivityRemoteDataSource) {
    fun getActivity(listener: ResponseListener<Slides>){
        this.remoteDataSource.getActivity(listener)
    }
}