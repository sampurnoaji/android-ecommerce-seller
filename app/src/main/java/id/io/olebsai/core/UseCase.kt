package id.io.olebsai.core

interface UseCase<Params, out T> {
    suspend operator fun invoke(params: Params): T
}