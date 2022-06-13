package id.io.android.olebsai.core

interface NoParamsUseCase<T> {
    suspend operator fun invoke(): T
}