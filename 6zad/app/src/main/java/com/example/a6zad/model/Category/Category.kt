package com.example.a6zad.model.Category

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey



open class Category : RealmObject {
    @PrimaryKey var id: String? = null
      var name: String? = null
      var status: String? = null
      var description: String? = null


    constructor(id: String?, name: String?, status: String?, description: String?) {
        this.name = name
        this.id = id
        this.status = status
        this.description = description
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}