package com.dinesh.kotlinstructure.util

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread
 */
fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}