package com.dinesh.kotlinstructure.common

import com.dinesh.kotlinstructure.models.ResultModel
import io.reactivex.annotations.NonNull

/**
 * Created by Dinesh
 */

object Consts {
    val TAG = "MY_ASSESMENT"
    val NETWORK_BR = "COM.DINESH.NETWORK.CHANGE"
}

object DataMapper {

    @NonNull
    val mArticleDetails: MutableList<ResultModel?> = mutableListOf()
}
