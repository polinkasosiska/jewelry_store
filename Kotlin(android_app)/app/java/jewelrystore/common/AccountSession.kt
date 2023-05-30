package com.sysoliatina.jewelrystore.common


class AccountSession {
    var token: String? = null
    var userId: Int? = null
    var email: String? = null
    var fullName: String? = null


    companion object {
        var instance: AccountSession = AccountSession()
    }
}