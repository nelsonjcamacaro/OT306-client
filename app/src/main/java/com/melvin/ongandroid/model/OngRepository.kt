package com.melvin.ongandroid.model

class OngRepository(private val remoteDataSource: OngRemoteDataSource) {

   suspend fun getTestimonialsList(netWorkResponse: NetWorkResponse<List<Testimonial>>){
       return remoteDataSource.getTestimonials(netWorkResponse)
    }
}
