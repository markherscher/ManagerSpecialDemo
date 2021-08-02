package com.herscher.swiftlydemo.api.special

import com.herscher.swiftlydemo.api.NetworkResult
import com.herscher.swiftlydemo.api.toNetworkSingle
import io.reactivex.Single

internal class RealSpecialsManager(
    private val specialsApi: SpecialsApi
) : SpecialsManager {

    override fun getSpecialsList(): Single<SpecialsManagerResult> {
        return specialsApi.getSpecials().toNetworkSingle()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        // Map from the internal API type to our public type
                        NetworkResult.Success(
                            ManagerSpecials(
                                canvasUnit = result.data.canvasUnit,
                                specialItemList = result.data.itemList.map { item ->
                                    SpecialItem(
                                        name = item.name,
                                        height = item.height,
                                        imageUrl = item.imageUrl,
                                        originalPrice = item.originalPrice,
                                        price = item.price,
                                        width = item.width
                                    )
                                }
                            )
                        )
                    }
                    is NetworkResult.Failed -> NetworkResult.Failed(result.failure)
                }
            }
    }
}