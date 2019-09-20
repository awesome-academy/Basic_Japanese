package com.sun.basic_japanese.data.source.local

interface LocalDataHandler<P, T> {
    @Throws(Exception::class)
    fun execute(vararg params: P): T
}
