package com.herscher.swiftlydemo.specials

import com.herscher.swiftlydemo.api.NetworkError
import com.herscher.swiftlydemo.api.NetworkResult
import com.herscher.swiftlydemo.api.special.ManagerSpecials
import com.herscher.swiftlydemo.api.special.SpecialItem
import com.herscher.swiftlydemo.api.special.SpecialsManager
import com.herscher.swiftlydemo.api.special.SpecialsManagerResult
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class SpecialsListViewModelTests {
    private val specialsManager = SpecialsManagerTestImpl()
    private val viewModel = SpecialsListViewModel(specialsManager, TestStringProvider())
    lateinit var testState: TestObserver<SpecialsListState>

    @Before
    fun setup() {
        testState = viewModel.state().test()
    }

    @Test
    fun `test success`() {
        specialsManager.resultSingle = Single.just(NetworkResult.Success(mockResponse))
        viewModel.loadSpecialsList()
        testState.assertValues(
            SpecialsListState.Initial,
            SpecialsListState.Loading,
            SpecialsListState.Content(
                products = expectedProducts
            )
        )
    }

    @Test
    fun `test no internet error`() {
        specialsManager.resultSingle = Single.just(NetworkResult.Failed(NetworkError.NoInternet))
        viewModel.loadSpecialsList()
        testState.assertValues(
            SpecialsListState.Initial,
            SpecialsListState.Loading,
            SpecialsListState.Error("No Internet connection")
        )
    }

    @Test
    fun `test failed response error`() {
        specialsManager.resultSingle =
            Single.just(NetworkResult.Failed(NetworkError.FailedResponse))
        viewModel.loadSpecialsList()
        testState.assertValues(
            SpecialsListState.Initial,
            SpecialsListState.Loading,
            SpecialsListState.Error("An unexpected error occurred")
        )
    }

    @Test
    fun `test timeout error`() {
        specialsManager.resultSingle = Single.just(NetworkResult.Failed(NetworkError.Timeout))
        viewModel.loadSpecialsList()
        testState.assertValues(
            SpecialsListState.Initial,
            SpecialsListState.Loading,
            SpecialsListState.Error("An unexpected error occurred")
        )
    }

    @Test
    fun `test exception thrown by manager`() {
        specialsManager.resultSingle = Single.error(NullPointerException("blah"))
        viewModel.loadSpecialsList()
        testState.assertValues(
            SpecialsListState.Initial,
            SpecialsListState.Loading,
            SpecialsListState.Error("An unexpected error occurred")
        )
    }

    private val expectedProducts = listOf(
        Product(
            name = "name1",
            imageUrl = "image1",
            currentPrice = "$2.00",
            originalPrice = "$1.00",
            dimension = Product.Dimension(
                heightPercentage = 0.5f,
                widthPercentage = 1.0f
            )
        ),
        Product(
            name = "name2",
            imageUrl = "image2",
            currentPrice = "$2.99",
            originalPrice = "$1.12",
            dimension = Product.Dimension(
                heightPercentage = 1.0f,
                widthPercentage = 0.125f
            )
        ),
        Product(
            name = "name3",
            imageUrl = "image3",
            currentPrice = "$2.33",
            originalPrice = "$4.00",
            dimension = Product.Dimension(
                heightPercentage = 0.25f,
                widthPercentage = 0.25f
            )
        ),
        Product(
            name = "name4",
            imageUrl = "image4",
            currentPrice = "$2.00",
            originalPrice = "$1.00",
            dimension = Product.Dimension(
                heightPercentage = 1.125f,
                widthPercentage = 1.0f
            )
        )
    )

    private val mockResponse = ManagerSpecials(
        canvasUnit = 16,
        specialItemList = listOf(
            SpecialItem(
                name = "name1",
                height = 8,
                imageUrl = "image1",
                originalPrice = "1.00",
                price = "2.00",
                width = 16
            ),
            SpecialItem(
                name = "name2",
                height = 16,
                imageUrl = "image2",
                originalPrice = "1.12",
                price = "2.99",
                width = 2
            ),
            SpecialItem(
                name = "name3",
                height = 4,
                imageUrl = "image3",
                originalPrice = "4.00",
                price = "2.33",
                width = 4
            ),
            SpecialItem(
                name = "name4",
                height = 18,
                imageUrl = "image4",
                originalPrice = "1.00",
                price = "2.00",
                width = 20
            )
        )
    )
}

class SpecialsManagerTestImpl : SpecialsManager {
    lateinit var resultSingle: Single<SpecialsManagerResult>
    override fun getSpecialsList(): Single<SpecialsManagerResult> = resultSingle
}