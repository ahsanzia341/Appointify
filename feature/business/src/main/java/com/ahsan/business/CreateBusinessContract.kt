package com.ahsan.business

import com.ahsan.data.models.Business

data class ViewState(val isLoading: Boolean)

sealed class CreateBusinessEvent{
    data class CreateBusiness(val business: Business): CreateBusinessEvent()
}