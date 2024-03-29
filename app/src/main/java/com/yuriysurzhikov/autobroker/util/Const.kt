package com.yuriysurzhikov.autobroker.util

object Const {

    const val LOGIN_TEXT_TIMER = 1500L

    object CarConst {
        const val CAR_MODEL_COLLECTION = "models"
        const val CAR_NAME_FIELD = "name"
        const val CAR_MODEL_URI_FIELD = "icon"
        const val USER_CARS_COLLECTION = "cars_collection"

        const val CAR_ID = "car_id"
        const val CAR_TITLE = "car_title"
        const val CAR_YEAR_ISSUE = "car_year_issue"
        const val CAR_IMAGES_REFS = "car_images_refs"
        const val CAR_MODEL_ID = "car_model_id"
        const val CAR_NUMBER = "car_number"
        const val CAR_BRAND_ID = "car_brand_id"
        const val CAR_MILEAGE = "car_mileage"
        const val CAR_DESCRIPTION = "car_description"
        const val CAR_PRICE = "car_price"
    }

    object UserConst {
        const val USER_COLLECTION = "users"
        const val ID = "user-id"
        const val PASSWORD = "user-password"
        const val IS_LOGGED_IN = "is-logged-in"
        const val IS_FULL_REGISTER = "is-full-register"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val PHOTO_URL = "photo-url"
        const val LOCATION = "location"
        const val DISPLAY_NAME = "display-name"
    }

    object DictsConst {
        const val REGIONS_COLLECTION = "regions"
        const val FUEL_TYPE_COLLECTION = "fuel_type"
        const val GEAR_TYPE_COLLECTION = "gear_type"
        const val CARS_COLLECTION = "cars"
        const val CAR_REGISTRATION_TYPE = "car_registration_type"
    }

    object GeneralConst {
        const val LABEL_RU = "name_ru"
        const val LABEL_EN = "name_en"
        const val ACTION_LOGOUT = "logout"
        const val ACTION_FULL_INFO = "full_user_info"
        const val DEFAULT_ICON_FIELD = "icon"
    }
}