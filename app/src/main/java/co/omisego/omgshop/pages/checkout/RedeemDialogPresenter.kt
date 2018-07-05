package co.omisego.omgshop.pages.checkout

import co.omisego.omgshop.base.BaseContract
import co.omisego.omgshop.base.BasePresenterImpl
import co.omisego.omisego.extension.bd
import java.math.BigDecimal

/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 4/12/2017 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

class RedeemDialogPresenter : BasePresenterImpl<RedeemDialogContract.View, BaseContract.BaseCaller>(), RedeemDialogContract.Presenter {
    override var caller: BaseContract.BaseCaller? = null
    private var mRedeemValue: BigDecimal = 0.bd

    override fun handleClickRedeem() {
        mView?.sendDiscountToCheckoutPage(mRedeemValue)
    }

    override fun redeemChanged(value: BigDecimal, symbol: String) {
        mRedeemValue = value
        mView?.setTextDiscount(value.toPlainString())
        mView?.setTextRedeemAmount(value.toPlainString())
    }
}
