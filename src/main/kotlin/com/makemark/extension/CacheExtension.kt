package com.makemark.extension

import com.github.benmanes.caffeine.cache.AsyncCache
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future

suspend fun <K : Any, V : Any?> AsyncCache<K, V>.getSuspending(
    key: K,
    block: suspend (K) -> V
): V = coroutineScope {
    this@getSuspending.get(key) { k: K, _ ->
        future(coroutineContext) {
            block.invoke(k)
        }
    }.await()
}


suspend fun <K : Any, V : Any?> AsyncCache<K, V>.putSuspending(
    key: K,
    value: V
): Unit = coroutineScope {
    this@putSuspending.put(key,
        future(coroutineContext) { value }
    )
}

