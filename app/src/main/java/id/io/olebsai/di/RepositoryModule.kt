package id.io.olebsai.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.io.olebsai.data.repository.OrderRepositoryImpl
import id.io.olebsai.data.repository.ProductRepositoryImpl
import id.io.olebsai.data.repository.ShopRepositoryImpl
import id.io.olebsai.data.repository.UserRepositoryImpl
import id.io.olebsai.domain.repository.OrderRepository
import id.io.olebsai.domain.repository.ProductRepository
import id.io.olebsai.domain.repository.ShopRepository
import id.io.olebsai.domain.repository.UserRepository
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

    @Singleton
    @Binds
    fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun bindsOrderRepository(repositoryImpl: OrderRepositoryImpl): OrderRepository
}