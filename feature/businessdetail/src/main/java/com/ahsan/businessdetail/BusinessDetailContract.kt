package com.ahsan.businessdetail

import com.ahsan.data.models.Business

data class ViewState(val isLoading: Boolean, val business: Business?)

sealed class BusinessDetailEvent{

}