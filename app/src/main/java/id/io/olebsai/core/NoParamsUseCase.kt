package id.io.olebsai.core

interface NoParamsUseCase<T> {
    suspend operator fun invoke(): T
}