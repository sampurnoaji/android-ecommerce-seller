package id.io.olebsai.data.source.remote.order

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.io.olebsai.domain.model.order.Order
import javax.inject.Inject

class OrderPagingSource @Inject constructor(
    private val remoteDataSource: OrderRemoteDataSource,
) : PagingSource<Int, Order>() {

    companion object {
        private const val STARTING_KEY = 1
        const val ITEMS_PER_PAGE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        val position = params.key ?: STARTING_KEY

        return try {
            val orders = remoteDataSource.getDoneOrders(
                page = position,
                size = params.loadSize,
            )
            val endOfPaginationReached = orders.isEmpty()
            LoadResult.Page(
                data = orders.map { it.toDomain() },
                prevKey = if (position == STARTING_KEY) null else position - 1,
                nextKey = if (endOfPaginationReached) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}