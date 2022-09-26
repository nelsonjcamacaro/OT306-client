package com.melvin.ongandroid.model

import com.melvin.ongandroid.utils.ResultState

class OngRepository(private val remoteDataSource: OngRemoteDataSource) {

   suspend fun getTestimonialsList():List<Testimonial>?{
       return try {
           remoteDataSource.getTestimonials()
       } catch (e:Exception){
           ResultState.Error(e)
           null
       }
   }

    suspend fun SendContactMessage(post: ContactMessageDto) {
        try {
            remoteDataSource.SendContactMessage(post)
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }
}
