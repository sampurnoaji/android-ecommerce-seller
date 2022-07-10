package id.io.android.olebsai.core

interface UseCase<Params, out T> {
    suspend operator fun invoke(params: Params): T
}