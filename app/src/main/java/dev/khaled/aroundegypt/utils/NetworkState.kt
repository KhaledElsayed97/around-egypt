package dev.khaled.aroundegypt.utils

sealed class NetworkState {
    object Disconnected : NetworkState()
    object Connected : NetworkState()
}