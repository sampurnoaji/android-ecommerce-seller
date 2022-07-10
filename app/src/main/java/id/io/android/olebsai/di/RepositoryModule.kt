package id.io.android.olebsai.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import id.io.android.olebsai.data.repository.ShopRepositoryImpl
import id.io.android.olebsai.data.repository.UserRepositoryImpl
import id.io.android.olebsai.domain.repository.ShopRepository
import id.io.android.olebsai.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindShopRepository(shopRepositoryImpl: ShopRepositoryImpl): ShopRepository
}