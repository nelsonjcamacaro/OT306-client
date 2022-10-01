package com.melvin.ongandroid.model.nosotrosActivities

import com.melvin.ongandroid.model.nosotrosActivities.model.MembersResponse
import retrofit2.Response
import retrofit2.http.GET

interface MembersService {
    @GET("/api/members")
   suspend fun getMembers(): Response<MembersResponse>

}