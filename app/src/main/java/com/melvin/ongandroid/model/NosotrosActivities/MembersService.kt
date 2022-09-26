package com.melvin.ongandroid.model.NosotrosActivities

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MembersService {
    @GET("/api/members")
   suspend fun getMembers(): Response<Members>

}