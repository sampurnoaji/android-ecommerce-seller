package id.io.android.seller.domain.repository

import id.io.android.seller.domain.model.user.User

interface UserRepository {
    fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    suspend fun getUser(id: Int): User
}