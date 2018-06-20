package co.omisego.omgshop.models

import com.google.gson.annotations.SerializedName

/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/27/2017 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

object Register {
    data class Request(
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String
    )
}
