package vn.vunganyen.fastdelivery.screens.shipper.collection

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiSalaryService
import vn.vunganyen.fastdelivery.data.model.salary.MainCollectionRes
import vn.vunganyen.fastdelivery.data.model.salary.ShipperCollectionReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShipperCollectionPst {
    var shipperCollectionItf : ShipperCollectionItf

    constructor(shipperCollectionItf: ShipperCollectionItf) {
        this.shipperCollectionItf = shipperCollectionItf
    }

    fun validCheck(req: ShipperCollectionReq){
        if(req.dateFrom.isEmpty() || req.dateTo.isEmpty()){
            shipperCollectionItf.Empty()
            return
        }
        val result = req.dateTo.compareTo(req.dateFrom)
        if(result < 0 || result == 0){
            shipperCollectionItf.DateError()
            return
        }
        shipper_collection(req)
    }

    fun shipper_collection(req : ShipperCollectionReq){
        println("vô")
        ApiSalaryService.Api.api.shipper_collection(SplashActivity.token,req).enqueue(object : Callback<MainCollectionRes>{
            override fun onResponse(call: Call<MainCollectionRes>, response: Response<MainCollectionRes>) {
                if(response.isSuccessful){
                    println("sao nữa")
                    shipperCollectionItf.getListCollection(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainCollectionRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}