package vn.vunganyen.fastdelivery.data.api

import java.io.Serializable

class PathURL : Serializable {
    companion object{
        const val BASE_URL ="http://192.168.2.11:4000/"
      //  const val BASE_URL ="http://172.20.10.8:4000/"
        const val BASE_URL2 ="https://graphhopper.com/api/1/route/"
     //   const val BASE_URL3 ="http://dev.minhducduong.com:89/api/"
        const val BASE_URL3 ="https://provinces.open-api.vn/api/"
        const val BASE_URL_petShop ="http://192.168.2.11:3000/"
    }
}