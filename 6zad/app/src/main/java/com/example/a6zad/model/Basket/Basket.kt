package com.example.a6zad.model.Basket

import com.example.a6zad.model.Product.Product
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Basket : RealmObject {
    @PrimaryKey var id: String =""
    var product: Product? = null
    var quantity = 0



    constructor(id: String, product: Product?, quantity: Int) {
        this.id = id
        this.product = product
        this.quantity = quantity
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}