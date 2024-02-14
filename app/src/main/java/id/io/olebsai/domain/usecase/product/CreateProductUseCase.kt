package id.io.olebsai.domain.usecase.product

import id.io.olebsai.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(private val repository: ProductRepository) {

}