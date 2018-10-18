package com.dinesh.kotlinstructure.presentor

import com.dinesh.kotlinstructure.router.ApiConstant
import com.dinesh.kotlinstructure.view.MainActivityView
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityPresentorTest {

    @Mock
    private var view: MainActivityView? = null

    private var presentor: MainActivityPresentor? = null

    @Before
    fun setUp() {
        presentor = MainActivityPresentor()
        presentor!!.viewHandler = view
    }

    @After
    fun tearDown() {
        presentor = null
        view = null
    }

    @Test
    fun successRequestTest() {
        Mockito.`when`(view?.isTesting()).thenReturn(true)
        presentor?.requestData(requestType = ApiConstant.REQUEST_TYPE.MOST_POPULAR)
        Mockito.verify(view)?.onDataAvailable()
    }

    @Test
    fun failureRequestTest() {
        Mockito.`when`(view!!.isTesting()).thenReturn(true)
        presentor?.requestData(requestType = ApiConstant.REQUEST_TYPE.FAKE)
        Mockito.verify(view)?.onDataNotFound()
    }
}