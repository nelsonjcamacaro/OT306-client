package com.melvin.ongandroid.model

import com.melvin.ongandroid.utils.ResultState

class OngRepository(private val remoteDataSource: OngRemoteDataSource) {

   suspend fun getTestimonialsList(netWorkResponse: NetWorkResponse<List<Testimonial>>){
       try {
           remoteDataSource.getTestimonials(netWorkResponse)
       } catch (e:Exception){
           ResultState.Error(e)
       }
   }
}
