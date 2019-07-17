package reactivecircus.blueprint.interactor.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

@ExperimentalCoroutinesApi
class CalculateSquare(
    override val dispatcher: CoroutineDispatcher
) : SuspendingInteractor<CalculateSquare.Params, Int>() {
    override suspend fun doWork(params: Params): Int {
        delay(1000L)
        return params.value * params.value
    }

    class Params(val value: Int) : InteractorParams
}

@ExperimentalCoroutinesApi
class FailingSuspendingInteractor(
    override val dispatcher: CoroutineDispatcher
) : SuspendingInteractor<EmptyParams, Unit>() {
    override suspend fun doWork(params: EmptyParams) {
        delay(1000L)
        throw IOException()
    }
}

@ExperimentalCoroutinesApi
class FlowInteractorWithThreeEmissions(
    override val dispatcher: CoroutineDispatcher
) : FlowInteractor<EmptyParams, Int>() {
    override fun createFlow(params: EmptyParams): Flow<Int> {
        return flow {
            delay(1000L)
            repeat(3) {
                emit(it)
            }
        }
    }
}

@ExperimentalCoroutinesApi
class FlowInteractorWithException(
    override val dispatcher: CoroutineDispatcher
) : FlowInteractor<EmptyParams, Int>() {
    override fun createFlow(params: EmptyParams): Flow<Int> {
        return flow {
            delay(1000L)
            throw IOException()
        }
    }
}
