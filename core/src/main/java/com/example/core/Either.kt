package com.example.core

sealed class Either<out L, out R> {

    data class Left<out L>(val l: L) : Either<L, Nothing>()
    data class Right<out R>(val r: R) : Either<Nothing, R>()

    val isLeft get() = this is Left<L>
    val isRight get() = this is Right<R>

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(l)
            is Right -> fnR(r)
        }
}
