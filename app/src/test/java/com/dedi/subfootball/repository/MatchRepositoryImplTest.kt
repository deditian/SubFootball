package com.dedi.subfootball.repository

import com.dedi.subfootball.api.MyApi
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchRepositoryImplTest {

    @Mock
    lateinit var myApi: MyApi

    lateinit var matchRepositoryImpl: MatchRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchRepositoryImpl = MatchRepositoryImpl(myApi)
    }

    @Test
    fun getEventById() {
        matchRepositoryImpl.getEventById("111")
        verify(myApi).getEventById("111")
    }

    @Test
    fun getLastMatch() {
        matchRepositoryImpl.getLastMatch("111")
        verify(myApi).getLastFootball("111")
    }

    @Test
    fun getNextMatch() {
        matchRepositoryImpl.getNextMatch("111")
        verify(myApi).getNextFootball("111")
    }
}