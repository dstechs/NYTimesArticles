package com.dinesh.kotlinstructure.router

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class ProgressResponseBody(private val mResponseBody: ResponseBody?, private val mProgressListener: Listener) : ResponseBody() {
    private var mBufferedSource: BufferedSource? = null
    private var mTotalBytesRead: Long = 0

    init {
        mTotalBytesRead = 0L
    }

    override fun contentType(): MediaType? {
        return mResponseBody!!.contentType()
    }

    override fun contentLength(): Long {
        return mResponseBody!!.contentLength()
    }

    fun totalBytesRead(): Long {
        return mTotalBytesRead
    }

    override fun source(): BufferedSource? {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody!!.source()))
        }
        return mBufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                mTotalBytesRead += if (bytesRead != -1L) bytesRead else 0
                if (mResponseBody != null)
                    mProgressListener.onRequestProgress(mTotalBytesRead, mResponseBody.contentLength(), bytesRead == -1L, mBufferedSource, bytesRead)
                return bytesRead
            }
        }
    }

    interface Listener {
        fun onRequestProgress(mTotalBytesRead: Long, totalLength: Long, isBlank: Boolean, mBufferedSource: BufferedSource?, byteCount: Long)
    }
}