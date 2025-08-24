package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.utils.NetworkState
import dev.khaled.aroundegypt.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TestNetworkUtils(private val isOnline: Boolean) : NetworkUtils(createMockContext()) {
    override fun isNetworkAvailable(): Boolean = isOnline
    override fun observeNetworkState(): Flow<NetworkState> =
        MutableStateFlow(if (isOnline) NetworkState.Connected else NetworkState.Disconnected)

    companion object {
        private fun createMockContext(): android.content.Context {
            val mockContext = mock<android.content.Context>()
            val mockConnectivityManager = mock<android.net.ConnectivityManager>()
            whenever(mockContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE))
                .thenReturn(mockConnectivityManager)
            return mockContext
        }
    }
}