package com.poc.sample

class RequestCodes{
    companion object{
        const val REGISTRATION_RC = 101
    }
}

class SharedPreferenceConstants{
    companion object{
        const val NAME = "PILGRIM_APP"
        const val TOKEN = "token"
        const val USER_NAME = "user_name"
        const val USER_PASSWORD = "user_password"
        const val USER_ID = "user_id"
        const val IS_LOGIN = "is_login"
        const val FCM_TOEKN = "fcm_token"
        const val USER_STATUS = "user_status"
    }
}

enum class UserStatus {
    DELETED, DEACTIVATED, SESSION_EXPIRED, PENDING, ACTIVE, NONE, ORPHAN
}