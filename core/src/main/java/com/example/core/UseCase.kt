package com.example.core

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<F, S, P> : CoroutineScope {

    abstract suspend fun run(params: P): Either<F, S>

    override val coroutineContext: CoroutineContext
        get() = Job()

    operator fun invoke(params: P, result: (Either<F, S>) -> Unit) {
        val job: Deferred<Either<F, S>> = async(coroutineContext + Dispatchers.IO) { run(params) }
        launch(coroutineContext + Dispatchers.Main) { result.invoke(job.await()) }
    }

}