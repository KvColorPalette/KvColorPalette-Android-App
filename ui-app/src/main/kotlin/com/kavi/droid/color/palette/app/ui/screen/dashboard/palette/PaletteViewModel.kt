package com.kavi.droid.color.palette.app.ui.screen.dashboard.palette

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaletteViewModel @Inject constructor() : ViewModel() {

    private var _selectedPageIndex = MutableStateFlow(0)
    var selectedPageIndex: StateFlow<Int> = _selectedPageIndex

    fun setOrUpdateSelectedPageIndex(index: Int) {
        _selectedPageIndex.value = index
    }
}