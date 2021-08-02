package com.herscher.swiftlydemo.specials

import androidx.lifecycle.ViewModel
import com.herscher.swiftlydemo.R
import com.herscher.swiftlydemo.api.NetworkError
import com.herscher.swiftlydemo.api.NetworkResult
import com.herscher.swiftlydemo.api.special.ManagerSpecials
import com.herscher.swiftlydemo.api.special.SpecialsManager
import com.herscher.swiftlydemo.util.StringProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SpecialsListViewModel(
    private val manager: SpecialsManager,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val state = BehaviorSubject.create<SpecialsListState>()

    fun state(): Observable<SpecialsListState> = state.hide()

    init {
        state.onNext(SpecialsListState.Initial)
    }

    fun loadSpecialsList() {
        state.onNext(SpecialsListState.Loading)

        disposable.add(
            manager.getSpecialsList()
                .subscribe({ result ->
                    state.onNext(
                        when (result) {
                            is NetworkResult.Success -> handleSuccess(result.data)
                            is NetworkResult.Failed -> handleFailedNetwork(result.failure)
                        }
                    )
                }, {
                    state.onNext(
                        SpecialsListState.Error(
                            stringProvider.getString(R.string.error_general)
                        )
                    )
                })
        )
    }

    private fun handleSuccess(managerSpecials: ManagerSpecials): SpecialsListState {
        val canvasUnit = managerSpecials.canvasUnit.coerceAtLeast(1)

        return SpecialsListState.Content(
            managerSpecials.specialItemList.map { item ->
                Product(
                    name = item.name,
                    imageUrl = item.imageUrl,
                    currentPrice = stringProvider.getFormattedString(
                        R.string.dollar_format, item.price
                    ),
                    originalPrice = stringProvider.getFormattedString(
                        R.string.dollar_format, item.originalPrice
                    ),
                    // Since the item's dimensions are from 1 to canvasUnit, they are a percentage
                    // of the screen's width (width of 8 and canvasUnit of 16 = .5).
                    dimension = Product.Dimension(
                        heightPercentage = item.height.toFloat() / canvasUnit,
                        widthPercentage = item.width.coerceAtMost(canvasUnit).toFloat() / canvasUnit
                    )
                )
            }
        )
    }

    private fun handleFailedNetwork(error: NetworkError): SpecialsListState {
        return when (error) {
            is NetworkError.NoInternet -> {
                SpecialsListState.Error(
                    stringProvider.getString(R.string.error_no_internet)
                )
            }
            else -> {
                SpecialsListState.Error(
                    stringProvider.getString(R.string.error_general)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}