package com.esoft.paymentsapp.data.remote.dataSource

import com.esoft.paymentsapp.data.remote.api.PaymentsApi
import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.PaymentItem
import com.esoft.paymentsapp.model.SuccessResponse
import org.json.JSONObject

interface PaymentsDataSource {

    suspend fun getPaymentsList(): AuthResponse

}

class PaymentsDataSourceImpl(
    private val paymentsApi: PaymentsApi,
) : PaymentsDataSource {

    companion object {
        private const val SUCCESS = "success"
        private const val ERROR = "error"
        private const val ERROR_CODE = "error_code"
        private const val ERROR_MSG = "error_msg"
        private const val RESPONSE = "response"
        private const val ITEM_ID = "id"
        private const val ITEM_TITLE = "title"
        private const val ITEM_AMOUNT = "amount"
        private const val ITEM_CREATED = "created"
    }


    override suspend fun getPaymentsList(): AuthResponse {
        return getObjectFromJson(paymentsApi.getPaymentList().string())
    }

    private fun getObjectFromJson(jsonString: String): AuthResponse {
        val jsonContact = JSONObject(jsonString)
        val success: Boolean = jsonContact.getBoolean(SUCCESS)
        if (success) {
            val mutableItemList = mutableListOf<PaymentItem>()
            val jsonItemList = jsonContact.getJSONArray(RESPONSE)
            for (i in 0..<jsonItemList.length()) {
                val jsonItem: JSONObject? = jsonItemList.getJSONObject(i)
                mutableItemList.add(
                    PaymentItem(
                        id = jsonItem?.optInt(ITEM_ID),
                        title = jsonItem?.optString(ITEM_TITLE),
                        amount = jsonItem?.opt(ITEM_AMOUNT)?.toString(),
                        created = jsonItem?.optString(ITEM_CREATED).toString()
                    )
                )
            }
            return AuthResponse(
                success = success,
                response = SuccessResponse.PaymentsItems(
                    items = mutableItemList
                )
            )
        } else {
            val jsonError = jsonContact.getJSONObject(ERROR)
            return AuthResponse(
                success = success,
                response = SuccessResponse.ErrorModel(
                    code = jsonError.getInt(ERROR_CODE),
                    message = jsonError.getString(ERROR_MSG)
                )
            )
        }
    }


}