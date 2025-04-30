package com.ahsan.business

import com.ahsan.data.models.Business

data class ViewState(val isLoading: Boolean = false,
                     val isSuccess: Boolean? = null,
                     val isSubmitted: Boolean = false, val business: Business? = null)

sealed class CreateBusinessEvent{
    data class CreateBusiness(val business: Business): CreateBusinessEvent()
}