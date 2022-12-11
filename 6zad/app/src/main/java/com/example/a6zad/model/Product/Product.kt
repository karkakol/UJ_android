package com.example.a6zad.model.Product

import com.example.a6zad.model.Category.Category
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Product : RealmObject {
    @PrimaryKey var id: String? = null
    var category: Category? = null
    var name: String?= null
    var description: String?= null
    var status: String?= null


    constructor(id: String?, categoryId: Category?, name: String?, status: String?, description: String?) {
        this.name = name
        this.id = id
        this.category = categoryId
        this.status = status
        this.description = description
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}