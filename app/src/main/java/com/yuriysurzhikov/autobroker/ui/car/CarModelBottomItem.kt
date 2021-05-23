package com.yuriysurzhikov.autobroker.ui.car

import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.ui.widget.sheets.IBottomItem

data class CarModelBottomItem(val carModel: CarModel) : IBottomItem {
    override fun getTitle() = carModel.name
    override fun getImageUrl() = carModel.imageSrc
}